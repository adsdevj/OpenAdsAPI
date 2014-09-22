package services.core

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.routing.SmallestMailboxPool
import services.core.ActorSetting._
import services.core.UtilityConversion._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by yabumoto on 2014/09/21.
 * ServiceClient is media service base.
 * management parallel action.
 */
trait ServiceClient {

  /**
   * setting for service action.
   */
  implicit val setting: ServiceSetting = DefaultServiceSetting
  implicit def actionSync(result: Future[ServiceResult]): ServiceResult = result block

  /**
   * client router.
   */
  val router = {
    val actorSystem = ActorSystem()
    val router = SmallestMailboxPool(setting.REQUEST_FREQUENCY) props (
      ServiceRouter props setting
    )
    actorSystem actorOf router
  }

  /**
   * execute media action.
   * routing actor action is blocked.
   * @param action media action.
   * @return Future[result]
   */
  def action(action: () => Future[ServiceResult]): Future[ServiceResult] = actionRetry(ServiceAction(action))

  /**
   * action retry if can retry.
   * @param service service action.
   * @param retryCount now retry count.
   * @return retried result.
   */
  private def actionRetry(service: ServiceAction, retryCount: Int = 1): Future[ServiceResult] = {
    val result = (router ? service).mapTo[Future[ServiceResult]] block;
    if (result.code == (Result.ERROR | Result.RETRY) && retryCount < setting.RETRY_COUNT) result flatMap { res =>
      actionRetry(service, retryCount + 1) map { r => r wrapError res }
    } else {
      result
    }
  }
}


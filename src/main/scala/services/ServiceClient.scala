package services

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.routing.SmallestMailboxPool

import scala.concurrent.Future

import UtilityConversion._
import ActorSetting._

/**
 * Created by yabumoto on 2014/09/21.
 * ServiceClient is media service base.
 * management parallel action.
 */
trait ServiceClient {

  /**
   * setting for service action.
   */
  implicit val setting = DefaultServiceSetting

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
  def action(action: () => Future[ServiceResult]): Future[ServiceResult] =
    // return Future[Future[ServiceResult]]
    (router ? ServiceAction(action)).asInstanceOf[Future[Future[ServiceResult]]] block
}


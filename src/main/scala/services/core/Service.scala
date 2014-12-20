package services.core

import java.util.concurrent.{SynchronousQueue, ThreadPoolExecutor, TimeUnit}
import services.control._
import utils._

import scala.concurrent._

/**
 * Created by yabumoto on 2014/09/21.
 * ServiceClient is media service base.
 * management parallel action.
 */
trait Service {

  /**
   * setting for service action.
   */
  val setting: ServiceSetting = DefaultServiceSetting
  implicit val settingImpl: ServiceSetting = setting

  /**
   * Service frequency context
   */
  implicit val ext: ExecutionContext = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(
      0,
      setting.REQUEST_FREQUENCY,
      60L,
      TimeUnit.SECONDS,
      new SynchronousQueue[Runnable]
    ), errorHandler)

  /**
   * ExceptionHandler
   * If you want to handle error, then override this method.
   * @param ex
   */
  def errorHandler(ex: Throwable): Unit = {
    logger.error(s"Service error at ${this.getClass.getName}. ${ex.getMessage}")
    throw ServiceException(this, ex)
  }

  /**
   * sync action.
   * please override action.
   * @param oldResults
   * @return Future[result]
   */
  protected def action(oldResults: Seq[ServiceResult]): Future[ServiceResult]

  /**
   * execute action with retry.
   * @return
   */
  def execute(): Future[ServiceResult] = actionRetry() { oldResults =>
    action(oldResults)
  }

  /**
   * action retry if can retry.
   * @param retryCount now retry count.
   * @param oldResults
   * @param f main action
   * @return retried result.
   */
  protected def actionRetry(retryCount: Int = 1, oldResults: Seq[ServiceResult] = Nil)(f: (Seq[ServiceResult]) => Future[ServiceResult]): Future[ServiceResult] = {
    for {
      onetimeResult <- f(oldResults)
      result <- {
        new ResultCtrl(onetimeResult, retryCount) match {
          case CanRetry(res, cnt) => actionRetry(cnt, oldResults :+ res)(f)
          case _ => Future { onetimeResult }
        }
      }
    } yield result
  }
}


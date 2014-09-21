package services

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal
import ExecutionContext.Implicits.global

/**
 * Created by yabumoto on 2014/09/21.
 * execute service.
 */

class ServiceAction(action: () => Future[ServiceResult]) {
  def execute: Future[ServiceResult] = action() recover {
    case NonFatal(ex) => new ServiceExceptionResult(Result.ERROR, ex)
  }
}

object ServiceAction {
  def apply(action: () => Future[ServiceResult]) = new ServiceAction(action)
}

package services.core

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal

/**
 * Created by yabumoto on 2014/09/21.
 * execute service.
 */

class ServiceAction(action: () => Future[ServiceResult]) {
  def execute: Future[ServiceResult] = action() recover {
    case NonFatal(ex) => new ServiceExceptionResult(false, ex)
  }
}

object ServiceAction {
  def apply(action: () => Future[ServiceResult]) = new ServiceAction(action)
}

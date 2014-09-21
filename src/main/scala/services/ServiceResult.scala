package services

/**
 * Created by yabumoto on 2014/09/21.
 */

class ServiceResult(val code: Result.Code)
class ServiceExceptionResult(code: Result.Code, val exception: Throwable) extends ServiceResult(code)

object Result{
  type Code = Int
  val PROCESSING: Code = 1
  val SUCCESS: Code = 2
  val ERROR: Code = 3
}

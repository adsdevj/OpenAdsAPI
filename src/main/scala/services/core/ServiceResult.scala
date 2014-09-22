package services.core

import scala.collection.mutable.Buffer

/**
 * Created by yabumoto on 2014/09/21.
 */

class ServiceResult(val code: Result.Code) {

  //TODO: maybe better method that class composition.
  private val errorBuffer: Buffer[ServiceResult] = Buffer()
  private val subResultBuffer: Buffer[ServiceResult] = Buffer()
  def errors = {
    val result = errorBuffer toSeq;
    if ((code & Result.ERROR) == Result.ERROR) { result :+ this }
    else result
  }
  def subResults = subResultBuffer toSeq

  def & (result: ServiceResult) = andThen(result)

  /**
   * Add error to ServiceResult.
   * @param errorResult
   * @return
   */
  def wrapError(errorResult: ServiceResult) = {
    this.errorBuffer += errorResult
    this
  }

  /**
   * Add sub result to ServiceResult
   * @param result
   * @return
   */
  def andThen(result: ServiceResult) = {
    this.subResultBuffer += result
    this
  }
}

case class ServiceExceptionResult(val canRetry: Boolean, val exception: Throwable) extends ServiceResult(
    if (canRetry) Result.ERROR | Result.RETRY else Result.ERROR
)

//TODO: change to TaggedType(scalaz)
object Result{
  type Code = Int
  val PROCESSING: Code = 1
  val SUCCESS: Code = 2
  val ERROR: Code = 4
  val RETRY: Code = 1024
}

package services.core

/**
 * Created by yabumoto on 2014/09/21.
 */

/**
 * Base class of result.
 * @param success
 */
class ServiceResult (val success: Boolean) {
  /**
   * This sync can retry.
   */
  val canRetry: Boolean = false

  /**
   * AND synthesis results
   * @param other
   * @return
   */
  def andThen(other: ServiceResult): ServiceResult = ServiceResult(success & other.success)

  /**
   * OR synthesis results
   * @param other
   * @return
   */
  def orElse(other: ServiceResult): ServiceResult = ServiceResult(success | other.success)

  /**
   * AND synthesis results. alias of andThen.
   * @param other
   * @return
   */
  def &(other: ServiceResult): ServiceResult = andThen(other)

  /**
   * OR synthesis results. alias of orElse.
   * @param other
   * @return
   */
  def |(other: ServiceResult): ServiceResult = orElse(other)
}

/**
 * Simple companion object.
 */
object ServiceResult {
  def apply(success: Boolean) = new ServiceResult(success)

  /**
   * Result of class when it fails.
   * @param code
   * @param canRetry
   * @param exception
   */
  class Failed(val code: Option[StatusCode], override val canRetry: Boolean, val exception: Option[Throwable] = None) extends ServiceResult(false) {
    override def andThen(other: ServiceResult) = this

    override def orElse(other: ServiceResult) = other
  }

  /**
   * StatusCode of class when it fails
   * @param code
   * @param message
   */
  case class StatusCode(code: Int, message: String)

  /**
   * Result class with the response content
   * @param contents
   * @tparam A
   */
  class ContentsResult[A](success: Boolean, val contents: Seq[A]) extends ServiceResult(success) {
    override def andThen(other: ServiceResult) = other match {
      case o: ContentsResult[A] => new ContentsResult[A](success & o.success, contents ++ o.contents)
      case _ => throw new IllegalArgumentException("can not configure due to differing types ServiceResult[A]")
    }

    override def orElse(other: ServiceResult) = other match {
      case o: ContentsResult[A] => new ContentsResult[A](success | o.success, contents ++ o.contents)
      case _ => throw new IllegalArgumentException("can not configure due to differing types ServiceResult[A]")
    }
  }

  /**
   * Result of class when it fails.
   * @param code
   * @param canRetry
   * @param exception
   * @param anyContents
   * @tparam A
   */
  class ContentFailed[A](val code: Option[StatusCode], override val canRetry: Boolean, val exception: Option[Throwable] = None, anyContents: Seq[A] = Nil) extends ContentsResult[A](false, anyContents)

  /**
   * Service Exception. This exception is fatal error.
   * You can't know what campaign is failed.
   * @param service
   * @param ex
   */
  class ExceptionResult(service: Service, ex: Throwable) extends ServiceResult(false)

}

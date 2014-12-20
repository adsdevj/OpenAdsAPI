package services.core

/**
 * Created by yabumoto on 14/12/20.
 */
class ServiceException(service: Service, message: Option[String], ex: Option[Throwable]) extends Exception(
  ServiceException.generateMessage(message, ex).mkString
) {
  override def getCause() = ex getOrElse this
}

/**
 * ServiceExceptions
 */
object ServiceException{
  def apply(service: Service, ex: Throwable) = new ServiceException(service, None, Option(ex))
  def apply(service: Service, msg: String) = new ServiceException(service, Option(msg), None)
  def apply(service: Service, msg: String, ex: Throwable) = new ServiceException(service, Option(msg), Option(ex))

  private[core] def generateMessage (message: Option[String], ex: Option[Throwable]) = message orElse ex.map(_.getMessage)
}



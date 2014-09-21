package services

import akka.util.Timeout

import scala.concurrent.duration._

/**
 * Created by yabumoto on 2014/09/21.
 */
trait ServiceSetting {
  val REQUEST_FREQUENCY: Int
}

object DefaultServiceSetting extends ServiceSetting {
  //TODO: load from setting file.
  override val REQUEST_FREQUENCY = 1000
}

object ActorSetting {
  //TODO: maybe AKKA has better methods
  implicit val timeout: Timeout = 1 hours
}

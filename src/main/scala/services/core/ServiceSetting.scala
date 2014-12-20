package services.core

/**
 * Created by yabumoto on 2014/09/21.
 */
trait ServiceSetting {
  val REQUEST_FREQUENCY: Int
  val RETRY_COUNT: Int
}

object DefaultServiceSetting extends ServiceSetting {
  //TODO: load from setting file.
  override val REQUEST_FREQUENCY = 1000
  override val RETRY_COUNT = 5
}
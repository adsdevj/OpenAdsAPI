package services.control

import services.core.ServiceSetting
import services.core.ServiceResult

/**
 * Created by yabumoto on 14/12/20.
 */
class ResultCtrl(val result: ServiceResult, val retryCount: Int)

object CanRetry {

  def unapply(cAny: Any)(implicit setting: ServiceSetting): Option[(ServiceResult, Int)] = cAny match {
    case c:ResultCtrl if (check(c.result, c.retryCount, setting)) => Some(c.result -> (c.retryCount + 1))
    case _ => None
  }

  def check(result: ServiceResult, retryCount: Int, setting: ServiceSetting): Boolean = {
    !result.success && result.canRetry && retryCount < setting.RETRY_COUNT
  }
}

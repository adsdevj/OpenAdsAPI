package utils

import org.slf4j.LoggerFactory

/**
 * Created by yabumoto on 2014/10/04.
 * for logging
 */
trait Logger {
  lazy val logger = LoggerFactory.getLogger("open_ads_api")
}

package services.medias

import services.medias.Media.Codes._
import services.medias.Media.SynchronizationRates._

/**
 * Created by yabumoto on 2014/09/23.
 */
object Media {

  /**
   * Media code for information.
   */
  sealed abstract class MediaCode(code: Int, name: String)
  object Codes {
    case object GDN extends MediaCode(1, "GDN")
    case object YDN extends MediaCode(2, "YDN")
  }

  /**
   * Media synchronization rate.
   */
  sealed abstract class MediaSynchronizationRate(rate: Int)
  object SynchronizationRates {
    case object LEVEL1 extends MediaSynchronizationRate(1)
    case object LEVEL2 extends MediaSynchronizationRate(2)
    case object LEVEL3 extends MediaSynchronizationRate(3)
    case object LEVEL4 extends MediaSynchronizationRate(4)
  }

  /**
   * Declare media information.
   */
  case class MediaInfo(syncLevel: MediaSynchronizationRate)

  val list = Map[MediaCode, MediaInfo] (
    GDN -> MediaInfo(LEVEL4),
    YDN -> MediaInfo(LEVEL4)
  )
}

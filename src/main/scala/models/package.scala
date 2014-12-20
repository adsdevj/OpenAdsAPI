

/**
 * Created by yabumoto on 2014/10/04.
 */
package object models {

  /**
   * Status Enum
   */
  object Status {
    case object ARCHIVE extends Status(-1)
    case object ACTIVE extends Status(1)
    case object PAUSED extends Status(2)
  }

  sealed abstract class Status(status: Int) {
    def name = toString
    override def toString = name
  }
}

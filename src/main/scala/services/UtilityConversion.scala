package services

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
 * Created by yabumoto on 2014/09/21.
 */
object UtilityConversion {

  implicit class BlockingActions[A](future: Future[A]) {

    /**
     * Blocking Future action
     * @return action result
     */
    def block(implicit atMost: Duration = Duration.Inf): A = Await.result(future, atMost)

  }
}

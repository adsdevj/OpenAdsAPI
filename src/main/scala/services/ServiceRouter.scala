package services

import akka.actor.{Props, Actor}
import akka.pattern.ask
import ActorSetting._

import scala.concurrent.Future

/**
 * Created by yabumoto on 2014/09/21.
 */
class ServiceRouter(implicit setting: ServiceSetting) extends Actor {
  val actor = context.actorOf(ServiceActor.props)

  def receive = {
    case action: ServiceAction => {
      // return Future[ServiceResult]
      val result = (actor ? action).asInstanceOf[Future[ServiceResult]]
      sender() ! result
    }
  }
}

object ServiceRouter {
  def props(implicit setting :ServiceSetting = DefaultServiceSetting): Props = Props(new ServiceRouter)
}


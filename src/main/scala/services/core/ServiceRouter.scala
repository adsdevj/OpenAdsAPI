package services.core

import akka.actor.{Actor, Props}
import akka.pattern.ask
import services.core.ActorSetting._

import scala.concurrent.Future

/**
 * Created by yabumoto on 2014/09/21.
 */
class ServiceRouter(implicit setting: ServiceSetting) extends Actor {
  val actor = context.actorOf(ServiceActor.props)

  def receive = {
    case action: ServiceAction => {
      // return Future[ServiceResult]
      val result = (actor ? action).mapTo[ServiceResult]
      sender() ! result
    }
  }
}

object ServiceRouter {
  def props(implicit setting :ServiceSetting = DefaultServiceSetting): Props = Props(new ServiceRouter)
}


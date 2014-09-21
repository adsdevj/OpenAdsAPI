package services

import akka.actor.{Props, Actor}

import UtilityConversion._

/**
 * Created by yabumoto on 2014/09/21.
 */
class ServiceActor(implicit val setting: ServiceSetting) extends Actor {

  def receive = {
    case action: ServiceAction => {
      val result = action.execute block;
      sender() ! result
    }
  }
}

object ServiceActor {
  def props(implicit setting: ServiceSetting): Props = {
    Props(new ServiceActor)
  }
}

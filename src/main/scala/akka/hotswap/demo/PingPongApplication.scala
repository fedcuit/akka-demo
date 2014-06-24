package akka.hotswap.demo

import akka.actor.{ActorSystem, Props}
import akka.hotswap.demo.actors.PingPongActor

case class PING()

case class PONG()

object PingPongApplication extends App {
  override def main(args: Array[String]) {
    val system = ActorSystem("PingPongActor")
    val pingPongActor = system.actorOf(Props[PingPongActor])

    pingPongActor ! PING

    Thread.sleep(1)
    system.shutdown()
  }
}

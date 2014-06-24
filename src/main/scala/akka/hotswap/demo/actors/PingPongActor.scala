package akka.hotswap.demo.actors

import akka.actor.Actor
import akka.hotswap.demo.{PING, PONG}


class PingPongActor extends Actor {

  import context.{become, unbecome}

  var count = 0

  override def receive: Receive = {
    case PING =>
      println("PING")
      count += 1
      self ! PONG
      become {
        case PONG =>
          println("PONG")
          count += 1
          self ! PING
          unbecome()
      }
  }
}

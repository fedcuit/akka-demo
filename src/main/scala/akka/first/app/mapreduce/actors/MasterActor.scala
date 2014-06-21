package akka.first.app.mapreduce.actors

import akka.actor.{Actor, Props}
import akka.first.app.mapreduce.Result

class MasterActor extends Actor {
  val aggregateActor = context.actorOf(Props[AggregateActor], "aggregate")
  val reduceActor = context.actorOf(Props(new ReduceActor(aggregateActor)), "reduce")
  val mapActor = context.actorOf(Props(new MapActor(reduceActor)), "map")

  override def receive: Receive = {
    case msg: String => mapActor ! msg
    case result: Result => aggregateActor ! result
  }
}

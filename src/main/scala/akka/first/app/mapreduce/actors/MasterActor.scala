package akka.first.app.mapreduce.actors

import akka.actor.{Actor, Props}
import akka.first.app.mapreduce.Result
import akka.routing.{RoundRobinPool, RoundRobinRouter}

class MasterActor extends Actor {
  val aggregateActor = context.actorOf(Props[AggregateActor], "aggregate")
  val reduceActor = context.actorOf(Props(new ReduceActor(aggregateActor)), "reduce")
  val mapActor = context.actorOf(Props(new MapActor(reduceActor)).withRouter(RoundRobinPool(nrOfInstances = 5)), "map")

  override def receive: Receive = {
    case msg: String => mapActor ! msg
    case result: Result => aggregateActor ! result
  }
}

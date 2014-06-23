package akka.first.app.mapreduce.actors

import akka.actor.{Actor, ActorRef}
import akka.first.app.mapreduce.{ReduceData, Result}

import scala.collection.mutable

class AggregateActor(masterActor: ActorRef) extends Actor {

  private val finalMapReduce: mutable.Map[String, Int] = new mutable.HashMap[String, Int]

  def aggregate(reduceData: ReduceData) {
    for ((key, value) <- reduceData.reduceDataMap) {
      if (finalMapReduce.contains(key)) {
        finalMapReduce(key) = finalMapReduce.get(key).get + value
      } else {
        finalMapReduce += (key -> value)
      }
    }
  }

  override def receive: Receive = {
    case reduceData: ReduceData => aggregate(reduceData)
    case result: Result => masterActor ! finalMapReduce.toMap
  }
}

package akka.first.app.mapreduce.actors

import akka.actor.Actor
import akka.first.app.mapreduce.{ReduceData, Result}

import scala.collection.mutable

class AggregateActor extends Actor {

  val finalMapReduce = new mutable.HashMap[String, Int]

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
    case result: Result => println(finalMapReduce.toString())
  }
}

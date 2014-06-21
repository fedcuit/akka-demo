package akka.first.app.mapreduce.actors

import akka.actor.{Actor, ActorRef}
import akka.first.app.mapreduce.{MapData, ReduceData}

class ReduceActor(aggregateActor: ActorRef) extends Actor {

  def reduce(mapData: MapData): ReduceData = {
    ReduceData(mapData.dataList.groupBy(_.word.toLowerCase).mapValues(_.size))
  }

  override def receive: Receive = {
    case mapData: MapData => aggregateActor ! reduce(mapData)
  }
}

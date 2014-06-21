package akka.first.app.mapreduce

import akka.actor.{ActorSystem, Props}
import akka.first.app.mapreduce.actors.MasterActor

sealed trait MapReduceMessage

case class WordCount(word: String, count: Int) extends MapReduceMessage

case class MapData(dataList: List[WordCount]) extends MapReduceMessage

case class ReduceData(reduceDataMap: Map[String, Int]) extends MapReduceMessage

case class Result() extends MapReduceMessage

object MapReduceApplication extends App {
  override def main(args: Array[String]) {
    val actorSystem = ActorSystem("MapReduceApp")
    val masterActor = actorSystem.actorOf(Props[MasterActor])

    masterActor ! "The quick brown fox tried to jump over the lazy dog and fell on the dog"
    masterActor ! "Dog is man's best friend"
    masterActor ! "Dog and Fox belong to the same family"

    Thread.sleep(500)

    masterActor ! new Result

    Thread.sleep(500)

    actorSystem.shutdown()
  }
}
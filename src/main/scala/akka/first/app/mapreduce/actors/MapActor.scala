package akka.first.app.mapreduce.actors

import akka.actor.{Actor, ActorRef}
import akka.first.app.mapreduce.{MapData, WordCount}

class MapActor(reduceActor: ActorRef) extends Actor {
  val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at ", "be", "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")

  def evaluateExpression(s: String): MapData = MapData(
    s.split( """\s+""").filterNot(STOP_WORDS_LIST.contains).map(new WordCount(_, 1)).toList
  )

  override def receive: Receive = {
    case msg: String => reduceActor ! evaluateExpression(msg)
  }
}

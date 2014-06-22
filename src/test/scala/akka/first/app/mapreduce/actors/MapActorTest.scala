package akka.first.app.mapreduce.actors

import akka.actor.{ActorSystem, Props}
import akka.first.app.mapreduce.{MapData, WordCount}
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSpecLike, Matchers}

import scala.concurrent.duration._

class MapActorTest extends TestKit(ActorSystem("MapActor")) with DefaultTimeout with ImplicitSender
with FunSpecLike with Matchers with BeforeAndAfterAll {
  val mapActor = system.actorOf(Props(new MapActor(testActor)))

  override def afterAll() {
    shutdown()
  }

  describe("MapActor") {
    it("should forward map data of a sentence to next actor") {
      within(500 millis) {
        mapActor ! "The quick brown fox"
        expectMsg(MapData(List(WordCount("The", 1), WordCount("quick", 1), WordCount("brown", 1), WordCount("fox", 1))))
      }
    }

    it("should forward map data of a sentence to next actor without stop words") {
      within(500 millis) {
        mapActor ! "The quick brown fox tried to jump over the"
        expectMsg(MapData(List(WordCount("The", 1), WordCount("quick", 1), WordCount("brown", 1), WordCount("fox", 1), WordCount("tried", 1), WordCount("jump", 1), WordCount("over", 1))))
      }
    }
  }
}
package akka.first.app.mapreduce.actors

import akka.actor.{ActorSystem, Props}
import akka.first.app.mapreduce.{MapData, ReduceData, WordCount}
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, FunSpecLike, Matchers}

import scala.collection.immutable.HashMap
import scala.concurrent.duration._

class ReduceActorTest extends TestKit(ActorSystem("ReduceActor")) with FunSpecLike with Matchers with BeforeAndAfterAll {
  val reduceActor = system.actorOf(Props(new ReduceActor(testActor)))

  override def afterAll() {
    shutdown()
  }

  describe("ReduceActor") {
    it("should forward reduce data to next actor") {
      within(500 millis) {
        reduceActor ! MapData(List(WordCount("dog", 1), WordCount("dog", 1), WordCount("fox", 1)))
        expectMsg(ReduceData(HashMap("dog" -> 2, "fox" -> 1)))
      }
    }

    it("should forward reduce data to next actor ignore case") {
      within(500 millis) {
        reduceActor ! MapData(List(WordCount("dog", 1), WordCount("Dog", 1), WordCount("fox", 1)))
        expectMsg(ReduceData(HashMap("dog" -> 2, "fox" -> 1)))
      }
    }
  }
}

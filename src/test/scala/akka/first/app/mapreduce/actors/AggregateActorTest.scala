package akka.first.app.mapreduce.actors

import akka.actor.{ActorSystem, Props}
import akka.first.app.mapreduce._
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, FunSpecLike, Matchers}

import scala.collection.immutable.HashMap
import scala.concurrent.duration._

class AggregateActorTest extends TestKit(ActorSystem("AggregateActor")) with FunSpecLike with Matchers with BeforeAndAfterAll {
  val aggregateActor = system.actorOf(Props(new AggregateActor(testActor)))

  override def afterAll() {
    shutdown()
  }

  describe("AggregateActor") {
    it("should aggregate several reduce data") {
      within(500 millis) {
        aggregateActor ! ReduceData(HashMap("dog" -> 2, "fox" -> 1))
        aggregateActor ! ReduceData(HashMap("dog" -> 2, "fox" -> 1, "jump" -> 1))
        aggregateActor ! ReduceData(HashMap("dog" -> 2, "fox" -> 1))

        aggregateActor ! new Result()
        expectMsg(collection.mutable.HashMap("dog" -> 6, "jump" -> 1, "fox" -> 3))
      }
    }
  }
}

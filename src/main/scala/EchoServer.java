import akka.actor.Actor;
import akka.actor._

val system = ActorSystem()

class EchoServer extends Actor {
    def receive = {
        case msg: String => println("echo " + msg)
    }
}

val echoServer = system.actorOf(Props[EchoServer])
echoServer ! "hi"

system.shutdown
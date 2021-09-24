package com.qbrainx.actor
import akka.actor.SupervisorStrategy.{Restart, Resume, stop}
import akka.actor.{Actor, ActorRef, OneForOneStrategy, PoisonPill, Props, SupervisorStrategy}
import MyActor.{EnterJson, Stop}
import com.qbrainx.model.Student
import com.qbrainx.model.StudentImplicits._
import com.qbrainx.repository.StudentDataBaseImpl._
import spray.json.JsonParser.ParsingException
import spray.json._
import scala.concurrent.duration.DurationInt
import scala.io.StdIn

object MyActor{
  case object EnterJson
  case object Stop

}
class MyActor extends Actor {

  val child: ActorRef =context.actorOf(Props[MyChild],"myChild")

  override def preStart(): Unit = {
    println(s"${self.path} - actor is started")
  }
  override def postStop(): Unit = {
    println(s"${self.path} - actor is stopped")
  }

  override def receive: Receive = {
    case EnterJson =>
      println(s"${self.path}:enter json value")
      val input: String =StdIn.readLine()
      child ! input
    case Stop=>
      context.stop(self)
    case _=>println("case mismatch")
  }

  override val supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
      case _: NullPointerException => Restart
      case _: IllegalArgumentException => Resume
      case _:ParsingException=>
        println("enter the correct Json string")
        stop
      case _: Exception=> stop
    }
 }

class MyChild extends Actor {

  override def receive: Receive = {
    case msg: String =>
      println(s"${self.path}-Json Received")
      val student: Student = msg.parseJson.convertTo[Student]
      println(s"${self.path}:Converted to Student")
      println(s"${self.path}:"+student)
      insert(student)
      println(s"${self.path}:inserted into Database")
    case _=>println("Invalid cannot be inserted to db")
  }

}


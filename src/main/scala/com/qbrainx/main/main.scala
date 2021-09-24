package com.qbrainx.main

import akka.actor.{ActorRef, ActorSystem, Props}
import com.qbrainx.actor.MyActor
import com.qbrainx.actor.MyActor.{EnterJson, Stop}

import scala.annotation.tailrec
import scala.io.StdIn


object main extends App {
  val system: ActorSystem = ActorSystem("myActorSystem")
  println("enter the no.of.students ")
  val a=StdIn.readLine()
  val actorJson: ActorRef = system.actorOf(Props[MyActor], "myActor")
  read(a.toInt)
  def read(a:Int): Unit ={
    @tailrec
    def read(a:Int, count:Int): Unit ={
      if(count==a)
        actorJson!Stop
        else {
        actorJson!EnterJson
        read(a,count=count+1)
      }
    }
   read(a,count=0)
  }
}

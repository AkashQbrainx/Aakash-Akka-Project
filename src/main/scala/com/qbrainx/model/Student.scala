package com.qbrainx.model

case class Student(name:String,age:Int,rollNumber:Int)

object Student{
  def apply(input: (String, Int,Int)): Student =
    input match {
      case (id, fName, lName) => apply(id, fName, lName)
    }
}

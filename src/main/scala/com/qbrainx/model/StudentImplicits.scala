package com.qbrainx.model
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

object StudentImplicits {
  implicit val rootJsonFormat: RootJsonFormat[Student] = jsonFormat3(Student.apply)
}

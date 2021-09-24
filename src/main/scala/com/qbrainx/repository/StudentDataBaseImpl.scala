package com.qbrainx.repository

import com.qbrainx.config.SlickConfig
import com.qbrainx.model.Student
import slick.lifted.ProvenShape
import scala.concurrent.Future

object StudentDataBaseImpl  {

  import com.qbrainx.config.SlickConfig.jdbcProfile.api._

  val db = SlickConfig.db

  private val query: TableQuery[schema] = TableQuery(new schema(_))

   def insert(student: Student): Future[Int] = db.run(query += student)


  import SlickConfig.jdbcProfile.api._

  val tableName = "Student"

  final class schema(tag: Tag) extends Table[Student](tag, tableName) {

    def name: Rep[String] = column[String]("name")

    def age: Rep[Int] = column[Int]("age")

    def rollNumber: Rep[Int] = column[Int]("rollNumber")

    override def * : ProvenShape[Student] = (name, age, rollNumber) <> (Student.apply, Student.unapply)


  }
}

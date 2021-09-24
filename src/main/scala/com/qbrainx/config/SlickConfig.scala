package com.qbrainx.config

import com.typesafe.config.{Config, ConfigFactory}
import slick.basic.DatabaseConfig
import slick.jdbc.{JdbcBackend, JdbcProfile}

object SlickConfig {

  val config: Config =ConfigFactory.load().getConfig("Akka-project")

  val dataConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig("slick", config)

  val jdbcProfile: JdbcProfile = dataConfig.profile

  val db: JdbcBackend#DatabaseDef = dataConfig.db

}

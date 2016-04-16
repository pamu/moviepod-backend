package models

import java.sql.Timestamp
import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * Created by pnagarjuna on 04/04/16.
  */
case class User(id: Long, name: String, email: String, key: String, updated: Timestamp, created: Timestamp)

@Singleton
class UserRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._
  val db = dbConfig.db
  private val Users = TableQuery[UserTable]

  def findById(id: Long): Future[User] = {
    db.run(Users.filter(_.id === id).result.head)
  }

  private class UserTable(tag: Tag) extends Table[User](tag, "USER") {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def name = column[String]("NAME")

    def email = column[String]("EMAIL")

    def key = column[String]("KEY")

    def updated = column[Timestamp]("UPDATED_AT")

    def created = column[Timestamp]("CREATED_AT")

    def * = (id, name, email, key, updated, created) <>(User.tupled, User.unapply)
  }

}


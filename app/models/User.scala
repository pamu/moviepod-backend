package models

import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * Created by pnagarjuna on 04/04/16.
  */
case class User(name: String, id: Option[Long] = None)

@Singleton
class UserRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._
  val db = dbConfig.db
  private val Users = TableQuery[UserTable]

  def findById(id: Long): Future[User] = {
    db.run(Users.filter(_.id === id).result.head)
  }

  def findByName(name: String): Future[User] = {
      db.run(Users.filter(_.name === name).result.head)
  }

  def create(user: User): Future[Long] = {
    db.run(Users.returning(Users.map(_.id)) += user)
  }

  private class UserTable(tag: Tag) extends Table[User](tag, "USER") {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def name = column[String]("NAME")

    def * = (name, id.?) <>(User.tupled, User.unapply)
  }

}


package models

import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  * Created by pnagarjuna on 18/04/16.
  */
case class Movie (name: String, imdb: String, userId: Long, id: Option[Long] = None)

@Singleton
class MovieRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._
  val db = dbConfig.db
  private val Movies = TableQuery[MovieTable]

  def findMoviesByUserId(userId: Long): Future[Seq[Movie]] = {
    db.run(Movies.filter(_.userId === userId).result)
  }

  private class MovieTable(tag: Tag) extends Table[Movie](tag, "MOVIE_TABLE") {
    def name = column[String]("NAME")
    def imdb = column[String]("IMDB")
    def userId = column[Long]("USER_ID")
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def * = (name, imdb, userId, id.?) <> (Movie.tupled, Movie.unapply)
  }
}

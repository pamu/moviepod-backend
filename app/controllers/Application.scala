package controllers

import com.google.inject.Inject
import models.{Movie, MovieRepo, UserRepo}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by pnagarjuna on 24/03/16.
  */
case class MovieData(userId: Long, name: String, imdb: String)

class Application @Inject()(override val userRepo: UserRepo, val movieRepo: MovieRepo) extends Controller with UserRepoProvider with Secured {

  def index = Action.async { implicit req =>
    req.session.get("id").map { id =>
      Future(Ok(views.html.index(id)(req.flash)))
    }.getOrElse {
      Future(Ok(views.html.index("-1")(req.flash)))
    }
  }

  def myMovies = withUser(parse.anyContent) { user => implicit req =>
    Future(Ok(views.html.myMovies("Movie Pod")(req.flash)))
  }

  implicit val movieWrites: Writes[Movie] = new Writes[Movie] {
    override def writes(o: Movie): JsValue =
      Json.obj(
        "id" -> o.id,
        "imdb" -> o.imdb,
        "name" -> o.name)
  }


  implicit val movieDataReads: Reads[MovieData] = (
    (JsPath \ "userId").read[Long] and
    (JsPath \ "name").read[String] and
      (JsPath \ "imdb").read[String]
    ) (MovieData.apply _)

  def addMovie = withUser(parse.json) { user => implicit req =>
    req.body.validate[MovieData] match {
      case success: JsSuccess[MovieData] => {
        movieRepo.addMovie(success.value, user.id.get).map { id =>
          Ok(Json.obj("success" -> true))
        }.recover { case th =>
          Ok(Json.obj("success" -> false))
        }
      }
      case error: JsError => Future(Ok(Json.obj("success" -> false)))
    }

  }

  def movieList = withUser(parse.json) { user => implicit req =>
    movieRepo.findMoviesByUserId(user.id.get).map { movies =>
      Ok(Json.obj("movies" -> Json.toJson(movies)))
    }
  }

  def logout = Action.async { implicit req =>
    Future(Redirect(routes.Application.index).withNewSession)
  }


}


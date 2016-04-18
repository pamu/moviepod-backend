package controllers

import com.google.inject.Inject
import models.{Movie, MovieRepo, UserRepo}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json, Writes}

/**
  * Created by pnagarjuna on 24/03/16.
  */
class Application @Inject()(override val userRepo: UserRepo, val movieRepo: MovieRepo) extends Controller with UserRepoProvider with Secured {

  def index = Action.async { implicit req =>
    Future(Ok(views.html.index("Movie Pod")(req.flash)))
  }

  def myMovies = Action.async { implicit req =>
    Future(Ok(views.html.myMovies("Movie Pod")(req.flash)))
  }

  implicit val movieWrites: Writes[Movie] = new Writes[Movie] {
    override def writes(o: Movie): JsValue =
      Json.obj(
        "id" -> o.id,
        "imdb" -> o.imdb,
        "name" -> o.name)
  }

  //implicit val movieReads =
  def addMovie = withUser(parse.json) { user => implicit req =>
    Future(Ok(""))
  }

  def movieList = withUser(parse.json) { user => implicit req =>
    movieRepo.findMoviesByUserId(user.id.get).map { movies =>
      Ok(Json.obj("movies" -> Json.toJson(movies)))
    }
  }


}


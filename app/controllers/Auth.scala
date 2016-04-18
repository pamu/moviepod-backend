package controllers

import com.google.inject.Inject
import models.{User, UserRepo}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
  * Created by pnagarjuna on 04/04/16.
  */
class Auth @Inject()(override val userRepo: UserRepo) extends Controller with UserRepoProvider {

  def login = Action { implicit req =>
    Ok(views.html.login(loginForm)(req.flash))
  }

  val loginForm = Form(
    single(
      "username" -> nonEmptyText(minLength = 1, maxLength = 20)
    )
  )

  def loginPost = Action.async { implicit req =>
    loginForm.bindFromRequest().fold(
      errors => Future(BadRequest(views.html.login(errors)(req.flash)).withNewSession.flashing("failure" -> "login failed")),
      data => {
        val userF = userRepo.findByName(data)
        userF.map { user =>
          Redirect(routes.Application.myMovies).withNewSession.withSession("id" -> user.id.toString)
        }.recoverWith { case th =>
          userRepo.create(User(data)).map { id  =>
            Redirect(routes.Application.myMovies).withNewSession.withSession("id" -> id.toString)
          }
        }
      }
    )
  }


}

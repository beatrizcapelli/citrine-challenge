package controllers

import actors.system.ActorSystems
import actors.utils.datatypes.ServiceTypes._
import akka.pattern.ask
import akka.util.Timeout
import javax.inject._
import org.slf4s.Logging
import play.api.Configuration
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class UnitsController @Inject()(
    cc: ControllerComponents,
    config: Configuration,
    actorSystems: ActorSystems)(implicit exec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  implicit val timeout: Timeout = Timeout(30.seconds)

  val unitsActor = actorSystems.system.actorSelection("/user/units")

  // TODO authentication
  def convert() = Action.async { request =>
    Try {
      val queryString = getQueryString(request)
      (unitsActor ? ConvertUnitsInput(queryString)).map {
        case output: ConvertUnitsOutput => {
          Ok(output.toJson).as(JSON)
        }
        case output: ConvertUnitsFailure => {
          BadRequest(output.toJson).as(JSON)
        }
        case m: Any => {
          log.error(
            s"convert units failed with unknown actor response - ${m.getClass.getName}")
          InternalServerError(RestError(m.getClass.getName, "unknown").toJson)
            .as(JSON)
        }
      }
    } match {
      case Success(value) => value
      case Failure(exception: IllegalArgumentException) => {
        Future(
          BadRequest(
            RestError(exception.getClass.getName, exception.getMessage).toJson)
            .as(JSON))
      }
      case Failure(exception) => {
        Future(
          InternalServerError(
            RestError(exception.getClass.getName, exception.getMessage).toJson)
            .as(JSON))
      }
    }
  }

  def getQueryString(request: Request[AnyContent]) = {
    val queryStringOption = request.getQueryString("units")
    if (queryStringOption.isEmpty || queryStringOption.get.isEmpty) {
      log.error(s"failed to parse request - units is mandatory input")
      throw new IllegalArgumentException("units is mandatory input")
    }
    queryStringOption.get
  }
}

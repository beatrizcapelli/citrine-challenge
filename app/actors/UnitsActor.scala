package actors

import actors.utils.ConversionUtils.convert
import actors.utils.datatypes.ReferenceTypes._
import actors.utils.datatypes.ServiceTypes._
import akka.actor.Actor
import org.slf4s.Logging
import play.api.Configuration

import scala.util.{Failure, Success, Try}

class UnitsActor(config: Configuration) extends Actor with Logging {

  val validUnits = loadValidUnits(config)

  override def receive = {
    case input: ConvertUnitsInput => {
      val originalSender = sender()
      log.info(s"start convert units ${input.units}")
      Try {
        convert(input.units, validUnits)
      } match {
        case Success(output) => {
          log.info(
            s"convert units ${input.units} completed successfully - ${output.toJson}")
          originalSender ! output
        }
        case Failure(exception) => {
          log.error(
            s"convert units ${input.units} failed with error - ${exception.getMessage}")
          originalSender ! ConvertUnitsFailure(input.units,
                                               exception.getClass.getName,
                                               exception.getMessage)
        }
      }
    }
  }
}

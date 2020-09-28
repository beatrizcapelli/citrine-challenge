package actors.utils.datatypes

import java.util.UUID

import actors.utils.JsonData

object ServiceTypes {

  case class ConvertUnitsInput(units: String)

  case class ConvertUnitsOutput(unit_name: String,
                                multiplication_factor: Double)
      extends JsonData

  case class ConvertUnitsFailure(unit_name: String,
                                 error: String,
                                 error_description: String)
      extends JsonData

  case class RestError(id: String,
                       error: Option[String],
                       error_description: Option[String])
      extends JsonData

  object RestError {
    def apply(error: String, error_description: String): RestError =
      new RestError(id = UUID.randomUUID.toString,
                    error = Some(error),
                    error_description = Some(error_description))
  }
}

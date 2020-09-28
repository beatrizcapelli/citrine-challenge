package actors.utils

import actors.utils.Constants._
import actors.utils.datatypes.ReferenceTypes.{Conversion, ValidUnits}
import actors.utils.datatypes.ServiceTypes.ConvertUnitsOutput
import org.slf4s.Logging

import scala.annotation.tailrec
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object ConversionUtils extends Logging {

  def convert(input: String, validUnits: ValidUnits) = {

    val units = identifyUnits(input)

    val conversionObjects = identifyConversions(units, validUnits)

    log.debug(
      s"input unit tokens are - ${units} and conversion objects are - ${conversionObjects}")

    val unit =
      convert(input, units, conversionObjects, conversionToString = _.unit)

    val expression =
      convert(input,
              units,
              conversionObjects,
              conversionToString = _.multiplication_factor.toString)

    val result = calculate(expression)

    ConvertUnitsOutput(unit_name = unit, multiplication_factor = result)
  }

  private def identifyUnits(units: String) = {
    // replace operators with empty string and split to identify only the units used
    units
      .replace(LEFT_PARENTHESIS, EMPTY)
      .replace(RIGHT_PARENTHESIS, EMPTY)
      .replace(MULTIPLIER, EMPTY)
      .replace(DIVIDER, EMPTY)
      .trim
      .split(EMPTY)
      .toList
  }

  private def identifyConversions(units: List[String],
                                  validUnits: ValidUnits) = {
    val conversionObjects = units.flatMap { unit =>
      {
        validUnits.units
          .find { value =>
            value.labels.contains(unit)
          }
          .map(_.conversion)
      }
    }

    if (units.size != conversionObjects.size) {
      throw new IllegalArgumentException(
        "some units were not found on reference data")
    }

    conversionObjects
  }

  @tailrec
  private final def convert(
      expression: String,
      units: List[String],
      conversionObjects: List[Conversion],
      conversionToString: Conversion => String): String = {
    if (units.isEmpty) expression
    else {
      val tokenHead = units.head
      val conversionHead = conversionObjects.head
      val updatedExpression =
        expression.replace(tokenHead, conversionToString(conversionHead))
      convert(updatedExpression,
              units.tail,
              conversionObjects.tail,
              conversionToString)
    }
  }

  private def calculate(expression: String) = {
    val toolbox = currentMirror.mkToolBox()
    toolbox.eval(toolbox.parse(expression)).toString.toDouble
  }

}

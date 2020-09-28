package actors.utils.datatypes

import actors.utils.JsonData
import com.typesafe.config.{Config, ConfigObject}
import org.slf4s.Logging
import play.api.{ConfigLoader, Configuration}

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

object ReferenceTypes extends Logging {

  case class ValidUnits(units: List[Unit]) extends JsonData

  object ValidUnits {
    implicit val configLoader: ConfigLoader[ValidUnits] =
      new ConfigLoader[ValidUnits] {
        def load(rootConfig: Config, path: String): ValidUnits = {
          val config = rootConfig.getConfig(path)
          val units = config
            .getObjectList("units")
            .asScala
            .map(configObjectToUnit)
            .toList

          ValidUnits(units)
        }
      }
  }

  case class Unit(quantity: String,
                  labels: List[String],
                  conversion: Conversion)

  case class Conversion(unit: String, multiplication_factor: Double)

  def configObjectToUnit = (configObject: ConfigObject) => {

    val quantity = configObject.toConfig.getString("quantity")

    val labels =
      configObject.toConfig.getStringList("labels").asScala.toList

    val conversionConfig =
      configObject.toConfig.getConfig("conversion")

    val conversionUnit = conversionConfig.getString("unit")

    val conversionMultFactor =
      conversionConfig.getDouble("multiplication_factor")

    Unit(quantity, labels, Conversion(conversionUnit, conversionMultFactor))
  }

  def loadValidUnits(config: Configuration) = {
    Try {
      config.get[ValidUnits]("application.valid_units")
    } match {
      case Success(value) =>
        log.info(s"load valid units successful - ${value.toJson}")
        value
      case Failure(exception) => {
        log.error(s"failed to load valid units - ${exception.getMessage}")
        ValidUnits(units = List.empty)
      }
    }
  }
}

package actors

import java.io.File

import actors.utils.datatypes.ServiceTypes.{
  ConvertUnitsFailure,
  ConvertUnitsInput,
  ConvertUnitsOutput
}
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
import org.slf4s.Logging
import play.api.Configuration
import scala.concurrent.duration._

class UnitsActorSpec
    extends TestKit(
      ActorSystem("Test",
                  ConfigFactory
                    .parseFile(new File("./conf/test.conf"))
                    .resolve()
                    .getConfig("application")))
    with ImplicitSender
    with AnyFlatSpecLike
    with Matchers
    with BeforeAndAfterAll
    with Logging {

  val config = ConfigFactory
    .parseFile(new File("./conf/test.conf"))
    .resolve()

  val unitsActor =
    system.actorOf(Props(new UnitsActor(Configuration(config)) {}), "units")

  it should "succeed to convert known units" in {
    within(60.seconds) {
      val input = ConvertUnitsInput("(degree/minute)")
      unitsActor ! input
      val output = expectMsgClass(classOf[ConvertUnitsOutput])

      output.unit_name shouldBe "(rad/s)"
      output.multiplication_factor shouldBe 0.0002908882086657216d
    }
  }

  it should "fail to convert unknown units" in {
    within(60.seconds) {
      val input = ConvertUnitsInput("(degree/year)")
      unitsActor ! input
      val output = expectMsgClass(classOf[ConvertUnitsFailure])

      output.unit_name shouldBe "(degree/year)"
      output.error shouldBe "java.lang.IllegalArgumentException"
      output.error_description shouldBe "some units were not found on reference data"
    }
  }

  it should "fail to convert wrong syntax formula" in {
    within(60.seconds) {
      val input = ConvertUnitsInput(")degree//(")
      unitsActor ! input
      val output = expectMsgClass(classOf[ConvertUnitsFailure])

      output.unit_name shouldBe ")degree//("
      output.error shouldBe "java.lang.IllegalArgumentException"
      output.error_description shouldBe "invalid input syntax"
    }
  }
}

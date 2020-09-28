package actors.system

import actors.UnitsActor
import akka.actor.{ActorSystem, Props}
import javax.inject.Inject
import org.slf4s.Logging
import play.api.{Configuration, Environment}

import scala.util.{Failure, Success, Try}

trait ActorSystems {
  def system: ActorSystem
}

class ActorSystemsImpl @Inject()(env:Environment, config: Configuration) extends ActorSystems with Logging {

  def systemName ="UnitsSystem"
  def system: ActorSystem = _system
  private val _system = ActorSystem(systemName)

  init()

  def init() = {
    Try {
      log.info("init")
      log.info("UnitsActor")
      _system.actorOf(Props(new UnitsActor(config)), "units")
    } match {
      case Success(_) => {log.info("init successful")}
      case Failure(exception) =>{
        log.error(s"init failed - error is ${exception.getMessage}")
      }
    }
  }
}
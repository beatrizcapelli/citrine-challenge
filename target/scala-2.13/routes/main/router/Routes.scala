// @GENERATOR:play-routes-compiler
// @SOURCE:/home/beatriz/citrine-challenge/citrine-challenge/conf/routes
// @DATE:Sun Sep 27 21:56:33 BRT 2020

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  UnitsController_0: controllers.UnitsController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    UnitsController_0: controllers.UnitsController
  ) = this(errorHandler, UnitsController_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, UnitsController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """units/si""", """controllers.UnitsController.convert"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_UnitsController_convert0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("units/si")))
  )
  private[this] lazy val controllers_UnitsController_convert0_invoker = createInvoker(
    UnitsController_0.convert,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UnitsController",
      "convert",
      Nil,
      "GET",
      this.prefix + """units/si""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_UnitsController_convert0_route(params@_) =>
      call { 
        controllers_UnitsController_convert0_invoker.call(UnitsController_0.convert)
      }
  }
}

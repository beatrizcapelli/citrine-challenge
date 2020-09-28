// @GENERATOR:play-routes-compiler
// @SOURCE:/home/beatriz/citrine-challenge/citrine-challenge/conf/routes
// @DATE:Sun Sep 27 21:56:33 BRT 2020

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers.javascript {

  // @LINE:6
  class ReverseUnitsController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def convert: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UnitsController.convert",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "units/si"})
        }
      """
    )
  
  }


}

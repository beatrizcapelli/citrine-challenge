// @GENERATOR:play-routes-compiler
// @SOURCE:/home/beatriz/citrine-challenge/citrine-challenge/conf/routes
// @DATE:Sun Sep 27 21:56:33 BRT 2020

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers {

  // @LINE:6
  class ReverseUnitsController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def convert(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "units/si")
    }
  
  }


}

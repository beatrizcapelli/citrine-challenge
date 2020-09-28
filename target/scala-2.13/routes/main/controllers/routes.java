// @GENERATOR:play-routes-compiler
// @SOURCE:/home/beatriz/citrine-challenge/citrine-challenge/conf/routes
// @DATE:Sun Sep 27 21:56:33 BRT 2020

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseUnitsController UnitsController = new controllers.ReverseUnitsController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseUnitsController UnitsController = new controllers.javascript.ReverseUnitsController(RoutesPrefix.byNamePrefix());
  }

}

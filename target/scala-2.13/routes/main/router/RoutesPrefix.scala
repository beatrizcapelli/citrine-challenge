// @GENERATOR:play-routes-compiler
// @SOURCE:/home/beatriz/citrine-challenge/citrine-challenge/conf/routes
// @DATE:Sun Sep 27 21:56:33 BRT 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}

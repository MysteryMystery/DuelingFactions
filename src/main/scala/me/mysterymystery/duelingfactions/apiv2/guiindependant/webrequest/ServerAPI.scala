package me.mysterymystery.duelingfactions.apiv2.guiindependant.webrequest

import me.mysterymystery.duelingfactions.apiv2.guiindependant.config.Config.webServerAddress
import me.mysterymystery.duelingfactions.apiv2.guiindependant.logging.Logger
import net.liftweb.json.JsonAST.JObject
import scalaj.http.Http
import net.liftweb.json._

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ServerAPI {
  import RequestParams.RequestParam

  //Future
  def heartBeat: Boolean = true

  def getAllClients = {
    me.mysterymystery.duelingfactions.apiv2.guiindependant.webrequest.ServerAPI.get(RequestParams.Clients)
    println(compactRender(get(RequestParams.Clients) \\ "data"))
    compactRender(get(RequestParams.Clients) \\ "data")
  }

  def get(param: RequestParam, id: Int = -1): JValue = {
    var request = if (id == -1)
      Http(s"$webServerAddress/${param.toString.toLowerCase}")
    else
      Http(s"$webServerAddress/${param.toString.toLowerCase}").param("id", id.toString)
    println(request.asString.body)
    parse(request.asString.body)
  }

  object RequestParams extends Enumeration {
    type RequestParam = Value
    val
        Decks,
        Clients,
        Games,
        Boards,
        Graveyards = Value
  }
}

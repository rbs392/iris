package controllers

import clients.{ElasticSearchClient, IrisHelperClient}
import play.api.mvc.{Action, Controller}
import core._
import utils._

import scalaj.http.Http

class Iris extends Controller{
  val host = sys.env.getOrElse("HOST", "localhost").mkString
  val port = sys.env.getOrElse("PORT", "9000").mkString
  val baseUrl = s"http://$host:$port/"
  val index = "iris"
  val utils = new IrisUtils()
  val iris = new IrisCore(index,
    new ElasticSearchClient(baseUrl, Http),
    new IrisHelperClient(Http),
    utils
  )
  iris.createMaping()

  def search(url: String) = Action {
    Ok(utils.formatOutputJSON(iris.search(url)._2)).as(JSON)
  }

  def searchRaw = Action { request =>
    val data = request.body.asText.getOrElse("")
    Ok(utils.formatOutputJSON(iris.searchRaw(data)._2)).as(JSON)
  }

  def add(url: String, metadata: String) = Action {
    Ok(iris.add(url, metadata)._2)
  }

  def addIndex(name: String) = Action {
    Ok(iris.addIndex(name)._2)
  }

  def health() = Action {
    Ok(iris.health())
  }
}

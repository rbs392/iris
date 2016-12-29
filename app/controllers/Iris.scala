package controllers

import play.api.mvc.{Action, Controller}
import core._
import play.api.libs.json.Json

/**
  * Created by bala on 29/12/16.
  */
class Iris extends Controller{
  def search(url: String) = Action {
    Ok(Main.search(url)._2).as(HTML)
  }
  def add(url: String) = Action {
    Ok(Main.add(url)._2)
  }
}

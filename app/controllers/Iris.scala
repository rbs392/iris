package controllers

import play.api.mvc.{Action, Controller}
import core._

/**
  * Created by bala on 29/12/16.
  */
class Iris extends Controller{
  def search(url: String) = Action {
    Ok(Main.search(url)._2)
  }
  def add(url: String) = Action {
    Ok(Main.add(url)._2)
  }
}

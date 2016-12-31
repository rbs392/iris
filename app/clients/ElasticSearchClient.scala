package clients

import models.IrisModel
import play.api.libs.json.{JsValue, Json}

import scalaj.http.BaseHttp

class ElasticSearchClient(BASEURL: String, http: BaseHttp) {

  def createMapping(index: String, schema: String) = {
    val url = BASEURL+index
    val result = http(url).put(schema).asString
    (result.code, result.body)
  }

  def add(index: String, data: IrisModel) = {
    val url = BASEURL+index+"/iris/"
    println(url)
    val result = http(url).postData(data.toString).asString
    (result.code, result.body)
  }

  def search(index: String, query: String) = {
    val url = BASEURL+index+"/iris/_search"
    val result = http(url).postData(query).asString
    (result.code, result.body)
  }
}

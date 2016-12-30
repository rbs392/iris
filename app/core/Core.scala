package core

/**
  * Created by bala on 29/12/16.
  */
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import play.api.GlobalSettings
import play.api.libs.json.{JsLookupResult, JsObject, JsValue, Json}
import sun.misc.BASE64Encoder

import scalaj.http.{Http, HttpConstants}

object Main {
  val mapper = new ObjectMapper()
  def createMaping(index: String) = {
    val maping =
      """
        |{
        |    "test": {
        |        "properties": {
        |            "my_img": {
        |                "type": "image",
        |                "feature": {
        |                    "CEDD": {
        |                        "hash": "BIT_SAMPLING"
        |                    },
        |                    "JCD": {
        |                        "hash": ["BIT_SAMPLING", "LSH"]
        |                    },
        |                    "FCTH": {}
        |                },
        |                "metadata": {
        |                    "jpeg.image_width": {
        |                        "type": "string",
        |                        "store": "yes"
        |                    },
        |                    "jpeg.image_height": {
        |                        "type": "string",
        |                        "store": "yes"
        |                    }
        |                }
        |            }
        |        }
        |    }
        |}
      """.stripMargin
    val url = s"http://build.indix.tv:4920/$index"
    val result = Http(url).postData(maping).asString
    (result.code, result.body, url)
  }

  def getImgFromUrl(url: String) = {
    val response  = Http(url)
    val img = response.asBytes.body
    new BASE64Encoder().encode(img).replaceAll("[\n\r]", "")
  }

  def add(url: String) = {
    val data = getImgFromUrl(url)
    val result = Http("http://build.indix.tv:4920/test/test").postData(s"""{"my_img": "$data"}""").asString
    (result.code, result.body)
  }

  def search(url: String) = {
    val data = getImgFromUrl(url)
    val query =
      s"""
         |{
         |    "query": {
         |        "image": {
         |            "my_img": {
         |                "feature": "CEDD",
         |                "image": "$data",
         |                "hash": "BIT_SAMPLING",
         |                "boost": 2.1,
         |                "limit": 100
         |            }
         |        }
         |    }
         |}
      """.stripMargin
    val result = Http("http://build.indix.tv:4920/test/_search").postData(query).asString
    val body = Json.parse(result.body)
    val hits = body\"hits"\"hits"

    val images = hits.as[List[JsValue]].map(x => {
      val json = x.asInstanceOf[JsValue]
      val score = (json\"_score").as[JsValue].toString
      val id = (json\"_id").as[JsValue].toString
      val img = (json\"_source"\"my_img").as[JsValue].toString().replaceAll("\"", "")
      s"""
         |<p>
         |  <h4>id: $id</h4>
         |  <h4>score: $score</h4>
         |  <img src=\"data:image/jpeg;base64,$img"/>
         | </p>""".stripMargin
    })
    (result.code, images.foldLeft("")((a, c) => a+"\n"+c))
  }
}

case class hit(_index: String, _type: String, _id: String, _score: String, _source: _source)
case class _source(my_image: String)
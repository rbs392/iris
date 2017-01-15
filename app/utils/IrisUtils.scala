package utils

import play.api.libs.json.{JsValue, Json}

class IrisUtils {
  def generateQuery(rawImage: String) = {
    s"""
       |{
       |    "query": {
       |        "image": {
       |            "rawImage": {
       |                "feature": "CEDD",
       |                "image": "$rawImage",
       |                "hash": "BIT_SAMPLING",
       |                "boost": 2.1,
       |                "limit": 100
       |            }
       |        }
       |    }
       |}
      """.stripMargin
  }

  def formatImage(rawJson: String) = {
    val body = Json.parse(rawJson)
    val hits = body\"hits"\"hits"
    val images = hits.as[List[JsValue]].map(x => {
      val score = (x\"_score").as[JsValue].toString
      val id = (x\"_id").as[JsValue].toString
      val img = (x\"_source"\"rawImage").as[JsValue].toString().replaceAll("\"", "")
      s"""
         |<p>
         |  <h4>id: $id</h4>
         |  <h4>score: $score</h4>
         |  <img src=\"data:image/jpeg;base64,$img"/>
         | </p>""".stripMargin
    })
    images.foldLeft("")((a, c) => a+"\n"+c)
  }

  def formatOutputJSON(rawJson: String) = {
    val body = Json.parse(rawJson)
    val hits = body\"hits"\"hits"
    val output = hits.as[List[JsValue]].map(x => {
      val id = (x\"_id").as[JsValue].toString
      val score = (x\"_score").as[JsValue].toString
      val metadata = (x\"_source").as[JsValue].toString
      s"""
         |{
         |  "id": $id,
         |  "metadata": $metadata,
         |  "score": $score
         |}
       """.stripMargin
    })
    s"""[${output.mkString(",")}]"""
  }
}

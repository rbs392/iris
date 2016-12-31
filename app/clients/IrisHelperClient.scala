package clients

import play.api.libs.json.{JsValue, Json}
import sun.misc.BASE64Encoder

import scalaj.http.BaseHttp

class IrisHelperClient(http: BaseHttp) {

  def getImgFromUrl(url: String) = {
    val response  = http(url)
    val img = response.asBytes.body
    new BASE64Encoder().encode(img).replaceAll("[\n\r]", "")
  }

}

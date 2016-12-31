package models

import play.api.libs.json.{JsLookup, JsLookupResult, JsObject, JsValue}

class IrisModel(
        mpid: String,
        oldPid: String,
        pid: String,
        imageUrl: String,
        additionalImageUrls: String,
        title: String,
        url: String,
        canonicalUrl: String,
        categoryIdPath: String,
        categoryNamePath: String,
        storeId: String,
        storeName: String,
        countryCode: String,
        rawImage: String
){

  override def toString: String =
    s"""
      |{
      |  "mpid": "$mpid",
      |  "oldPid": "$oldPid",
      |  "pid": "$pid",
      |  "imageUrl": "$imageUrl",
      |  "additionalImageUrls": "$additionalImageUrls",
      |  "title": "$title",
      |  "url": "$url",
      |  "canonicalUrl": "$canonicalUrl",
      |  "categoryIdPath": "$categoryIdPath",
      |  "categoryNamePath": "$categoryNamePath",
      |  "storeId": "$storeId",
      |  "storeName": "$storeName",
      |  "countryCode": "$countryCode",
      |  "rawImage": "$rawImage"
      |}
    """.stripMargin

  def this(rawImage: String, metadata: JsValue) = this(
    (metadata\"mpid").asOpt[String].getOrElse(""),
    (metadata\"oldPid").asOpt[String].getOrElse(""),
    (metadata\"pid").asOpt[String].getOrElse(""),
    (metadata\"imageUrl").asOpt[String].getOrElse(""),
    (metadata\"additionalImageUrls").asOpt[String].getOrElse(""),
    (metadata\"title").asOpt[String].getOrElse(""),
    (metadata\"url").asOpt[String].getOrElse(""),
    (metadata\"canonicalUrl").asOpt[String].getOrElse(""),
    (metadata\"categoryIdPath").asOpt[String].getOrElse(""),
    (metadata\"categoryNamePath").asOpt[String].getOrElse(""),
    (metadata\"storeId").asOpt[String].getOrElse(""),
    (metadata\"storeName").asOpt[String].getOrElse(""),
    (metadata\"countryCode").asOpt[String].getOrElse(""),
    rawImage
  )
}
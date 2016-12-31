package schemas

object Schemas {
  val IRIS =
    """
        |{
        |   "mappings": {
        |     "iris": {
        |       "properties": {
        |         "rawImage": {
        |           "type": "image",
        |           "feature": {
        |             "CEDD": { "hash": "BIT_SAMPLING" },
        |             "JCD": { "hash": ["BIT_SAMPLING", "LSH"] },
        |             "FCTH": {}
        |           },
        |           "metadata": {
        |             "jpeg.image_width": { "type": "string" },
        |             "jpeg.image_height": { "type": "string" }
        |           }
        |         },
        |         "mpid": { "type": "string" },
        |         "oldPid": { "type": "string" },
        |         "pid": { "type": "string" },
        |         "imageUrl": { "type": "string" },
        |         "additionalImageUrls": { "type": "string" },
        |         "title": { "type": "string" },
        |         "url": { "type": "string" },
        |         "canonicalUrl": { "type": "string" },
        |         "categoryIdPath": { "type": "string" },
        |         "categoryNamePath": { "type": "string" },
        |         "storeId": { "type": "string" },
        |         "storeName": { "type": "string" },
        |         "countryCode": { "type": "string" }
        |       }
        |     }
        |   }
        |}
    """.stripMargin
}
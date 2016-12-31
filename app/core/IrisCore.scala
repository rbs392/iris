package core

import utils._
import schemas.Schemas
import models.IrisModel
import clients.{ElasticSearchClient, IrisHelperClient}
import play.api.libs.json.Json

class IrisCore(index: String, client: ElasticSearchClient, helper: IrisHelperClient, utils: IrisUtils) {

  def createMaping() = client.createMapping(index, Schemas.IRIS)

  def add(url: String, metadata: String) = {
    val rawImage = helper.getImgFromUrl(url)
    val data = new IrisModel(rawImage,Json.parse(metadata))
    client.add(index, data)
  }

  def search(url: String) = {
    val rawImage = helper.getImgFromUrl(url)
    val query = utils.generateQuery(rawImage)
    client.search(index, query)
  }

  def addIndex(name: String) = {
    client.createMapping(name, Schemas.IRIS)
  }

  def health() = {
    "ok"
  }
}
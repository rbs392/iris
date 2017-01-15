package core

import clients.{ElasticSearchClient, IrisHelperClient}
import models.IrisModel
import org.mockito.{Matchers, Mockito}
import play.api.libs.json.Json
import schemas.Schemas
import utils.IrisUtils

class IrisCoreSpec extends IrisSpec {
  val index = "test"
  val url = "http://test_image.com"
  val metadata =
    """
      |{
      | "title": "test_title",
      | "mpid": "asdf123234"
      |}
    """.stripMargin
  val metadataModel = new IrisModel("image_data", Json.parse(metadata))
  val client = Mockito.mock(classOf[ElasticSearchClient])
  val helper = Mockito.mock(classOf[IrisHelperClient])
  val utils = Mockito.mock(classOf[IrisUtils])

  val irisCore = new IrisCore(index, client, helper, utils)
  describe("Iris core") {
    describe("create maping") {
      it("Should return 200 on success") {
        Mockito.when(client.createMapping(index, Schemas.IRIS)).thenReturn((200, "success"))
        assert(irisCore.createMaping() === (200, "success"))
      }
      it("Should return 500 on failure") {
        Mockito.when(client.createMapping(index, Schemas.IRIS)).thenReturn((500, "failure"))
        assert(irisCore.createMaping() === (500, "failure"))
      }
    }

    describe("add data") {
      it("should add data and return success") {
        Mockito.when(helper.getImgFromUrl(url)).thenReturn("image_data")
        Mockito.when(client.add(Matchers.anyString(), Matchers.any(classOf[IrisModel])))
          .thenReturn((200, "success"))
        assert(irisCore.add(url, metadata) === (200, "success"))
      }
      it("should return 500 on failure ") {
        Mockito.when(helper.getImgFromUrl(url)).thenReturn("image_data")
        Mockito.when(client.add(Matchers.anyString(),Matchers.any(classOf[IrisModel])))
          .thenReturn((500, "failure"))
        assert(irisCore.add(url, metadata) === (500, "failure"))
      }
    }

    describe("search data") {
      it("should search data and return success") {
        Mockito.when(helper.getImgFromUrl(url)).thenReturn("image_data")
        Mockito.when(utils.generateQuery("image_data")).thenReturn("query_data")
        Mockito.when(client.search(Matchers.anyString(), Matchers.anyString())).thenReturn((200, "success"))
        assert(irisCore.search(url) === (200, "success"))
      }
      it("should return 500 on failure ") {
        Mockito.when(helper.getImgFromUrl(url)).thenReturn("image_data")
        Mockito.when(utils.generateQuery("image_data")).thenReturn("query_data")
        Mockito.when(client.search(Matchers.anyString(), Matchers.anyString())).thenReturn((500, "failure"))
        assert(irisCore.search(url) === (500, "failure"))
      }
    }

    describe("search raw data") {
      it("should search data and return success") {
        Mockito.when(utils.generateQuery("image_data")).thenReturn("query_data")
        Mockito.when(client.search(Matchers.anyString(), Matchers.anyString())).thenReturn((200, "success"))
        assert(irisCore.searchRaw(url) === (200, "success"))
      }
      it("should return 500 on failure ") {
        Mockito.when(helper.getImgFromUrl(url)).thenReturn("image_data")
        Mockito.when(utils.generateQuery("image_data")).thenReturn("query_data")
        Mockito.when(client.search(Matchers.anyString(), Matchers.anyString())).thenReturn((500, "failure"))
        assert(irisCore.searchRaw("testbase") === (500, "failure"))
      }
    }
  }
}

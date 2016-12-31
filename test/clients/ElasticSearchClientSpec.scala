package clients

import core.IrisSpec
import models.IrisModel
import org.mockito.Mockito
import scalaj.http._

class ElasticSearchClientSpec extends IrisSpec {
  val index = "my_index"
  val schema = "my_schema"
  val baseurl = "test_base_url"
  val resBody = "response from mock"
  val mockReq = Mockito.mock(classOf[HttpRequest])
  val successRes = new HttpResponse[String](resBody, 200, Map())
  val failureRes = new HttpResponse[String](resBody, 500, Map())
  val mockHttp = Mockito.mock(classOf[BaseHttp])
  val client = new ElasticSearchClient(baseurl, mockHttp)
  val mockData = Mockito.mock(classOf[IrisModel])

  def setResponse(res: HttpResponse[String]) = {
  }
  def setUpMocks(res: HttpResponse[String], postData: String, onHttp: String) = {
    Mockito.when(mockReq.asString).thenReturn(res)
    Mockito.when(mockReq.postData(postData)).thenReturn(mockReq)
    Mockito.when(mockHttp.apply(onHttp)).thenReturn(mockReq)
  }

  def setUpMocks(res: HttpResponse[String], postData: IrisModel, data: String, onHttp: String) = {
    Mockito.when(mockReq.asString).thenReturn(res)
    Mockito.when(mockData.toString).thenReturn(data)
    Mockito.when(mockReq.postData(data)).thenReturn(mockReq)
    Mockito.when(mockHttp.apply(onHttp)).thenReturn(mockReq)
  }

  describe("Elastic search client") {
    describe("create maping") {
      it("should return 200 on successful creation") {
        setUpMocks(successRes, schema, baseurl+index)
        assert(client.createMapping(index, schema) === (200, resBody))
      }

      it("should return 500 on failure ") {
        setUpMocks(failureRes, schema, baseurl+index)
        assert(client.createMapping(index, schema) === (500, resBody))
      }
    }

    describe("add image") {
      it("should return 200 on success of add image") {
        setUpMocks(successRes, mockData, "data", baseurl+index)
        assert(client.add(index, mockData) === (200, resBody))
      }

      it("should return 500 on failure of add image") {
        setUpMocks(failureRes, mockData, "data", baseurl+index)
        assert(client.add(index, mockData) === (500, resBody))
      }
    }


    describe("search image") {
      it("should return 200 on success of search image") {
        val data = "data"
        setUpMocks(successRes, data, baseurl+index+"/_search")
        assert(client.search(index, data) === (200, resBody))
      }

      it("should return 500 on failure of search image") {
        val data = "data string"
        setUpMocks(failureRes, data, baseurl+index+"/_search")
        assert(client.search(index, data) === (500, resBody))
      }
    }
  }

}

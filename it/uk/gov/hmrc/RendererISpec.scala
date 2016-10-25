package uk.gov.hmrc.emailrenderertemplate

import org.scalatest.concurrent.ScalaFutures
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.ws.WS
import uk.gov.hmrc.play.it.{ExternalService, MicroServiceEmbeddedServer, ServiceSpec}
import uk.gov.hmrc.play.test.WithFakeApplication

class RendererISpec extends ServiceSpec
  with WithFakeApplication
  with ScalaFutures {

  "email renderer" should {
    "render the html and text content for sample1 template" in {
      val result = WS.url(resource("/templates/sample1")).
        post(Json.obj("parameters" -> Json.obj("name" -> "Dr. Bruce Banner"))).futureValue
      result.status shouldBe 200
    }
  }

  class TestServer(override val testName: String = "RendererControllerISpec") extends MicroServiceEmbeddedServer {
    override protected val externalServices: Seq[ExternalService] = Seq.empty
  }

  override protected val server = new TestServer()
}

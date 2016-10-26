package uk.gov.hmrc.emailrenderertemplate

import play.api.libs.json.Json
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec, WsScalaTestClient}
import play.api.test.Helpers._

class RendererISpec extends PlaySpec with OneServerPerSuite with WsScalaTestClient {

  "email renderer" should {
    "render the html and text content for sample1 template" in {
      val result = await(wsUrl("/templates/sample1").
        post(Json.obj("parameters" -> Json.obj("name" -> "Dr. Bruce Banner"))))
      result.status mustBe 200
    }
  }
}

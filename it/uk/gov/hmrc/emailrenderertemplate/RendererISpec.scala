package uk.gov.hmrc.emailrenderertemplate

import org.scalatest.words.EmptyWord
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec, WsScalaTestClient}
import play.api.http.Status
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.test.Helpers._

class RendererISpec extends PlaySpec with OneServerPerSuite with WsScalaTestClient {
  protected implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]
  "email renderer" should {
    "render the html and text content for sample1 template" in {
      val result = await(wsUrl("/templates/sample1").
        post(Json.obj("parameters" -> Json.obj("name" -> "Dr. Bruce Banner"))))

      result.status mustBe Status.OK
      (result.json \ "fromAddress").as[String] mustBe "<sample1> @gov.uk"
      (result.json \ "subject").as[String] mustBe "New message for sample1 template"
      (result.json \ "service").as[String] mustBe "REPLACE WITH YOUR TAX DOMAIN"
      (result.json \ "plain").as[String] mustNot be(new EmptyWord())
      (result.json \ "html").as[String] mustNot be(new EmptyWord())
      (result.json \ "priority").as[String] mustBe "standard"
    }

    "return NOT_FOUND if template does not exist" in {
      val result = await(wsUrl("/templates/notExistTemplate").
        post(Json.obj("parameters" -> Json.obj("name" -> "Dr. Bruce Banner"))))
      result.status mustBe NOT_FOUND
      (result.json \ "reason").as[String] mustBe "Missing template with id: notExistTemplate"
    }

    "return BAD_REQUEST if required parameter is not in the request body" in {
      val result = await(wsUrl("/templates/sample1").
        post(Json.obj("parameters" -> Json.obj("noName" -> "Dr. Bruce Banner"))))
      result.status mustBe BAD_REQUEST
      (result.json \ "reason").as[String] mustBe "Failed to render template due to: key not found: name"
    }
  }
}

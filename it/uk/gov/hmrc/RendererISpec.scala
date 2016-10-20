package uk.gov.hmrc.emailrenderertemplate

import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.libs.json.Json
import play.api.libs.ws.WS
import play.api.test.Helpers._
import play.test.WithServer
import uk.gov.hmrc.play.test.WithFakeApplication

class RendererISpec extends PlaySpec with WithFakeApplication with ScalaFutures with BeforeAndAfterEach with OneServerPerSuite {

  import play.api.Play.current

  "email renderer" should {
    "render the html and text content for sample1 template" in new TestCase {
      val result = WS.url(s"$baseUrl//templates/sample1").post(Json.obj("parameters" -> Json.obj("name" -> "Dr. Bruce Banner"))).futureValue
      result.status mustBe (200)
    }
  }


  trait TestCase extends WithServer {
    val baseUrl = s"""http://localhost:$testServerPort"""
  }
}

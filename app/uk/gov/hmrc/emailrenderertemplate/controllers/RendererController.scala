/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.emailrenderertemplate.controllers

import play.api.libs.json._
import play.api.mvc._
import uk.gov.hmrc.emailrenderertemplate.controllers.model.Params
import uk.gov.hmrc.emailrenderertemplate.controllers.model.Params._
import uk.gov.hmrc.emailrenderertemplate.domain.{NoTemplateFoundError, RenderTemplateError}
import uk.gov.hmrc.emailrenderertemplate.services.RendererService
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

object RendererController extends RendererController {
	val rendererService = RendererService
}

trait RendererController extends BaseController {

	def rendererService: RendererService

	def render(templateId: String) = Action.async(parse.json) { implicit request =>
		withJsonBody[Params] { params =>
			Future.successful(rendererService.render(templateId, params) match {
				case Right(renderResult) => Ok(Json.toJson(renderResult))
				case Left(error: NoTemplateFoundError) =>
					NotFound(Json.toJson(error))
				case Left(error: RenderTemplateError) =>
					BadRequest(Json.toJson(error))
			})
		}
	}
}



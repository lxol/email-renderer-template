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

package uk.gov.hmrc.emailrenderertemplate.preview


import play.api.libs.json.Json
import play.api.mvc.{Action, Results}
import play.twirl.api.Html
import uk.gov.hmrc.emailrenderertemplate.domain.Template
import uk.gov.hmrc.emailrenderertemplate.services.{RendererService, TemplateLocator}
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

object PreviewController extends PreviewController {
  val templates: Seq[Template] = TemplateLocator.templates
  val rendererService: RendererService = RendererService
  val templateParams: TemplateParams = TemplateParams
}

trait PreviewController extends BaseController {

  def templates: Seq[Template]

  def templateParams: TemplateParams

  def rendererService: RendererService

  def preview = Action.async { implicit request =>
    Future.successful(Ok(views.html.preview(templates)))
  }

  def previewHtml(templateId: String) = Action.async { implicit request =>
    rendererService.render(templateId, templateParams.paramsFor(templateId)) match {
      case Right(renderResult) => Future.successful(Ok(Html(renderResult.html)))
      case Left(error) => Future.successful(BadRequest(Json.toJson(error)))
    }
  }

  def previewHtmlSource(templateId: String) = Action.async { implicit request =>
    rendererService.render(templateId, templateParams.paramsFor(templateId)) match {
      case Right(renderResult) => Future.successful(Ok(renderResult.html))
      case Left(error) => Future.successful(BadRequest(Json.toJson(error)))
    }
  }

  def previewText(templateId: String) = Action.async { implicit request =>
    rendererService.render(templateId, templateParams.paramsFor(templateId)) match {
      case Right(renderResult) => Future.successful(Ok(renderResult.plain))
      case Left(error) => Future.successful(BadRequest(Json.toJson(error)))
    }
  }
}

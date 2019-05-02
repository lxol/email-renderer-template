/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.emailrenderertemplate.services

import uk.gov.hmrc.emailrenderertemplate.controllers.model.Params
import uk.gov.hmrc.emailrenderertemplate.domain._

object RendererService extends RendererService {
  override def templateLocator: TemplateLocator = TemplateLocator
}

trait RendererService {

  def templateLocator: TemplateLocator

  def render(templateId: String, params: Params): Either[ErrorMessage, RenderResult] =
    templateLocator.findTemplate(templateId).map { template =>
      Template.render(template, params)
    }.getOrElse(Left(NoTemplateFoundError(templateId)))

}

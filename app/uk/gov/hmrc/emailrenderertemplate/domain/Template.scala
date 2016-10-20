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

package uk.gov.hmrc.emailrenderertemplate.domain

import play.twirl.api.{Appendable, Html, Txt}
import uk.gov.hmrc.emailrenderertemplate.controllers.model.Params

abstract class Template {

  def templateId: String

  def fromAddress: String

  def subject: Subject

  def body: Body
}

object Template {
  def render(template: Template, params: Params): Either[ErrorMessage, RenderResult] =
    try {
      Right(RenderResult(
        template.body.text(params).toString,
        template.body.html(params).toString,
        template.fromAddress,
        template.subject.text))
    } catch {
      case ex: Throwable => Left(RenderTemplateError(ex.getMessage))
    }
}

case class Subject(text: String)

case class Body(
                 html: Params => Appendable[Html],
                 text: Params => Appendable[Txt]
               )

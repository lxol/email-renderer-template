/*
 * Copyright 2018 HM Revenue & Customs
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

import play.api.libs.json.{JsValue, Json, Writes}

sealed trait ErrorMessage {
  def msg: String
}
case class NoTemplateFoundError(templateId: String) extends ErrorMessage{ val msg = templateId }
case class RenderTemplateError(reason: String) extends ErrorMessage { val msg = reason }

object ErrorMessage{
  implicit val writes: Writes[ErrorMessage] = new Writes[ErrorMessage] {
    override def writes(error: ErrorMessage): JsValue = error match {
      case e:NoTemplateFoundError => Json.obj("reason" -> s"Missing template with id: ${e.msg}")
      case other => Json.obj("reason" -> s"Failed to render template due to: ${other.msg}")
    }
  }
}
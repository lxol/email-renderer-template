import sbt._

import play.sbt.PlayImport._
import play.core.PlayVersion

object MicroServiceBuild extends Build with MicroService {

  val appName = "email-renderer-template"

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  def apply()= Seq(
    ws,
    "uk.gov.hmrc"             %% "microservice-bootstrap"    % "10.4.0",
    "uk.gov.hmrc"             %% "domain"                    % "5.2.0",
    "uk.gov.hmrc"             %% "hmrctest"                  % "3.5.0-play-25" % "test,it",
    "org.scalatest"           %% "scalatest"                 % "3.0.7" % "test,it",
    "org.pegdown"             %  "pegdown"                   % "1.6.0" % "test,it",
    "com.typesafe.play"       %% "play-test"                 % PlayVersion.current % "test,it",
    "org.scalatestplus.play"  %% "scalatestplus-play"        % "2.0.1" % "test,it"
  )
}

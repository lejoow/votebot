package com.lejoow.votebot.vote.api

import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import play.api.libs.json.{Format, Json}
import com.lejoow.votebot.commons.JsonUtils._
import com.lejoow.votebot.vote.api.GenderEnum.Gender

/**
  * Created by Joo on 6/5/2017.
  */
case class VoterDto(residentId: String, city: String, gender: Gender, age: Int)

object VoterDto {
  implicit val format: Format[VoterDto] = Json.format
}

object GenderEnum extends Enumeration {
  val Male, Female = Value
  type Gender = Value
  implicit val format: Format[Gender] = enumFormat(this)
  implicit val pathParamSerializer: PathParamSerializer[Gender] =
    PathParamSerializer.required("gender")(withName)(_.toString)
}

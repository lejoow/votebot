package com.lejoow.votebot.candidate.api

import com.lejoow.votebot.commons.JsonUtils.enumFormat
import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class CandidateDto(name:String, party:String)

object CandidateDto {
  implicit val format: Format[CandidateDto] = Json.format
}

package com.lejoow.votebot.candidate.api

import julienrf.json.derived
import play.api.libs.json.{Format, Json, __}

/**
  * Created by Joo on 6/5/2017.
  */
case class CandidateDto(candidateNumber: Int, residentId: String, name: String, party: String)

object CandidateDto {
  implicit val format: Format[CandidateDto] = Json.format
}

sealed trait CandidateMsg

case class CandidateRegisteredMsg(candidateDto: CandidateDto) extends CandidateMsg

object CandidateRegisteredMsg {
  implicit val format: Format[CandidateRegisteredMsg] = Json.format
}

object CandidateMsg {
  implicit val format: Format[CandidateMsg] =
    derived.flat.oformat((__ \ "type").format[String])
}




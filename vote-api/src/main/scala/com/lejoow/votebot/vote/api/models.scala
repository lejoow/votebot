package com.lejoow.votebot.vote.api

import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class VoterDto(residentId: String, age: Int, postCode: String)

object VoterDto {
  implicit val format: Format[VoterDto] = Json.format
}

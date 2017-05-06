package com.lejoow.votebot.vote.impl.commons

import com.lejoow.votebot.vote.api.GenderEnum.Gender
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 5/5/2017.
  */

case class Voter(residentId: String, city: String, gender: Gender, age: Int)

object Voter {
  implicit val format: Format[Voter] = Json.format
}


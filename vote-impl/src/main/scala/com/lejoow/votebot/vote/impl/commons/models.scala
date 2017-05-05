package com.lejoow.votebot.vote.impl.commons

import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 5/5/2017.
  */

case class Voter(residentId:String, age:Int, postCode:String)

object Voter {
  implicit val format: Format[Voter] = Json.format
}


package com.lejoow.votebot.collection.api

import java.util.UUID

import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 7/5/2017.
  */
case class VoteView(candidateNumber: Int, voterId: UUID, city: String, gender: String, age: Int)

object VoteView {
  implicit val format: Format[VoteView] = Json.format
}


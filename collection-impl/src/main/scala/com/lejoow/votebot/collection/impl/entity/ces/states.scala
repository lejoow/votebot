package com.lejoow.votebot.collection.impl.entity.ces

import java.util.UUID

import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class VoterCounter(registeredVoters:Set[UUID])

object VoterCounter {

  val emptyState = VoterCounter(registeredVoters = Set())

  implicit val format: Format[VoterCounter] = Json.format

}
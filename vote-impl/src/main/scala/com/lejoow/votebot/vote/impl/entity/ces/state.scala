package com.lejoow.votebot.vote.impl.entity.ces

import java.util.UUID

import com.lejoow.votebot.vote.impl.commons.Voter
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 5/5/2017.
  */
case class VoterState(voterCode:Option[UUID], voter:Option[Voter], voted:Boolean)

object VoterState {

  val emptyState = VoterState(voterCode = None, voter = None, voted = false)

  implicit val format: Format[VoterState] = Json.format
}
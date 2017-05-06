package com.lejoow.votebot.candidate.impl.candidateEntity.ces

import java.util.UUID

import com.lejoow.votebot.commons.JsonUtils.singletonFormat
import com.lejoow.votebot.vote.impl.entity.ces.VoterCmd
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateCmd

case object GetCandidateCmd extends CandidateCmd with ReplyType[CandidateState] {
  implicit val format: Format[GetCandidateCmd.type] = singletonFormat(GetCandidateCmd)
}


case class RegisterVoterCmd(residentId: String,
                            city: String,
                            gender: Gender,
                            age: Int) extends VoterCmd with ReplyType[Option[UUID]]

object RegisterVoterCmd {
  implicit val format: Format[RegisterVoterCmd] = Json.format
}
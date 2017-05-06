package com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.Candidate
import com.lejoow.votebot.commons.JsonUtils.singletonFormat
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateRegistryCmd

case object GetCandidateRegistryCmd extends CandidateRegistryCmd with ReplyType[CandidateRegistryState] {
  implicit val format: Format[GetCandidateRegistryCmd.type] = singletonFormat(GetCandidateRegistryCmd)
}

case class RegisterCandidateCmd(residentId: String, name: String, party: String) extends CandidateRegistryCmd with ReplyType[Candidate]

object RegisterCandidateCmd {
  implicit val format: Format[RegisterCandidateCmd] = Json.format
}
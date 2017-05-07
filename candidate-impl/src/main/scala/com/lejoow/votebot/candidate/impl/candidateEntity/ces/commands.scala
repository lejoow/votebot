package com.lejoow.votebot.candidate.impl.candidateEntity.ces

import akka.Done
import com.lejoow.votebot.commons.JsonUtils.singletonFormat
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateCmd

case object GetCandidateCmd extends CandidateCmd with ReplyType[CandidateState] {
  implicit val format: Format[GetCandidateCmd.type] = singletonFormat(GetCandidateCmd)
}

case class CreateCandidateCmd(candidate: Candidate) extends CandidateCmd with ReplyType[Done]

object CreateCandidateCmd {
  implicit val format: Format[CreateCandidateCmd] = Json.format
}
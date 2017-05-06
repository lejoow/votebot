package com.lejoow.votebot.candidate.impl.candidateNumberEntity.ces

import java.util.UUID

import com.lejoow.votebot.commons.JsonUtils.singletonFormat
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateNumberCmd

case object GetCurrentCandidateNumberCmd extends CandidateNumberCmd with ReplyType[Int] {
  implicit val format: Format[GetCurrentCandidateNumberCmd.type] = singletonFormat(GetCurrentCandidateNumberCmd)
}

case object GetNextAvailableCandidateNumberCmd extends CandidateNumberCmd with ReplyType[Int] {
  implicit val format: Format[GetNextAvailableCandidateNumberCmd.type] = singletonFormat(GetNextAvailableCandidateNumberCmd)
}
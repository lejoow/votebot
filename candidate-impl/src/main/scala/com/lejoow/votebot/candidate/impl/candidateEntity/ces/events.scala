package com.lejoow.votebot.candidate.impl.candidateEntity.ces

import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces.CandidateRegisteredEvt
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateEvt extends AggregateEvent[CandidateEvt] {
  override def aggregateTag: AggregateEventTagger[CandidateEvt] = CandidateEvt.Tag
}

object CandidateEvt {
  val NumShards = 4
  val Tag = AggregateEventTag.sharded[CandidateEvt](NumShards)
  //val Tag = AggregateEventTag[CandidateEvt]
}

case class CandidateCreatedEvt(candidate:Candidate) extends CandidateEvt

object CandidateCreatedEvt {
  implicit val format: Format[CandidateCreatedEvt] = Json.format
}


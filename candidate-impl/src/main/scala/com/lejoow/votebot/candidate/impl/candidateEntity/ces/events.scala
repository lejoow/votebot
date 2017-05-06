package com.lejoow.votebot.candidate.impl.candidateEntity.ces

import java.util.UUID
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateEvt extends AggregateEvent[CandidateEvt] {
  override def aggregateTag: AggregateEventTagger[CandidateEvt] = CandidateEvt.Tag
}

object CandidateEvt {
  //val NumShards = 4
  //val Tag = AggregateEventTag.sharded[VoteEvt](NumShards)
  val Tag = AggregateEventTag[CandidateEvt]
}

case class CandidateRegisteredEvt(candidate:Candidate) extends CandidateEvt

object CandidateRegisteredEvt {
  implicit val format: Format[CandidateRegisteredEvt] = Json.format
}

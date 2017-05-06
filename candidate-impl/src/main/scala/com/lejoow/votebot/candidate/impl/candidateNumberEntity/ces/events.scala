package com.lejoow.votebot.candidate.impl.candidateNumberEntity.ces

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.CandidateEvt
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateNumberEvt extends AggregateEvent[CandidateNumberEvt] {
  override def aggregateTag: AggregateEventTagger[CandidateNumberEvt] = CandidateNumberEvt.Tag
}

object CandidateNumberEvt {
  //val NumShards = 4
  //val Tag = AggregateEventTag.sharded[VoteEvt](NumShards)
  val Tag = AggregateEventTag[CandidateNumberEvt]
}

case class CandidateNumberAssignedEvt(candidate:Candidate) extends CandidateNumberEvt

object CandidateNumberAssignedEvt {
  implicit val format: Format[CandidateNumberEvt] = Json.format
}

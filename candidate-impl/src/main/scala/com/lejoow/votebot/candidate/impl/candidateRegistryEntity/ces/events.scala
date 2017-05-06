package com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.Candidate
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait CandidateRegistryEvt extends AggregateEvent[CandidateRegistryEvt] {
  override def aggregateTag: AggregateEventTagger[CandidateRegistryEvt] = CandidateRegistryEvt.Tag
}

object CandidateRegistryEvt {
  val NumShards = 4
  val Tag = AggregateEventTag.sharded[CandidateRegistryEvt](NumShards)
  //val Tag = AggregateEventTag[CandidateRegistryEvt]
}

case class CandidateRegisteredEvt(candidate: Candidate) extends CandidateRegistryEvt

object CandidateRegisteredEvt {
  implicit val format: Format[CandidateRegisteredEvt] = Json.format
}

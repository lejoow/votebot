package com.lejoow.votebot.collection.impl.entity.ces

import java.util.UUID

import com.lejoow.votebot.collection.impl.commons.Vote
import com.lejoow.votebot.collection.impl.commons.VoteRejectionReason.VoteRejectionReason
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait VoteCollectorEvt extends AggregateEvent[VoteCollectorEvt] {
  override def aggregateTag: AggregateEventTagger[VoteCollectorEvt] = VoteCollectorEvt.Tag
}

object VoteCollectorEvt {
  //val NumShards = 4
  //val Tag = AggregateEventTag.sharded[VoteEvt](NumShards)
  val Tag = AggregateEventTag[VoteCollectorEvt]
}

case class CandidateRegisteredEvt(candidateNumber:Int) extends VoteCollectorEvt

object CandidateRegisteredEvt {
  implicit val format: Format[CandidateRegisteredEvt] = Json.format
}

case class VoteAcceptedEvt(vote:Vote) extends VoteCollectorEvt

object VoteAcceptedEvt {
  implicit val format: Format[VoteAcceptedEvt] = Json.format
}

case class VoteRejectedEvt(vote:Vote, reason: VoteRejectionReason) extends VoteCollectorEvt

object VoteRejectedEvt {
  implicit val format: Format[VoteRejectedEvt] = Json.format
}

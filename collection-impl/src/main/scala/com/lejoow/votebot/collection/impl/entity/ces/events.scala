package com.lejoow.votebot.collection.impl.entity.ces

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait VoterCounterEvt extends AggregateEvent[VoterCounterEvt] {
  override def aggregateTag: AggregateEventTagger[VoterCounterEvt] = VoterCounterEvt.Tag
}

object VoterCounterEvt {
  //val NumShards = 4
  //val Tag = AggregateEventTag.sharded[VoteEvt](NumShards)
  val Tag = AggregateEventTag[VoterCounterEvt]
}
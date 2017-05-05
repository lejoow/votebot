package com.lejoow.votebot.vote.impl.entity.ces

import java.util.UUID

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 5/5/2017.
  */
/**
  * Lagom provides some utilities for helping create sharded tags.
  * To create the sharded tags, define the number of shards in a static variable, as the shards tag, and implement the aggregateTag method to return the shards tag:
  */
sealed trait VoterEvt extends AggregateEvent[VoterEvt] {
  override def aggregateTag: AggregateEventTagger[VoterEvt] = VoterEvt.Tag
}

object VoterEvt {
  //val NumShards = 4
  //val Tag = AggregateEventTag.sharded[VoteEvt](NumShards)
  val Tag = AggregateEventTag[VoterEvt]
}

case class VoterRegisteredEvt(voterCode:UUID, residentId:String, age:Int, postCode:String) extends VoterEvt

object VoterRegisteredEvt{
  implicit val format: Format[VoterRegisteredEvt] = Json.format
}

package com.lejoow.votebot.collection.impl

import com.lejoow.votebot.collection.impl.commons.{Vote, VoterInfo}
import com.lejoow.votebot.collection.impl.entity.ces._
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

/**
  * Created by Joo on 6/5/2017.
  */

object CollectionSerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    /**
      * Models
      */
    JsonSerializer[VoteCollector],
    JsonSerializer[VoterInfo],
    JsonSerializer[Vote],

    /**
      * Commands
      */
    JsonSerializer[GetLiveVoteCountCmd.type],
    JsonSerializer[CollectVoteCmd],
    JsonSerializer[GetCandidateNumbersCmd.type],
    JsonSerializer[RegisterCandidateCmd],


    /**
      * Events
      */
    JsonSerializer[VoteAcceptedEvt],
    JsonSerializer[VoteRejectedEvt],
    JsonSerializer[CandidateRegisteredEvt]
    //JsonSerializer[VoterCounterEvt],

    /**
      * States
      */

  )
}

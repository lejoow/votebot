package com.lejoow.votebot.collection.impl

import com.lejoow.votebot.collection.impl.entity.ces.{VoterCounter, VoterCounterCmd, VoterCounterEvt}
import com.lejoow.votebot.vote.impl.commons.Voter
import com.lejoow.votebot.vote.impl.entity.ces.{RegisterVoterCmd, VoterRegisteredEvt, VoterState}
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

/**
  * Created by Joo on 6/5/2017.
  */

object CollectionSerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    /**
      * Models
      */
    JsonSerializer[VoterCounter],

    /**
      * Commands
      */
    JsonSerializer[VoterCounterCmd],


    /**
      * Events
      */
    JsonSerializer[VoterCounterEvt],

    /**
      * States
      */
    JsonSerializer[VoterCounter]
  )
}

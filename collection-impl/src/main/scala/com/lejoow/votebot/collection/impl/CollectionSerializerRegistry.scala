package com.lejoow.votebot.collection.impl

import com.lejoow.votebot.collection.impl.entity.ces.{GetVoterCountCmd, VoterCounter, VoterCounterCmd, VoterCounterEvt}
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
    JsonSerializer[GetVoterCountCmd.type ],


    /**
      * Events
      */
    //JsonSerializer[VoterCounterEvt],

    /**
      * States
      */
    JsonSerializer[VoterCounter]
  )
}

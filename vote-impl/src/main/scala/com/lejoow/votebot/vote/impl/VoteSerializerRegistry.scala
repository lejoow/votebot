package com.lejoow.votebot.vote.impl

import com.lejoow.votebot.vote.impl.commons.Voter
import com.lejoow.votebot.vote.impl.entity.ces.{RegisterVoterCmd, VoterRegisteredEvt, VoterState}
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

object VoteSerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    /**
      * Models
      */
    JsonSerializer[Voter],

    /**
      * Commands
      */
    JsonSerializer[RegisterVoterCmd],


    /**
      * Events
      */
    JsonSerializer[VoterRegisteredEvt],

    /**
      * States
      */
    JsonSerializer[VoterState]
  )
}

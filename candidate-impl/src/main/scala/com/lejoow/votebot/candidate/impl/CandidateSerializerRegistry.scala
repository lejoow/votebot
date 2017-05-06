package com.lejoow.votebot.candidate.impl

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.{Candidate, CandidateRegisteredEvt, CandidateState}
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

/**
  * Created by Joo on 6/5/2017.
  */
object CandidateSerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    /**
      * Models
      */
    JsonSerializer[Candidate],

    /**
      * Commands
      */
    JsonSerializer[RegisterCandidateCmd],


    /**
      * Events
      */
    JsonSerializer[CandidateRegisteredEvt],

    /**
      * States
      */
    JsonSerializer[CandidateState]
  )
}

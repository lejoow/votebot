package com.lejoow.votebot.candidate.impl

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.{Candidate, CandidateCreatedEvt, CandidateState, CreateCandidateCmd}
import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces.{CandidateRegisteredEvt, CandidateRegistryState, RegisterCandidateCmd}
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
    JsonSerializer[CreateCandidateCmd],


    /**
      * Events
      */
    JsonSerializer[CandidateRegisteredEvt],
    JsonSerializer[CandidateCreatedEvt],

    /**
      * States
      */
    JsonSerializer[CandidateState],
    JsonSerializer[CandidateRegistryState]
  )
}

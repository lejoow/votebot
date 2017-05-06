package com.lejoow.votebot.collection.impl

import akka.stream.scaladsl.Flow
import com.lejoow.votebot.candidate.api.{CandidateMsg, CandidateRegisteredMsg, CandidateService}
import com.lejoow.votebot.collection.impl.entity.VoteCollectorEntity
import com.lejoow.votebot.collection.impl.entity.ces.RegisterCandidateCmd
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateEventSubscriber(persistentEntityRegistry: PersistentEntityRegistry,
                               candidateService: CandidateService) {

  candidateService
    .candidateTopic
    .subscribe
    .atLeastOnce(Flow[CandidateMsg].mapAsync(1) {
      case msg: CandidateRegisteredMsg =>
        voteCollectorEntityRef.ask(RegisterCandidateCmd(msg.candidateDto.candidateNumber))
    })

  private def voteCollectorEntityRef = persistentEntityRegistry.refFor[VoteCollectorEntity]("VOTE_COLLECTOR_ENTITY")
}

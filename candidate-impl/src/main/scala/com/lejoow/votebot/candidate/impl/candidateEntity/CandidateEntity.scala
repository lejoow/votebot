package com.lejoow.votebot.candidate.impl.candidateEntity

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.{CandidateCmd, CandidateEvt, CandidateState}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateEntity extends PersistentEntity{
  override type Command = CandidateCmd
  override type Event = CandidateEvt
  override type State = CandidateState

  override def initialState = CandidateState.emptyState

  override def behavior: Behavior ={
    case None => notCreated
    case Some(candidate) =>
  }

}

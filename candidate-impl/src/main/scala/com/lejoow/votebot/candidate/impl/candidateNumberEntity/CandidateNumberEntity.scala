package com.lejoow.votebot.candidate.impl.candidateNumberEntity

import com.lejoow.votebot.candidate.impl.candidateNumberEntity.ces.{CandidateNumberEvt, CandidateNumberState}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateNumberEntity extends PersistentEntity{
  override type Command = CandidateNumberEntity
  override type Event = CandidateNumberEvt
  override type State = CandidateNumberState

  override def initialState: CandidateNumberEntity.this.type = ???

  override def behavior: Behavior = ???
}

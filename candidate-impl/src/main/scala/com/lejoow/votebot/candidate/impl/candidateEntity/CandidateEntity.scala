package com.lejoow.votebot.candidate.impl.candidateEntity

import akka.Done
import com.lejoow.votebot.candidate.impl.candidateEntity.ces._
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateEntity extends PersistentEntity {
  override type Command = CandidateCmd
  override type Event = CandidateEvt
  override type State = CandidateState

  override def initialState = CandidateState.emptyState

  override def behavior: Behavior = {
    case state: CandidateState if state.candidate.isEmpty => notCreated
    case state: CandidateState if state.candidate.isDefined => getCandidateCommand
  }

  private val getCandidateCommand = Actions().onReadOnlyCommand[GetCandidateCmd.type, CandidateState] {
    case (GetCandidateCmd, ctx, state) => ctx.reply(state)
  }

  private val notCreated = getCandidateCommand orElse Actions().onCommand[CreateCandidateCmd, Done] {
    case (cmd: CreateCandidateCmd, ctx, state) =>
      ctx.thenPersist(CandidateCreatedEvt(cmd.candidate))(_ => ctx.reply(Done))
  }.onEvent {
    case (evt: CandidateCreatedEvt, state) => state.copy(candidate = Some(evt.candidate), isActive = true)
  }

}

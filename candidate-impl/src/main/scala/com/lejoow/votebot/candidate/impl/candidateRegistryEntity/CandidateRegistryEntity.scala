package com.lejoow.votebot.candidate.impl.candidateRegistryEntity

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.Candidate
import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces._
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateRegistryEntity extends PersistentEntity {
  override type Command = CandidateRegistryCmd
  override type Event = CandidateRegistryEvt
  override type State = CandidateRegistryState

  override def initialState = CandidateRegistryState.emptyState

  override def behavior: Behavior = {
    case state => handleCommand(state)
  }

  private val getCandidateRegistryCommand = Actions().onReadOnlyCommand[GetCandidateRegistryCmd.type, CandidateRegistryState] {
    case (GetCandidateRegistryCmd, ctx, state) => ctx.reply(state)
  }

  private def handleCommand(state: CandidateRegistryState): Actions = {
    getCandidateRegistryCommand orElse
      Actions().onCommand[RegisterCandidateCmd, Candidate] {
        case (cmd: RegisterCandidateCmd, ctx, _) =>
          state.candidates.find(_.residentId == cmd.residentId) match {
            case Some(existing) =>
              //candidate already exists and registered!
              ctx.reply(existing)
              ctx.done
            case None =>
              val candidateNumber = state.candidates.size + 1
              val candidate = Candidate(candidateNumber = candidateNumber, residentId = cmd.residentId, name = cmd.name, party = cmd.party)
              ctx.thenPersist(CandidateRegisteredEvt(candidate))(_ => ctx.reply(candidate))
          }
      }.onEvent {
        case (evt: CandidateRegisteredEvt, state) => state.addCandidateToMap(evt.candidate)
      }
  }


}

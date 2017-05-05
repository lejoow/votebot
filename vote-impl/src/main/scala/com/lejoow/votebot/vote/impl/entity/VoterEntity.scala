package com.lejoow.votebot.vote.impl.entity

import java.util.UUID

import com.lejoow.votebot.vote.impl.commons.Voter
import com.lejoow.votebot.vote.impl.entity.ces._
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

/**
  * Created by Joo on 5/5/2017.
  */
class VoterEntity extends PersistentEntity {
  override type Command = VoterCmd
  override type Event = VoterEvt
  override type State = VoterState

  override def initialState = VoterState.emptyState

  override def behavior: Behavior = {
    case state: VoterState if state.voter.isEmpty => notRegistered
    case state: VoterState if state.voter.isDefined => registered
  }

  private val getVoterCommand = Actions().onReadOnlyCommand[GetVoterCmd.type, VoterState] {
    case (GetVoterCmd, ctx, state) => ctx.reply(state)
  }

  private val notRegistered = {
    getVoterCommand orElse Actions().onCommand[RegisterVoterCmd, Option[UUID]] {
      case (cmd: RegisterVoterCmd, ctx, state) =>
        val voterCode = UUID.randomUUID()
        val evt = VoterRegisteredEvt(
          voterCode = voterCode,
          residentId = cmd.residentId,
          age = cmd.age,
          postCode = cmd.postCode)
        ctx.thenPersist(evt)(_ => ctx.reply(Some(voterCode)))
    }.onEvent {
      case (evt: VoterRegisteredEvt, state) =>
        val voter = Voter(evt.residentId, evt.age, evt.postCode)
        state.copy(voterCode = Some(evt.voterCode), voter = Some(voter))
    }
  }

  private val registered = {
    getVoterCommand orElse Actions().onReadOnlyCommand[RegisterVoterCmd, Option[UUID]] {
      case (cmd: RegisterVoterCmd, ctx, state) =>
        ctx.reply(None)
    }
  }

}

package com.lejoow.votebot.collection.impl.entity

import com.lejoow.votebot.collection.impl.entity.ces.{GetVoterCountCmd, VoterCounter, VoterCounterCmd, VoterCounterEvt}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

/**
  * Created by Joo on 6/5/2017.
  */
class VoterCounterEntity extends PersistentEntity {
  override type Command = VoterCounterCmd
  override type Event = VoterCounterEvt
  override type State = VoterCounter

  override def initialState = VoterCounter.emptyState

  override def behavior: Behavior = {
    case counter =>
  }

  private val getCounterCommand = Actions().onReadOnlyCommand[GetVoterCountCmd.type, Long] {
    case (GetVoterCountCmd, ctx, state) => ctx.reply(state.registeredVoters.size)
  }
}

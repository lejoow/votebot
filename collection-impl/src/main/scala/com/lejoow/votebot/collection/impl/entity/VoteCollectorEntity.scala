package com.lejoow.votebot.collection.impl.entity

import akka.Done
import com.lejoow.votebot.collection.impl.commons.VoteRejectionReason
import com.lejoow.votebot.collection.impl.entity.ces.{GetCandidateNumbersCmd, _}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

/**
  * Created by Joo on 6/5/2017.
  */
class VoteCollectorEntity extends PersistentEntity {

  override type Command = VoteCollectorCmd
  override type Event = VoteCollectorEvt
  override type State = VoteCollector

  override def initialState = VoteCollector.emptyState

  override def behavior: Behavior = {
    case collector => handleCommands(collector)
  }

  private val getCommand = Actions()
    .onReadOnlyCommand[GetLiveVoteCountCmd.type, Long] {
    case (GetLiveVoteCountCmd, ctx, state) =>
      ctx.reply(state.acceptedVotes.size.toLong)
  }.onReadOnlyCommand[GetCandidateNumbersCmd.type, Seq[Int]] {
    case (GetCandidateNumbersCmd, ctx, state) =>
      ctx.reply(state.validCandidates.toSeq)
  }

  private def handleCommands(collector: VoteCollector) = {
    val acceptedVotes = collector.acceptedVotes
    val validCandidates = collector.validCandidates

    getCommand orElse Actions().onCommand[CollectVoteCmd, Done] {
      case (CollectVoteCmd(vote), ctx, state) if validCandidates.isEmpty =>
        val evt = VoteRejectedEvt(vote = vote, reason = VoteRejectionReason.NoCandidateRegistered)
        ctx.thenPersist(evt)(_ => ctx.reply(Done))
      case (CollectVoteCmd(vote), ctx, state) if acceptedVotes.contains(vote) =>
        val evt = VoteRejectedEvt(vote = vote, reason = VoteRejectionReason.DuplicateVote)
        ctx.thenPersist(evt)(_ => ctx.reply(Done))
      case (CollectVoteCmd(vote), ctx, state) if !validCandidates.contains(vote.candidateNumber) =>
        val evt = VoteRejectedEvt(vote = vote, reason = VoteRejectionReason.InvalidCandidateNumber)
        ctx.thenPersist(evt)(_ => ctx.reply(Done))
      case (CollectVoteCmd(vote), ctx, state) if acceptedVotes.contains(vote) && validCandidates.contains(vote.candidateNumber) =>
        val evt = VoteAcceptedEvt(vote = vote)
        ctx.thenPersist(evt)(_ => ctx.reply(Done))
    }.onCommand[RegisterCandidateCmd, Done] {
      case (cmd: RegisterCandidateCmd, ctx, state) =>
        if (state.validCandidates.contains(cmd.candidateNumber)) {
          ctx.reply(Done)
          ctx.done // no more events!
        } else {
          val evt = CandidateRegisteredEvt(cmd.candidateNumber)
          ctx.thenPersist(evt)(_ => ctx.reply(Done))
        }
    }.onEvent {
      case (evt: CandidateRegisteredEvt, state) =>
        val updatedCandidates = state.validCandidates + evt.candidateNumber
        state.copy(validCandidates = updatedCandidates)
      case (evt: VoteRejectedEvt, state) => state.addRejectedVote(evt.vote, evt.reason)
      case (evt: VoteAcceptedEvt, state) => state.addAcceptedVote(evt.vote)
    }
  }
}

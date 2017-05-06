package com.lejoow.votebot.collection.impl.entity

import java.util.UUID

import akka.Done
import com.lejoow.votebot.collection.impl.CollectionSerializerRegistry
import com.lejoow.votebot.collection.impl.commons._
import com.lejoow.votebot.collection.impl.entity.ces._
import com.lejoow.votebot.testkit.EntitySpec
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry

/**
  * Created by Joo on 6/5/2017.
  */
class VoteCollectorEntitySpec extends EntitySpec[VoteCollectorCmd, VoteCollectorEvt, VoteCollector] {


  private val voterInfoAtTest = VoterInfo(
    voterId = UUID.randomUUID(),
    city = "Seoul",
    gender = GenderEnum.Female,
    age = 30
  )

  private val voteAtTest = Vote(
    candidateNumber = 1,
    voterInfo = voterInfoAtTest
  )
  private val voteCmd = CollectVoteCmd(voteAtTest)

  override def serializationRegistry: JsonSerializerRegistry = CollectionSerializerRegistry

  override def entityToTest = (_ => new VoteCollectorEntity)

  override def entityId: String = "VOTE_COLLECTOR"

  override def defaultEntitySetup = (driver => driver.run(voteCmd))

  "The VoteCollectorEntity" should {
    "reject the vote when there is no registered candidates" in withNoEntitySetup { driver =>
      val outcome = driver.run(voteCmd)
      outcome.events should contain only VoteRejectedEvt(vote = voteAtTest, reason = VoteRejectionReason.NoCandidateRegistered)
      outcome.replies should contain only Done
      outcome.state shouldBe VoteCollector(
        validCandidates = Set(),
        acceptedVotes = Set(),
        rejectedVotes = Seq(RejectedVote(voteAtTest, VoteRejectionReason.NoCandidateRegistered)))
    }
  }
}

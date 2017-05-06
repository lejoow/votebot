package com.lejoow.votebot.candidate.impl.candidateRegistryEntity

import com.lejoow.votebot.candidate.impl.CandidateSerializerRegistry
import com.lejoow.votebot.candidate.impl.candidateEntity.ces.Candidate
import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces._
import com.lejoow.votebot.testkit.EntitySpec
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateRegistryEntitySpec extends EntitySpec[CandidateRegistryCmd, CandidateRegistryEvt, CandidateRegistryState] {

  private val candidateAtTest = Candidate(
    candidateNumber = 1,
    residentId = "alkafsdjlk",
    name = "Lee Joo Won",
    party = "Scala"
  )

  private val registerCmd = RegisterCandidateCmd(
    residentId = candidateAtTest.residentId,
    name = candidateAtTest.name,
    party = candidateAtTest.party
  )

  override def serializationRegistry: JsonSerializerRegistry = CandidateSerializerRegistry

  override def entityToTest = (_ => new CandidateRegistryEntity)

  override def entityId: String = "CANDIDATE_REGISTRY"

  override def defaultEntitySetup = (driver => driver.run(registerCmd))

  "The CandidateRegistryEntity " should {
    "register a new candidate and return the candidate number" in withNoEntitySetup { driver =>
      val outcome = driver.run(registerCmd)
      outcome.replies should have size 1

      outcome.replies should contain only candidateAtTest
      outcome.events should contain only CandidateRegisteredEvt(candidate = candidateAtTest)
      outcome.state shouldBe CandidateRegistryState(
        candidates = Seq(candidateAtTest)
      )
    }
  }
}

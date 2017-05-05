package com.lejoow.votebot.vote.impl.entity

import java.util.UUID

import com.lejoow.votebot.testkit.EntitySpec
import com.lejoow.votebot.vote.impl.VoteSerializerRegistry
import com.lejoow.votebot.vote.impl.commons.Voter
import com.lejoow.votebot.vote.impl.entity.ces._
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry

/**
  * Created by Joo on 6/5/2017.
  */
class VoterEntityTest extends EntitySpec[VoterCmd, VoterEvt, VoterState] {


  override def serializationRegistry: JsonSerializerRegistry = VoteSerializerRegistry

  override def entityToTest = (_ => new VoterEntity)

  override def entityId: String = UUID.randomUUID().toString

  private val voterAtTest = Voter(
    residentId = entityId,
    age = 30,
    postCode = "554479"
  )

  private val registerCmd = RegisterVoterCmd(
    residentId = voterAtTest.residentId,
    age = voterAtTest.age,
    postCode = voterAtTest.postCode
  )

  override def defaultEntitySetup = (driver => driver.run(registerCmd))

  "The VoterEntity" should {
    "register a new voter and return voterCode" in withNoEntitySetup { driver =>
      val outcome = driver.run(registerCmd)
      outcome.replies should have size 1
      outcome.replies.head shouldBe an[Option[UUID]]
      val voterCode = outcome.replies.head.asInstanceOf[Option[UUID]].get

      outcome.events should contain only VoterRegisteredEvt(
        voterCode = voterCode,
        residentId = registerCmd.residentId,
        age = registerCmd.age,
        postCode = registerCmd.postCode
      )

      outcome.state shouldBe VoterState(
        voterCode = Some(voterCode),
        voter = Some(voterAtTest),
        voted = false
      )
    }
  }
}

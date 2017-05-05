package com.lejoow.votebot.vote.impl

import java.util.UUID

import akka.{Done, NotUsed}
import com.lejoow.votebot.vote.api.{VoteService, VoterDto}
import com.lejoow.votebot.vote.impl.commons.VoterRegistrationException
import com.lejoow.votebot.vote.impl.entity.VoterEntity
import com.lejoow.votebot.vote.impl.entity.ces.RegisterVoterCmd
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.NotFound
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.lightbend.lagom.scaladsl.server.ServerServiceCall

import scala.concurrent.ExecutionContext

/**
  * Created by Joo on 5/5/2017.
  */
class VoteServiceImpl(registry: PersistentEntityRegistry)
                     (implicit ec: ExecutionContext) extends VoteService {

  override def registerVoter(): ServiceCall[VoterDto, UUID] = ServerServiceCall { dto =>

    val residentId = dto.residentId
    val age =dto.age
    val postCode = dto.postCode

    val cmd = RegisterVoterCmd(residentId, age, postCode)
    voterEntityRef(residentId).ask(cmd).map {
      case Some(voterCode) => voterCode
      case None => throw VoterRegistrationException("Voter already registered")
    }

  }

  //override def vote(voterCode: UUID, candidateName: String): ServiceCall[NotUsed, Done] = ???

  private def voterEntityRef(residentId: String) = registry.refFor[VoterEntity](residentId)
}

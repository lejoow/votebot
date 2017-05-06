package com.lejoow.votebot.candidate.impl

import akka.NotUsed
import com.lejoow.votebot.candidate.api.{CandidateDto, CandidateMsg, CandidateRegisteredMsg, CandidateService}
import com.lejoow.votebot.candidate.impl.candidateEntity.CandidateEntity
import com.lejoow.votebot.candidate.impl.candidateEntity.ces.CandidateEvt
import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.CandidateRegistryEntity
import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces.{CandidateRegisteredEvt, CandidateRegistryEvt, RegisterCandidateCmd}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.lightbend.lagom.scaladsl.server.ServerServiceCall

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateServiceImpl(registry: PersistentEntityRegistry)
                          (implicit ec: ExecutionContext) extends CandidateService {

  private val CANDIDATE_REGISTRY_ID = "CANDIDATE_REGISTRY"

  override def registerCandidate(residentId: String, name: String, party: String): ServiceCall[NotUsed, CandidateDto] = ServerServiceCall { _ =>
    for {
      candidate <- candidateRegistryEntityRef.ask(RegisterCandidateCmd(residentId = residentId, name = name, party = party))
    } yield CandidateDto(candidate.candidateNumber, candidate.residentId, candidate.name, candidate.party)
  }

  private def candidateEntityRef(candidateName: String) = registry.refFor[CandidateEntity](candidateName)

  private def candidateRegistryEntityRef = registry.refFor[CandidateRegistryEntity](CANDIDATE_REGISTRY_ID)

  override def candidateTopic: Topic[CandidateMsg] =
    TopicProducer.taggedStreamWithOffset(CandidateRegistryEvt.Tag.allTags.toList) { (tag, offset) =>
      registry.eventStream(tag, offset)
        .filter {
          _.event match {
            case x@(_: CandidateRegisteredEvt) => true
            case _ => false
          }
        }.mapAsync(1) { event =>
        event.event match {
          case evt: CandidateRegisteredEvt =>
            val candidate = evt.candidate
            val candidateDto = CandidateDto(candidate.candidateNumber, candidate.residentId, candidate.name, candidate.party)
            val msg = CandidateRegisteredMsg(candidateDto)
            Future.successful((msg, event.offset))
        }
      }
    }
}

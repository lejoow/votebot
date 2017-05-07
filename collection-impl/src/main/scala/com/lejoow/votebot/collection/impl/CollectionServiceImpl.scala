package com.lejoow.votebot.collection.impl

import akka.NotUsed
import com.lejoow.votebot.collection.api.{CollectionService, VoteView}
import com.lejoow.votebot.collection.impl.commons.Vote
import com.lejoow.votebot.collection.impl.entity.ces.GetCandidateNumbersCmd
import com.lejoow.votebot.collection.impl.entity.{VoteCollectorEntity, VoteCollectorRepository}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.lightbend.lagom.scaladsl.server.ServerServiceCall

import scala.concurrent.ExecutionContext

/**
  * Created by Joo on 6/5/2017.
  */
class CollectionServiceImpl(registry: PersistentEntityRegistry,
                            voteCollectorRepository: VoteCollectorRepository)
                           (implicit ec: ExecutionContext) extends CollectionService {

  override def getRegisteredCandidates(): ServiceCall[NotUsed, Seq[Int]] = ServerServiceCall { _ =>
    voteCollectorEntityRef.ask(GetCandidateNumbersCmd)
  }

  override def getVotesByCity(city: String): ServiceCall[NotUsed, Seq[VoteView]] = ServerServiceCall { _ =>

    for {
      votes <- voteCollectorRepository.findVotesByCity(city)
    } yield votes.map(convert2VoteView)
  }

  def convert2VoteView(vote: Vote): VoteView = {
    VoteView(
      candidateNumber = vote.candidateNumber,
      voterId = vote.voterInfo.voterId,
      city = vote.voterInfo.city,
      gender = vote.voterInfo.gender.toString,
      age = vote.voterInfo.age
    )
  }

  private def voteCollectorEntityRef = registry.refFor[VoteCollectorEntity]("VOTE_COLLECTOR_ENTITY")


}

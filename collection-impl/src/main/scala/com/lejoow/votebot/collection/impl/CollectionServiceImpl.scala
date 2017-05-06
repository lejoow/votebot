package com.lejoow.votebot.collection.impl

import akka.NotUsed
import com.lejoow.votebot.collection.api.CollectionService
import com.lejoow.votebot.collection.impl.entity.VoteCollectorEntity
import com.lejoow.votebot.collection.impl.entity.ces.GetCandidateNumbersCmd
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.lightbend.lagom.scaladsl.server.ServerServiceCall

import scala.concurrent.ExecutionContext

/**
  * Created by Joo on 6/5/2017.
  */
class CollectionServiceImpl(registry: PersistentEntityRegistry)
                           (implicit ec: ExecutionContext) extends CollectionService {

  override def getRegisteredCandidates(): ServiceCall[NotUsed, Seq[Int]] = ServerServiceCall { _ =>
    voteCollectorEntityRef.ask(GetCandidateNumbersCmd)
  }

  private def voteCollectorEntityRef = registry.refFor[VoteCollectorEntity]("VOTE_COLLECTOR_ENTITY")

}

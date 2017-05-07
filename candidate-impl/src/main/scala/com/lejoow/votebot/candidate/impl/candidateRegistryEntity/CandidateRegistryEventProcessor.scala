package com.lejoow.votebot.candidate.impl.candidateRegistryEntity

import akka.Done
import com.datastax.driver.core.BoundStatement
import com.lejoow.votebot.candidate.impl.candidateEntity.CandidateEntity
import com.lejoow.votebot.candidate.impl.candidateEntity.ces.CreateCandidateCmd
import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces.{CandidateRegisteredEvt, CandidateRegistryEvt}
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, EventStreamElement, PersistentEntityRegistry, ReadSideProcessor}

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Joo on 7/5/2017.
  */
class CandidateRegistryEventProcessor(persistentEntityRegistry: PersistentEntityRegistry,
                                      session: CassandraSession,
                                      readSide: CassandraReadSide)
                                     (implicit ec: ExecutionContext)
  extends ReadSideProcessor[CandidateRegistryEvt] {

  val READ_SIDE_ID = "candidateRegistryEventProcessor"

  override def buildHandler(): ReadSideProcessor.ReadSideHandler[CandidateRegistryEvt] = {
    readSide.builder[CandidateRegistryEvt](READ_SIDE_ID)
      .setEventHandler[CandidateRegisteredEvt](createCandidateEntity().andThen(done))
      .build()
  }

  override def aggregateTags: Set[AggregateEventTag[CandidateRegistryEvt]] = Set(CandidateRegistryEvt.Tag)

  def createCandidateEntity(): EventStreamElement[CandidateRegisteredEvt] => Future[Done] = {
    evt => {
      val candidate = evt.event.candidate
      candidateEntityRef(candidate.candidateNumber).ask(CreateCandidateCmd(candidate))
    }
  }


  private def done: Future[Done] => Future[immutable.Seq[BoundStatement]] = {
    doneValue => doneValue.map(_ => List())
  }

  private def candidateEntityRef(candidateNumber: Int) = persistentEntityRegistry.refFor[CandidateEntity](candidateNumber.toString)


}

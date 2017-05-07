package com.lejoow.votebot.collection.impl.entity

import akka.Done
import com.lejoow.votebot.collection.impl.entity.ces.{VoteAcceptedEvt, VoteCollectorEvt}
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, PersistentEntityRegistry, ReadSideProcessor}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Joo on 6/5/2017.
  */
class VoteCollectorDbEventProcessor(persistentEntityRegistry: PersistentEntityRegistry,
                                    session: CassandraSession,
                                    readSide: CassandraReadSide,
                                    dbHandler: VoteCollectorDbHandler)
                                   (implicit ec: ExecutionContext)
  extends ReadSideProcessor[VoteCollectorEvt] {

  val READ_SIDE_ID = "voteCollectorDbEventProcessor"

  override def aggregateTags: Set[AggregateEventTag[VoteCollectorEvt]] = Set(VoteCollectorEvt.Tag)

  override def buildHandler(): ReadSideProcessor.ReadSideHandler[VoteCollectorEvt] = {
    readSide.builder[VoteCollectorEvt](READ_SIDE_ID)
      .setGlobalPrepare(createTables)
      .setPrepare(_ => prepareStatements)
      .setEventHandler[VoteAcceptedEvt](ele => dbHandler.insertVote(ele.event.vote))
      .build()
  }

  private def createTables(): Future[Done.type] = {
    for {
      _ <- dbHandler.createTables
    } yield Done
  }

  private def prepareStatements(): Future[Done.type] = {

    for {
      _ <- dbHandler.prepare
    } yield Done
  }


}

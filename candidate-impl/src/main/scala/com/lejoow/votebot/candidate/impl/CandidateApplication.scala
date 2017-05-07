package com.lejoow.votebot.candidate.impl

import com.lejoow.votebot.candidate.api.CandidateService
import com.lejoow.votebot.candidate.impl.candidateEntity.CandidateEntity
import com.lejoow.votebot.candidate.impl.candidateRegistryEntity.{CandidateRegistryEntity, CandidateRegistryEventProcessor}
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.client.LagomServiceClientComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.Environment
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.ExecutionContext

trait CandidateComponents extends LagomServerComponents
  with AhcWSComponents
  with LagomServiceClientComponents
  with CassandraPersistenceComponents {

  implicit def executionContext: ExecutionContext

  def environment: Environment

  override lazy val lagomServer = serverFor[CandidateService](wire[CandidateServiceImpl])
  lazy val jsonSerializerRegistry = CandidateSerializerRegistry
  persistentEntityRegistry.register(wire[CandidateEntity])
  persistentEntityRegistry.register(wire[CandidateRegistryEntity])

  readSide.register(wire[CandidateRegistryEventProcessor])
  
  /*  lazy val itemRepository = wire[ItemRepository]
    persistentEntityRegistry.register(wire[ItemEntity])
    readSide.register(wire[ItemEventProcessor])*/
}

abstract class CandidateApplication(context: LagomApplicationContext) extends LagomApplication(context)
  with CandidateComponents
  with LagomKafkaComponents {

  //lazy val biddingService = serviceClient.implement[BiddingService]

  //wire[BiddingServiceSubscriber]
}

class CandidateApplicationLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new CandidateApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new CandidateApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[CandidateService]
  )
}

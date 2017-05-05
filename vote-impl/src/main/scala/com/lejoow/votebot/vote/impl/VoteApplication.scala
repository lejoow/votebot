package com.lejoow.votebot.vote.impl

import com.lejoow.votebot.vote.api.VoteService
import com.lejoow.votebot.vote.impl.entity.VoterEntity
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.internal.client.CircuitBreakerMetricsProviderImpl
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.client.LagomServiceClientComponents
import com.softwaremill.macwire._
import com.typesafe.conductr.bundlelib.lagom.scaladsl.ConductRApplicationComponents
import play.api.Environment
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.ExecutionContext

trait VoteComponents extends LagomServerComponents
  with AhcWSComponents
  with LagomServiceClientComponents
  with CassandraPersistenceComponents {

  implicit def executionContext: ExecutionContext

  def environment: Environment

  override lazy val lagomServer = serverFor[VoteService](wire[VoteServiceImpl])
  lazy val jsonSerializerRegistry = VoteSerializerRegistry
  persistentEntityRegistry.register(wire[VoterEntity])
  /*  lazy val itemRepository = wire[ItemRepository]
    persistentEntityRegistry.register(wire[ItemEntity])
    readSide.register(wire[ItemEventProcessor])*/
}

abstract class VoteApplication(context: LagomApplicationContext) extends LagomApplication(context)
  with VoteComponents
  with LagomKafkaComponents {

  //lazy val biddingService = serviceClient.implement[BiddingService]

  //wire[BiddingServiceSubscriber]
}

class VoteApplicationLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new VoteApplication(context)  {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new VoteApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[VoteService]
  )
}

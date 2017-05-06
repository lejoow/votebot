package com.lejoow.votebot.collection.impl

import com.lejoow.votebot.collection.api.CollectionService
import com.lejoow.votebot.collection.impl.entity.VoterCounterEntity
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.client.LagomServiceClientComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServerComponents}
import com.softwaremill.macwire.wire
import play.api.Environment
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.ExecutionContext

/**
  * Created by Joo on 6/5/2017.
  */
trait CollectionComponents extends LagomServerComponents
  with AhcWSComponents
  with LagomServiceClientComponents
  with CassandraPersistenceComponents {


  implicit def executionContext: ExecutionContext

  def environment: Environment

  override lazy val lagomServer = serverFor[CollectionService](wire[CollectionServiceImpl])
  lazy val jsonSerializerRegistry = CollectionSerializerRegistry
  persistentEntityRegistry.register(wire[VoterCounterEntity])

}


abstract class CollectionApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CollectionComponents
    with LagomKafkaComponents {

  //lazy val biddingService = serviceClient.implement[BiddingService]

  //wire[BiddingServiceSubscriber]
}

class CollectionApplicationLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new CollectionApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new CollectionApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[CollectionService]
  )
}

package com.lejoow.votebot.collection.api

import java.util.UUID

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * Created by Joo on 6/5/2017.
  */
trait CollectionService extends Service {

  def getVoterCounts(): ServiceCall[NotUsed, Long]

  //def vote(voterCode: UUID, candidateName: String): ServiceCall[NotUsed, Done]

  final override def descriptor: Descriptor = {
    import Service._

    named("collection").withCalls(
      restCall(Method.GET, "/api/collection/count", getVoterCounts)
    ).withAutoAcl(true)
  }
}

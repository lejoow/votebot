package com.lejoow.votebot.collection.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * Created by Joo on 6/5/2017.
  */
trait CollectionService extends Service {

  def getRegisteredCandidates(): ServiceCall[NotUsed, Seq[Int]]

  def getVotesByCity(city: String): ServiceCall[NotUsed, Seq[VoteView]]

  //def vote(voterCode: UUID, candidateName: String): ServiceCall[NotUsed, Done]

  final override def descriptor: Descriptor = {
    import Service._

    named("collection").withCalls(
      restCall(Method.GET, "/api/collection/candidates", getRegisteredCandidates),
      restCall(Method.GET, "/api/collection/votes?city", getVotesByCity _)
    ).withAutoAcl(true)
  }
}

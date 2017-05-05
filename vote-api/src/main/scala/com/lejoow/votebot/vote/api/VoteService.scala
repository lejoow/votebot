package com.lejoow.votebot.vote.api

import java.util.UUID

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * Created by Joo on 5/5/2017.
  */
trait VoteService extends Service {

  def registerVoter(): ServiceCall[VoterDto, UUID]

  //def vote(voterCode: UUID, candidateName: String): ServiceCall[NotUsed, Done]

  final override def descriptor: Descriptor = {
    import Service._

    named("vote").withCalls(
      restCall(Method.POST, "/api/voter", registerVoter _)
     // restCall(Method.POST, "/api/vote/:voterCode/:candidateName", vote _)
    ).withAutoAcl(true)
  }

}

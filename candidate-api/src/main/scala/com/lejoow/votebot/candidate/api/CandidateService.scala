package com.lejoow.votebot.candidate.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * Created by Joo on 6/5/2017.
  */
trait CandidateService extends Service {

  def registerCandidate(residentId: String, name: String, party: String): ServiceCall[NotUsed, CandidateDto]

  final override def descriptor: Descriptor = {
    import Service._

    named("candidate").withCalls(
      restCall(Method.POST, "/api/candidate?residentId&name&party", registerCandidate _)
    ).withAutoAcl(true)
  }
  

}

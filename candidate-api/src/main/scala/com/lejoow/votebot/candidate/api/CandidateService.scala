package com.lejoow.votebot.candidate.api

import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * Created by Joo on 6/5/2017.
  */
trait CandidateService extends Service {

  def registerCandidate(): ServiceCall[CandidateDto, Int]

  final override def descriptor: Descriptor = {
    import Service._

    named("candidate").withCalls(
      restCall(Method.POST, "/api/candidate", registerCandidate _)
    ).withAutoAcl(true)
  }

}

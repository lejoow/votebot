package com.lejoow.votebot.candidate.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * Created by Joo on 6/5/2017.
  */
trait CandidateService extends Service {

  def registerCandidate(residentId: String, name: String, party: String): ServiceCall[NotUsed, CandidateDto]


  def candidateTopic: Topic[CandidateMsg]

  final override def descriptor: Descriptor = {
    import Service._

    named("candidate").withCalls(
      restCall(Method.POST, "/api/candidate?residentId&name&party", registerCandidate _)
    ).withTopics(
      topic(CandidateService.CANDIDATE_TOPIC, candidateTopic)
    ).withAutoAcl(true)
  }


}


object CandidateService {

  val CANDIDATE_TOPIC = "candidate-Topic"
}


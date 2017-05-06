package com.lejoow.votebot.candidate.impl

import com.lejoow.votebot.candidate.api.CandidateService
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry

import scala.concurrent.ExecutionContext

/**
  * Created by Joo on 6/5/2017.
  */
class CandidateServiceImpl (registry: PersistentEntityRegistry)
                           (implicit ec: ExecutionContext) extends CandidateService{

}

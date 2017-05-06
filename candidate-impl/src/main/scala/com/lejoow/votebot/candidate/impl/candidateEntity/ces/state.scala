package com.lejoow.votebot.candidate.impl.candidateEntity.ces

import java.util.UUID

import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class Candidate(candidateNumber:Int, residentId:String, name: String, party: String)

object Candidate {
  implicit val format: Format[Candidate] = Json.format
}

case class CandidateState(candidate: Option[Candidate], isActive: Boolean)

object CandidateState {

  val emptyState = CandidateState(candidate = None, isActive = false)

  implicit val format: Format[CandidateState] = Json.format
}
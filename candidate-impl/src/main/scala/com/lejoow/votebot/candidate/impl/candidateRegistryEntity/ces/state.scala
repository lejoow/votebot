package com.lejoow.votebot.candidate.impl.candidateRegistryEntity.ces

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.Candidate
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class CandidateRegistryState(candidates: Seq[Candidate]) {

  def addCandidateToMap(candidate: Candidate): CandidateRegistryState = {
    val updatedList = candidates.filterNot(_.residentId == candidate.residentId) :+ candidate
    this.copy(candidates = updatedList)
  }

}

object CandidateRegistryState {
  val emptyState = CandidateRegistryState(candidates = Seq())
  implicit val format: Format[CandidateRegistryState] = Json.format
}
package com.lejoow.votebot.candidate.impl.candidateNumberEntity.ces

import com.lejoow.votebot.candidate.impl.candidateEntity.ces.Candidate
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class CandidateNumberState(candidateMap:Map[Int, Candidate])

object CandidateNumberState {
  implicit val format: Format[CandidateNumberState] = Json.format
}
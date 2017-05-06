package com.lejoow.votebot.collection.impl.entity.ces

import com.lejoow.votebot.collection.impl.commons.VoteRejectionReason.VoteRejectionReason
import com.lejoow.votebot.collection.impl.commons.{RejectedVote, Vote}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class VoteCollector(validCandidates: Set[Int],
                         acceptedVotes: Set[Vote],
                         rejectedVotes: Seq[RejectedVote]) {

  def addRejectedVote(vote: Vote, rejectedReason: VoteRejectionReason): VoteCollector = {
    val rejectedVote = RejectedVote(vote, rejectedReason)
    val updatedList = rejectedVotes :+ rejectedVote
    this.copy(rejectedVotes = updatedList)
  }

  def addAcceptedVote(vote:Vote):VoteCollector = {
    val updatedSet = acceptedVotes + vote
    this.copy(acceptedVotes = updatedSet)
  }

  def validCandidates(candidateNumber:Int): VoteCollector = {
    val updatedSet = validCandidates + candidateNumber
    this.copy(validCandidates = updatedSet)
  }

}

object VoteCollector {

  /* implicit val votesMapFormat: Format[Map[Int, Vote]] = {
     implicitly[Format[Map[String, Vote]]]
       .inmap(_.map {
         case (key, value) => key.toInt -> value
       }, _.map {
         case (key, value) => key.toString -> value
       })
   }*/

  val emptyState = VoteCollector(validCandidates = Set(), acceptedVotes = Set(), rejectedVotes = Seq())

  implicit val format: Format[VoteCollector] = Json.format
}


package com.lejoow.votebot.collection.impl.commons

import java.util.UUID

import com.lejoow.votebot.collection.impl.commons.VoteRejectionReason.VoteRejectionReason
import com.lejoow.votebot.commons.JsonUtils.enumFormat
import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
object GenderEnum extends Enumeration {
  val Male, Female = Value
  type Gender = Value
  implicit val format: Format[Gender] = enumFormat(this)
  implicit val pathParamSerializer: PathParamSerializer[Gender] =
    PathParamSerializer.required("gender")(withName)(_.toString)
}

object VoteRejectionReason extends Enumeration {
  val DuplicateVote, NoCandidateRegistered, InvalidCandidateNumber = Value
  type VoteRejectionReason = Value
  implicit val format: Format[VoteRejectionReason] = enumFormat(this)
  implicit val pathParamSerializer: PathParamSerializer[VoteRejectionReason] =
    PathParamSerializer.required("rejectionReason")(withName)(_.toString)
}


case class Vote(candidateNumber: Int, voterInfo: VoterInfo)

object Vote {
  implicit val format: Format[Vote] = Json.format
}

case class VoterInfo(voterId: UUID, city: String, gender: GenderEnum.Gender, age: Int)

object VoterInfo {
  implicit val format: Format[VoterInfo] = Json.format
}

case class RejectedVote(vote: Vote, rejectionReason: VoteRejectionReason)

object RejectedVote {
  implicit val format: Format[RejectedVote] = Json.format
}

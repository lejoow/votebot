package com.lejoow.votebot.vote.impl.commons

import com.lightbend.lagom.scaladsl.api.transport.{TransportErrorCode, TransportException}
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
case class DuplicateRequestException(message: String) extends TransportException(TransportErrorCode.PolicyViolation, message)

object DuplicateRequestException {

  implicit val format: Format[DuplicateRequestException] = Json.format
}

case class VoterRegistrationException(message:String) extends TransportException(TransportErrorCode.PolicyViolation, message)

object VoterRegistrationException {

  implicit val format: Format[VoterRegistrationException] = Json.format
}

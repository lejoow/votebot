package com.lejoow.votebot.collection.impl.entity.ces

import akka.Done
import com.lejoow.votebot.collection.impl.commons.Vote
import com.lejoow.votebot.commons.JsonUtils.singletonFormat
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait VoteCollectorCmd

case object GetLiveVoteCountCmd extends VoteCollectorCmd with ReplyType[Long] {
  implicit val format: Format[GetLiveVoteCountCmd.type] = singletonFormat(GetLiveVoteCountCmd)
}


case object GetCandidateNumbersCmd extends VoteCollectorCmd with ReplyType[Seq[Int]] {
  implicit val format: Format[GetCandidateNumbersCmd.type] = singletonFormat(GetCandidateNumbersCmd)
}

case class CollectVoteCmd(vote: Vote) extends VoteCollectorCmd with ReplyType[Done]

object CollectVoteCmd {
  implicit val format: Format[CollectVoteCmd] = Json.format
}

case class RegisterCandidateCmd(candidateNumber:Int) extends VoteCollectorCmd with ReplyType[Done]

object RegisterCandidateCmd {
  implicit val format: Format[RegisterCandidateCmd] = Json.format
}




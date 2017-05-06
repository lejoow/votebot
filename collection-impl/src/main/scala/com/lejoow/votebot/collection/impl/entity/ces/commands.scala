package com.lejoow.votebot.collection.impl.entity.ces

import com.lejoow.votebot.commons.JsonUtils.singletonFormat
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.Format

/**
  * Created by Joo on 6/5/2017.
  */
sealed trait VoterCounterCmd

case object GetVoterCountCmd extends VoterCounterCmd with ReplyType[Long]{
  implicit val format: Format[GetVoterCountCmd.type] = singletonFormat(GetVoterCountCmd)
}

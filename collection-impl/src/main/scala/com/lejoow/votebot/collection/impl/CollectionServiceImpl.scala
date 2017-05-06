package com.lejoow.votebot.collection.impl

import akka.NotUsed
import com.lejoow.votebot.collection.api.CollectionService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry

import scala.concurrent.ExecutionContext

/**
  * Created by Joo on 6/5/2017.
  */
class CollectionServiceImpl(registry: PersistentEntityRegistry)
                           (implicit ec: ExecutionContext) extends CollectionService {

  override def getVoterCounts(): ServiceCall[NotUsed, Long] = ???

}

package com.lejoow.votebot.collection.impl.commons

import akka.Done
import com.datastax.driver.core.{Session, TypeCodec}
import com.datastax.driver.extras.codecs.MappingCodec
import com.datastax.driver.extras.codecs.jdk8.{InstantCodec, LocalDateCodec, LocalTimeCodec}
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession

import scala.concurrent.Future

/**
  * Created by Joo on 6/5/2017.
  */
trait DbHandler {

  import scala.concurrent.ExecutionContext.Implicits.global

  def session: CassandraSession

  def createTables: Future[Done]

  def prepare: Future[Done]

  def registerStandardCodecs(session: CassandraSession): Future[Done] = {
    for {
      underlying <- session.underlying()
    } yield {
      registerCodec(underlying, new BigDecimalCodec)
      registerCodec(underlying, InstantCodec.instance)
      registerCodec(underlying, LocalDateCodec.instance)
      registerCodec(underlying, LocalTimeCodec.instance)
      Done
    }
  }

  class BigDecimalCodec extends MappingCodec(TypeCodec.decimal(), classOf[scala.math.BigDecimal]) {
    override def serialize(value: scala.math.BigDecimal): java.math.BigDecimal = value.bigDecimal

    override def deserialize(value: java.math.BigDecimal): scala.math.BigDecimal = BigDecimal(value)
  }

  private def registerCodec(session: Session, codec: TypeCodec[_]): Unit = {
    session.getCluster.getConfiguration.getCodecRegistry.register(codec)
  }

}
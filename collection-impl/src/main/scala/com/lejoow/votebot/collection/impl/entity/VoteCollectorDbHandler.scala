package com.lejoow.votebot.collection.impl.entity

import akka.Done
import com.datastax.driver.core.{BoundStatement, PreparedStatement}
import com.lejoow.votebot.collection.impl.commons.{DbHandler, Vote}
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Joo on 6/5/2017.
  */
class VoteCollectorDbHandler(val session: CassandraSession)
                            (implicit ec: ExecutionContext) extends DbHandler {

  private lazy val insertVoteStatement: Future[PreparedStatement] = session.prepare(VoteCollectorDbHandler.insert_vote_sql)

  override def createTables: Future[Done] = {
    for {
      _ <- session.executeCreateTable(VoteCollectorDbHandler.create_table_votes)
      _ <- session.executeCreateTable(VoteCollectorDbHandler.create_view_votes_by_city)
    } yield Done
  }

  override def prepare: Future[Done] = {
    registerStandardCodecs(session)
  }

  def insertVote(vote: Vote): Future[List[BoundStatement]] = Future.sequence {
    List(
      insertVoteStatement.map(stm => {
        stm.bind(
          new java.lang.Integer(vote.candidateNumber),
          vote.voterInfo.voterId,
          new java.lang.Integer(vote.voterInfo.age),
          vote.voterInfo.city,
          vote.voterInfo.gender.toString
        )
      })
    )
  }
}


object VoteCollectorDbHandler {

  val create_table_votes =
    """
      CREATE TABLE IF NOT EXISTS votes(
            candidateNumber INT,
            voterId         UUID,
            age             INT,
            city            STRING,
            gender          STRING,
            PRIMARY KEY ((candidateNumber))
      )
    """.stripMargin


  val create_view_votes_by_city =
    """
        CREATE MATERIALIZED VIEW IF NOT EXISTS votes_by_city AS
        SELECT * FROM votes
        WHERE candidateNumber IS NOT NULL AND city IS NOT NULL
        PRIMARY KEY((city),candidateNumber)
      """.stripMargin

  val insert_vote_sql =
    """
          INSERT INTO votes(
              candidateNumber,
              voterId,
              age,
              city,
              gender
          )
          VALUES(?, ?, ?, ?, ?)
        """.stripMargin

}


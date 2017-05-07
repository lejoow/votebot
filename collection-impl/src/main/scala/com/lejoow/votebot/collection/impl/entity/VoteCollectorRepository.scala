package com.lejoow.votebot.collection.impl.entity

import com.datastax.driver.core.Row
import com.lejoow.votebot.collection.impl.commons.{GenderEnum, Vote, VoterInfo}
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Joo on 7/5/2017.
  */
class VoteCollectorRepository(session: CassandraSession)(implicit ec: ExecutionContext) {

  def findAll(): Future[Seq[Vote]] = session.selectAll(
    """
      SELECT * FROM votes
    """.stripMargin).map(extractVotesFromRows)

  def findVotesByCity(city: String): Future[Seq[Vote]] = session.selectAll(
    """
      SELECT * FROM votes_by_city WHERE city = ?
    """.stripMargin, city).map(extractVotesFromRows)

  def extractVotesFromRows(rows: Seq[Row]): Seq[Vote] = rows.map(extractVoteFromRow)

  def extractVoteFromRow(row: Row): Vote = {
    val candidateNumber = row.getInt("candidateNumber")
    val voterId = row.getUUID("voterId")
    val age = row.getInt("age")
    val city = row.getString("city")
    val gender = row.getString("gender")

    Vote(
      candidateNumber = candidateNumber,
      voterInfo = VoterInfo(
        voterId = voterId,
        city = city,
        gender = GenderEnum.withName(gender),
        age = age
      )
    )
  }

}

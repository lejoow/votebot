package com.lejoow.votebot.testkit

import akka.Done
import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver.{Outcome, Reply, SideEffect}
import org.scalatest.{BeforeAndAfterAll, Inside, Matchers, WordSpec}

trait EntitySpec[C, E, S] extends WordSpec with Matchers with BeforeAndAfterAll with Inside {

  def serializationRegistry: JsonSerializerRegistry

  def entityToTest: Any => PersistentEntity {type Command = C; type Event = E; type State = S}

  def entityId: String

  def defaultEntitySetup: PersistentEntityTestDriver[C, E, S] => Any

  val system = ActorSystem(getClass.getSimpleName, JsonSerializerRegistry.actorSystemSetupFor(serializationRegistry))

  override protected def afterAll() = {
    TestKit.shutdownActorSystem(system)
  }

  def withGivenEntityAndNoSetup(givenEntityId: String)(block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {
    withCustomizedEntitySetupTearDownOnGivenEntityId(givenEntityId)(driver => driver)(checkIssues)(block)
  }

  def withEntitySetup(block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {
    withCustomizedEntitySetupTearDown(defaultEntitySetup)(checkIssues)(block)
  }

  def withNoEntitySetup(block: PersistentEntityTestDriver[C, E, S] => Unit): Unit = {
    withCustomizedEntitySetup(driver => driver)(block)
  }

  def withCustomizedEntitySetup(setUp: PersistentEntityTestDriver[C, E, S] => Any)
                               (block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {
    withCustomizedEntitySetupTearDown(setUp)(checkIssues)(block)
  }

  def withEntitySetup(entityId: String)(block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {
    withCustomizedEntitySetupTearDown(defaultEntitySetup)(checkIssues)(block)
  }



  def withCustomizedEntitySetupTearDown(setUp: PersistentEntityTestDriver[C, E, S] => Any)
                                       (tearDown: PersistentEntityTestDriver[C, E, S] => Any)
                                       (block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {
    withCustomizedEntitySetupTearDownOnGivenEntityId(entityId)(setUp)(tearDown)(block)
  }

  private def withCustomizedEntitySetupTearDownOnGivenEntityId(givenEntityId: String)
                                                              (setUp: PersistentEntityTestDriver[C, E, S] => Any)
                                                              (tearDown: PersistentEntityTestDriver[C, E, S] => Any)
                                                              (block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {
    val driver = new PersistentEntityTestDriver(system, entityToTest.apply(), givenEntityId)
    withCustomizedDriverAndEntitySetupTearDown(driver)(setUp)(tearDown)(block)
  }

  private def withCustomizedDriverAndEntitySetupTearDown(driver: PersistentEntityTestDriver[C, E, S])
                                                        (setUp: PersistentEntityTestDriver[C, E, S] => Any)
                                                        (tearDown: PersistentEntityTestDriver[C, E, S] => Any)
                                                        (block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {
    setUp(driver)
    block(driver)
    tearDown(driver)
  }

  def withNewDriver(driver: PersistentEntityTestDriver[C, E, S])(block: PersistentEntityTestDriver[C, E, S] => Any): Unit = {

    block(driver)
    checkIssues(driver)
  }

  def checkIssues(driver: PersistentEntityTestDriver[C, E, S]) = {
    if (driver.getAllIssues.nonEmpty) {
      driver.getAllIssues.foreach(println)
      fail("There were issues " + driver.getAllIssues.head)
    }
  }

  def verifyInvalidCommand(s: SideEffect, errorMessage: String = "") = {
    s match {
      case Reply(x) => x match {
        case ex: PersistentEntity.InvalidCommandException => ex.message should (include(errorMessage))
        case ex: Exception => fail(s"exception should be invalid command but was: ${ex.getCause}")
        case unexpected => fail(s"Expecting exception, but side effect was: ${unexpected.toString}")
      }
      case _ => fail("Side effect should be reply")
    }
  }

  def verifyOnlyReplyDone(outcome: Outcome[E, S]) = {
    outcome.events shouldBe empty
    outcome.replies should contain only(Done)
    outcome.sideEffects should contain only(Reply(Done))
  }


}

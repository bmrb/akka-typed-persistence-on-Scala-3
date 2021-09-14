package com.test

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.Behavior
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import akka.persistence.typed.{PersistenceId, RecoveryCompleted}

import com.test.serializer.CborSerializable

object TestAggregate {

  final val DefaultActorName = "TestAggregate"

  trait Protocol extends CborSerializable

  trait Event extends CborSerializable

  final case class Ping(s: String) extends Protocol

  final case class Pinged(s: String) extends Event

  case class State(context: ActorContext[Protocol], count: Int)

  def apply(): Behavior[Protocol] =
    Behaviors.setup[Protocol] { context =>
      EventSourcedBehavior[Protocol, Event, State](
        persistenceId = PersistenceId.ofUniqueId(DefaultActorName),
        emptyState = State(context, 0),
        commandHandler = commandHandler,
        eventHandler = eventHandler
      ).receiveSignal { case (state, RecoveryCompleted) =>
        onRecoveryCompleted(context, state)
      }
    }

  private def onRecoveryCompleted(context: ActorContext[_], state: State): Unit =
    state.context.log.info("{} Pinged event recovered", state.count)

  val commandHandler: (State, Protocol) => Effect[Event, State] = { (state, message) =>
    message match {
      case cmd: Ping =>
        state.context.log.info("Ping command received with message {}", cmd.s)
        Effect.persist(Pinged(cmd.s)).thenNoReply()

      case _ =>
        Effect.none
    }
  }

  val eventHandler: (State, Event) => State = { (state, event) =>
    event match {
      case evt: Pinged =>
        state.context.log.info("Pinged event recovered with message {}", evt.s)
        state.copy(count = state.count + 1)

      case _ =>
        state
    }
  }

}

package com.test

import akka.actor.typed.ActorSystem

object Main {

  def main(args: Array[String]): Unit = {
    val system: ActorSystem[TestAggregate.Protocol] = ActorSystem(TestAggregate(), "SerializerTest")
    system ! TestAggregate.Ping("DoPing")
  }
}

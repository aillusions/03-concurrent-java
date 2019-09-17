package com.zalizniak;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("helloakka");

        final ActorRef actor1 = system.actorOf(TestActor1.props(), "actor1");
        final ActorRef actor2 = system.actorOf(TestActor2.props(), "actor2");

        actor1.tell("on_msg", ActorRef.noSender());
        actor2.tell("on_msg", ActorRef.noSender());
    }
}

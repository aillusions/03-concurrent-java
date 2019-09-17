package com.zalizniak;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("helloakka");

        final ActorRef actor1 = system.actorOf(TestActor1.props(), "actor1");
        final ActorRef actor2 = system.actorOf(TestActor2.props(), "actor2");

        System.out.println("actor1.path " + actor1.path());
        System.out.println("actor2.path " + actor2.path());

        actor1.tell(1, ActorRef.noSender());
        //actor2.tell("on_msg", ActorRef.noSender());
    }
}

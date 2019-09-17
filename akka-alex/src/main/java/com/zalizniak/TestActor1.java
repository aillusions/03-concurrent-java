package com.zalizniak;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class TestActor1 extends AbstractActor {

    public Receive createReceive() {
        return receiveBuilder().match(Integer.class, p -> {
            System.out.println("TestActor1: " + p);
            getContext().getSystem().actorSelection("akka://helloakka/user/actor2").tell(p + 1, getSelf());
        }).build();
    }

    static public Props props() {
        return Props.create(TestActor1.class);
    }
}

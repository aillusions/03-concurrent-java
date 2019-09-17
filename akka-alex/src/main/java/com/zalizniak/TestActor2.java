package com.zalizniak;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class TestActor2 extends AbstractActor {

    public Receive createReceive() {
        return receiveBuilder().match(Integer.class, p -> {
            System.out.println("TestActor2: " + p);
            getContext().getSystem().actorSelection("akka://helloakka/user/actor1").tell(p + 1, getSelf());
        }).build();
    }

    static public Props props() {
        return Props.create(TestActor2.class);
    }
}

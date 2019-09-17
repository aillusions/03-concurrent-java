package com.zalizniak;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class TestActor2 extends AbstractActor {
    public Receive createReceive() {
        return receiveBuilder().matchEquals("on_msg", p -> {
            System.out.println("TestActor2: on_msg");
        }).build();
    }

    static public Props props() {
        return Props.create(TestActor2.class);
    }
}

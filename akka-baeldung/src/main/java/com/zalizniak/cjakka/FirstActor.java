package com.zalizniak.cjakka;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class FirstActor extends AbstractActor {

    public static Props props() {
        return Props.create(FirstActor.class);
    }

    @Override
    public void preStart() {
        System.out.println("Actor started");
    }

    @Override
    public void postStop() {
        System.out.println("Actor stopped");
    }

    // Messages will not be handled
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }
}
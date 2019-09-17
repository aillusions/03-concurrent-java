package com.zalizniak.cjakka;

import akka.actor.AbstractActor;

public class MyActor extends AbstractActor {

    @Override
    public void postStop() {
        System.out.println("Stopping actor this");
    }

    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit", p -> {
                    System.out.println("The address of this actor is: " + getSelf());
                    getSender().tell("Got Message", getSelf());
                })
                .build();
    }
}
package com.zalizniak.cjakka;


import akka.actor.AbstractActor;
import akka.actor.Props;

public class PrinterActor extends AbstractActor {

    public static Props props(String text) {
        return Props.create(PrinterActor.class, text);
    }

    public static final class PrintFinalResult {
        Integer totalNumberOfWords;

        public PrintFinalResult(Integer totalNumberOfWords) {
            this.totalNumberOfWords = totalNumberOfWords;
        }
    }

    @Override
    public void preStart() {
        System.out.println("Starting PrinterActor " + this);
    }

    @Override
    public void postStop() {
        System.out.println("Stopping PrinterActor " + this);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PrinterActor.PrintFinalResult.class,
                        r -> {
                            System.out.println("Received PrintFinalResult message from " + getSender());
                            System.out.println("The text has a total number of " + r.totalNumberOfWords + " words");
                        })
                .build();
    }
}
package com.zalizniak.cjakka;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.PatternsCS.ask;

public class ReadingActor extends AbstractActor {

    private String text;

    public ReadingActor(String text) {
        this.text = text;
    }

    public static final class ReadLines {
    }

    @Override
    public void preStart() {
        System.out.println("Starting ReadingActor " + this);
    }

    @Override
    public void postStop() {
        System.out.println("Stopping ReadingActor " + this);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ReadLines.class, r -> {

                    System.out.println("Received ReadLines message from " + getSender());

                    String[] lines = text.split("\n");
                    List<CompletableFuture> futures = new ArrayList<>();

                    for (int i = 0; i < lines.length; i++) {
                        String line = lines[i];
                        ActorRef wordCounterActorRef = getContext().actorOf(Props.create(WordCounterActor.class), "word-counter-" + i);

                        CompletableFuture<Object> future =
                                ask(wordCounterActorRef, new WordCounterActor.CountWords(line), 1000).toCompletableFuture();
                        futures.add(future);
                    }

                    Integer totalNumberOfWords = futures.stream()
                            .map(CompletableFuture::join)
                            .mapToInt(n -> (Integer) n)
                            .sum();

                    ActorRef printerActorRef = getContext().actorOf(Props.create(PrinterActor.class), "Printer-Actor");
                    printerActorRef.forward(new PrinterActor.PrintFinalResult(totalNumberOfWords), getContext());
//                    printerActorRef.tell(new PrinterActor.PrintFinalResult(totalNumberOfWords), getSelf());

                })
                .build();
    }
}
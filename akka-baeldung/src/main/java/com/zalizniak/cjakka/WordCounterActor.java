package com.zalizniak.cjakka;

import akka.actor.AbstractActor;

public class WordCounterActor extends AbstractActor {

    public static final class CountWords {
        String line;

        public CountWords(String line) {
            this.line = line;
        }
    }

    @Override
    public void preStart() {
        System.out.println("Starting WordCounterActor " + this);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CountWords.class, r -> {
                    try {
                        System.out.println("Received CountWords message from " + getSender());
                        int numberOfWords = countWordsFromLine(r.line);
                        getSender().tell(numberOfWords, getSelf());
                    } catch (Exception ex) {
                        getSender().tell(new akka.actor.Status.Failure(ex), getSelf());
                        throw ex;
                    }
                })
                .build();
    }

    private int countWordsFromLine(String line) throws Exception {

        if (line == null) {
            throw new IllegalArgumentException("The text to process can't be null!");
        }

        int numberOfWords = 0;
        String[] words = line.split(" ");
        for (String possibleWord : words) {
            if (possibleWord.trim().length() > 0) {
                numberOfWords++;
            }
        }
        return numberOfWords;
    }
}
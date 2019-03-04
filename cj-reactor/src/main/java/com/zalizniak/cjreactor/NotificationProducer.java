package com.zalizniak.cjreactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Service
public class NotificationProducer {

    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private EventBus eventBus;

    @Scheduled(fixedDelay = 800)
    public void run1() {
        NotificationData data = new NotificationData();
        data.setId(counter.incrementAndGet());
        data.setName("From-1");
        eventBus.notify("notificationConsumer", Event.wrap(data));
        //log.info("Notification " + i + ": notification task submitted successfully");
    }

    @Scheduled(fixedDelay = 1000)
    public void run2() {
        NotificationData data = new NotificationData();
        data.setId(counter.incrementAndGet());
        data.setName("From-2");
        eventBus.notify("notificationConsumer", Event.wrap(data));
        //log.info("Notification " + i + ": notification task submitted successfully");
    }
}

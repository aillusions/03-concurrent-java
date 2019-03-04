package com.zalizniak.cjreactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;


@Slf4j
@Service
public class NotificationConsumer implements Consumer<Event<NotificationData>> {

    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {
        NotificationData notificationData = notificationDataEvent.getData();

        try {
            handleNotification(notificationData);
        } catch (InterruptedException e) {
            log.error("accept error: ", e);
        }
    }

    public void handleNotification(NotificationData data) throws InterruptedException {
        log.info("Processing started for " + ": " + data.getId() + " " + data.getName());
        Thread.sleep(5000);
        log.info("Processing ended for " + ": " + data.getId() + " " + data.getName());
    }
}

package com.example.OrderScheduledThread;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Demotask implements Runnable {

    //String cron_expression="0 0/1 * * * ?";
    @Scheduled(fixedRate = 10000L)
    @Override public void run() {
        try {
            Thread.sleep(3000L);
            System.out.println("Hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
package com.example.OrderScheduledThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class CustomeService {

    @Async
    public CompletableFuture<String> findUser(String user) throws InterruptedException {
        System.out.println("Looking up " + user);
        Thread.sleep(10000L);
        return CompletableFuture.completedFuture(user);
    }

}
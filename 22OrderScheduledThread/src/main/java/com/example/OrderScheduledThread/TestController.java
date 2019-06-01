package com.example.OrderScheduledThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
public class TestController {

    @Autowired
    private GitHubLookupService gitHubLookupService;

    @Autowired
    private CustomeService customeService;

    public CompletableFuture<User> page1;
    public CompletableFuture<String> page2;
    public CompletableFuture<Void> page3;

    @Autowired
    private Demotask demotask;

    @Autowired
    public TaskScheduler taskScheduler;
    public ScheduledFuture<?> page4;

    @RequestMapping("/test/{name}")
    public User test(@PathVariable String name) throws InterruptedException,
            ExecutionException, TimeoutException {
        page1 = gitHubLookupService.findUser(name);
        page2 = customeService.findUser(name);
        CompletableFuture.anyOf(page1,page2).join();
        //if you will not get of user data output in
        // 4 secound than produces TimeoutException
        //this timeout is optional
        return page1.get(4, TimeUnit.SECONDS);
    }

    @RequestMapping("/test2/{name}")
    public String test2(@PathVariable String name) throws ExecutionException, InterruptedException {
        page2 = customeService.findUser(name);
        //page4 = taskScheduler.scheduleAtFixedRate(demotask, 1000L);//use cron
        page3 = CompletableFuture.runAsync(new Demotask());//use scheduled

        /*page3 = CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        });*/

        CompletableFuture.anyOf(page2,page3).join();
        return page2.get()+page3.get();
    }

}

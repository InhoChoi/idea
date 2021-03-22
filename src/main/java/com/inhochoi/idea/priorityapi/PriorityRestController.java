package com.inhochoi.idea.priorityapi;

import com.inhochoi.idea.priorityapi.HttpExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequestMapping("/ideas/priority-api")
@RestController
public class PriorityRestController {
    @Autowired
    private HttpExecutors httpExecutors;

    @GetMapping
    public String getDemo() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture<String> app1 = httpExecutors.request("App1", random.nextInt());
        CompletableFuture<String> app2 = httpExecutors.request("App2", random.nextInt());
        CompletableFuture<String> app3 = httpExecutors.request("App3", random.nextInt());
        CompletableFuture<String> app4 = httpExecutors.request("App4", random.nextInt());

        CompletableFuture.allOf(app1, app2, app3, app4)
                .get();

        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(app1.get());
        stringJoiner.add(app2.get());
        stringJoiner.add(app3.get());
        stringJoiner.add(app4.get());
        return stringJoiner.toString();
    }
}

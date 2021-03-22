package com.inhochoi.idea.priorityapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
@Service
public class HttpExecutors {
    private PriorityBlockingQueue<RequestMessage> queue = new PriorityBlockingQueue<>();
    private Executor executor = Executors.newCachedThreadPool();

    public HttpExecutors() {
        Runnable runnable = () -> {
            while (true) {
                try {
                    RequestMessage requestMessage = queue.take();

                    log.info("Get Message : {}", requestMessage);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    requestMessage.getFuture().complete(requestMessage.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        executor.execute(runnable);
    }

    public CompletableFuture<String> request(String message, int priority) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        queue.add(new RequestMessage(message, completableFuture, priority));
        return completableFuture;
    }


    @Getter
    @ToString
    @AllArgsConstructor
    public static class RequestMessage implements Comparable<RequestMessage> {
        private String message;
        private CompletableFuture future;
        private int priority;

        @Override
        public int compareTo(RequestMessage o) {
            return o.getPriority() - priority;
        }
    }
}

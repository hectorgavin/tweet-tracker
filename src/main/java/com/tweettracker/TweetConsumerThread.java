package com.tweettracker;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

class TweetConsumerThread implements Runnable {
    private int id;
    private Consumer<Tweet> consumer;
    private BlockingQueue<String> queue;

    TweetConsumerThread(int id, Consumer<Tweet> consumer, BlockingQueue<String> queue) {
        this.id = id;
        this.consumer = consumer;
        this.queue = queue;
    }

    long getId() {
        return id;
    }

    public void run() {
        try {
            while (true) {
                try {
                    Tweet tweet = new Tweet(queue.take(), this);
                    consumer.accept(tweet);
                }
                catch (InvalidTweetException e) {}
        }
        }
        catch (InterruptedException e) {
            System.out.println("Thread " + id + " was interrupted!");
        }
    }
}

package com.tweettracker.consumer;

import com.tweettracker.exception.InvalidTweetException;
import com.tweettracker.model.Tweet;
import com.tweettracker.processor.TweetProcessor;

import java.util.concurrent.BlockingQueue;

public class TweetConsumerThread implements Runnable {
    private int id;
    private TweetProcessor tweetProcessor;
    private BlockingQueue<String> queue;

    TweetConsumerThread(int id, TweetProcessor tweetProcessor, BlockingQueue<String> queue) {
        this.id = id;
        this.tweetProcessor = tweetProcessor;
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                try {
                    println("Waiting");
                    Tweet tweet = new Tweet(queue.take(), this);
                    println("Processing tweet: "+tweet);
                    tweetProcessor.process(tweet);
                }
                catch (InvalidTweetException e) {}
        }
        }
        catch (InterruptedException e) {
            println("Interrupted");
        }
    }

    private void println(String text) {
        System.out.println("[Thread " + id + "] "+ text + " ("+ queue.size() +" tweets waiting)");
    }
}

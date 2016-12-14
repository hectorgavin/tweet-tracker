package com.tweettracker.processor;

import com.tweettracker.model.Tweet;

import java.util.Random;

public class SleepTweetProcessor implements TweetProcessor {
    private static final Random random = new Random();

    @Override
    public void process(Tweet tweet) throws InterruptedException {
        Thread.sleep(random.nextInt(5000));
    }
}

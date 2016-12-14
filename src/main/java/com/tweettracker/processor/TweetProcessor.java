package com.tweettracker.processor;

import com.tweettracker.model.Tweet;

public interface TweetProcessor {
    void process(Tweet tweet) throws InterruptedException;
}

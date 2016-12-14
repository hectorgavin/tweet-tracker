package com.tweettracker.exception;

import org.json.JSONObject;

public class InvalidTweetException extends Exception {
    private JSONObject tweet;
    public InvalidTweetException(String message, JSONObject tweet) {
        super(message);
        this.tweet = tweet;
    }
}

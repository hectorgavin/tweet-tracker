package com.tweettracker;

import org.json.JSONObject;

class InvalidTweetException extends Exception {
    private JSONObject tweet;
    InvalidTweetException(String message, JSONObject tweet) {
        super(message);
        this.tweet = tweet;
    }
}

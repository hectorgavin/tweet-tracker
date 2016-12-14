package com.tweettracker;

import org.json.JSONObject;

import java.util.Date;

class Tweet {
    private String message;
    private Date postedAt;
    private String location;
    private String originalEvent;
    private TweetConsumerThread consumerThread;

    Tweet(String eventMessage, TweetConsumerThread thread) throws InvalidTweetException {
        JSONObject json = new JSONObject(eventMessage);
        validate(json);
        message = json.getString("text");
        postedAt = new Date();
        postedAt.setTime(json.getLong("timestamp_ms"));
        if (!json.isNull("place")) {
            location = json.getJSONObject("place").getString("country");
        }
        originalEvent = eventMessage;
        consumerThread = thread;
    }

    private void validate(JSONObject json) throws InvalidTweetException {
        if (json.isNull("text")) {
            throw new InvalidTweetException("Tweet must have a message", json);
        }
    }

    public String getLocation() {
        return location;
    }

    public String toString() {
        return "[Thread "+ consumerThread.getId() +"] "+ message;
    }
}

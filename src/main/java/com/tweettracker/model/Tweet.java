package com.tweettracker.model;

import com.tweettracker.consumer.TweetConsumerThread;
import com.tweettracker.exception.InvalidTweetException;
import org.json.JSONObject;

import java.util.Date;

public class Tweet {
    private String message;
    private Date postedAt;
    private String location;
    private String originalEvent;
    private TweetConsumerThread consumerThread;

    public Tweet(String eventMessage, TweetConsumerThread thread) throws InvalidTweetException {
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

    public String toString() {
        return message.replaceAll("\n", " ");
    }
}

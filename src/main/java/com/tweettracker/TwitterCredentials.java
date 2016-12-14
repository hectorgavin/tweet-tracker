package com.tweettracker;

import java.util.Properties;

public class TwitterCredentials {
    private String consumerKey;
    private String consumerSecret;
    private String tokenKey;
    private String tokenSecret;

    public TwitterCredentials(Properties properties) {
        consumerKey = properties.getProperty("consumerKey");
        consumerSecret = properties.getProperty("consumerSecret");
        tokenKey = properties.getProperty("tokenKey");
        tokenSecret = properties.getProperty("tokenSecret");
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }
}

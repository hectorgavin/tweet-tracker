package com.tweettracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class TweetTracker {
    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try(InputStream inputStream = classloader.getResourceAsStream("config.properties")) {
            // Read properties file
            Properties properties = new Properties();
            properties.load(inputStream);

            // Consume Tweets stream
            final TweetConsumer tweetConsumer = new TweetConsumer(new TwitterCredentials(properties), Arrays.asList(args));
            tweetConsumer.consume(TweetTracker::consumeTweet, 4);
        }
        catch(IOException e) {
            System.err.println("There was an error when reading config.properties file");
        }
    }

    private static void validateArgs(String[] args) {
        if (args == null || args.length < 1) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
    }

    private static void consumeTweet(Tweet tweet) {
        System.out.println(tweet);
    }
}

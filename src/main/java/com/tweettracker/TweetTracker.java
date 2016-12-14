package com.tweettracker;

import com.tweettracker.auth.TwitterCredentials;
import com.tweettracker.consumer.TweetConsumer;
import com.tweettracker.processor.SleepTweetProcessor;
import com.tweettracker.processor.TweetProcessor;

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
            final TweetProcessor tweetProcessor = new SleepTweetProcessor();
            tweetConsumer.consume(tweetProcessor, 10);

            // Interrupt after 10000
            Thread.sleep(10000);
            System.out.println("Done");
            tweetConsumer.shutdown();
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
}

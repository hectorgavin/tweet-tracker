package com.tweettracker;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.IntStream;

class TweetConsumer {

    private static final String CLIENT_NAME = "tweet-consumer";

    private Client client;
    private BlockingQueue<String> queue;
    private ExecutorService consumersPool;

    TweetConsumer(TwitterCredentials config, List<String> hashTags) {
        this(config, hashTags, 10000);
    }

    TweetConsumer(TwitterCredentials config, List<String> hashTags, int queueSize) {
        this.queue = new ArrayBlockingQueue<>(queueSize);
        this.client = buildClient(config, hashTags);
    }

    private Client buildClient(TwitterCredentials config, List<String> tags) {
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        hosebirdEndpoint.trackTerms(tags);

        Authentication hosebirdAuth = new OAuth1(config.getConsumerKey(), config.getConsumerSecret(), config.getTokenKey(), config.getTokenSecret());

        ClientBuilder builder = new ClientBuilder()
            .name(CLIENT_NAME)
            .hosts(hosebirdHosts)
            .authentication(hosebirdAuth)
            .endpoint(hosebirdEndpoint)
            .processor(new StringDelimitedProcessor(queue));

        return builder.build();
    }

    void consume(Consumer<Tweet> consumer, int threads) {
        shutdown();
        consumersPool = Executors.newFixedThreadPool(threads);

        IntStream.range(0, threads)
                 .mapToObj(i -> new TweetConsumerThread(i, consumer, queue))
                 .forEach(consumersPool::submit);

        client.connect();
    }

    void shutdown() {
        if (consumersPool != null && !consumersPool.isShutdown()) {
            consumersPool.shutdownNow();
        }
    }
}

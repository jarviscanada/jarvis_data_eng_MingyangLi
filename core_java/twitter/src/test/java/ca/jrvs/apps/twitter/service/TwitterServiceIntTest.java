package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {
    private String testId;
    private String postId;
    private final String post = "sometext";
    private final String test = "test";
    private final Double lon = -100.0;
    private final Double lat = 10.0;
    private TwitterDao dao;
    private TwitterService twitterService;

    @Before
    public void setUp() throws Exception {
        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(
                System.getenv("consumerKey"), System.getenv("consumerSecret"),
                System.getenv("accessToken"), System.getenv("tokenSecret"));
        this.dao = new TwitterDao(twitterHttpHelper);
        Tweet postTweet = TweetUtils.buildTweet(test, lon, lat);
        Tweet tweet = dao.create(postTweet);
        this.testId = tweet.getId_str();
        this.twitterService = new TwitterService(this.dao);
    }

    @Test
    public void postTweet() {
        Tweet postTweet = TweetUtils.buildTweet(post, lon, lat);
        try {
            System.out.println(JsonParser.toJson(postTweet,true,false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Tweet to JSON", e);
        }
        Tweet tweet = twitterService.postTweet(postTweet);
        this.postId = tweet.getId_str();
        assertEquals(post, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
    }

    @Test
    public void showTweet() {
        Tweet tweet = twitterService.showTweet(testId);
        assertEquals(test, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
    }

    @Test
    public void showTweetSelect() {
        String[] fields = new String[] {"text"};
        Tweet tweet = twitterService.showTweet(testId, fields);
        assertEquals(test, tweet.getText());
        assertNull(tweet.getCoordinates());
    }

    @Test
    public void deleteTweets() {
        List<Tweet> tweets = twitterService.deleteTweets(new String[]{testId});
        assertEquals(1, tweets.size());
        Tweet tweet = tweets.get(0);
        assertEquals(test, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
        testId = null;
    }

    @After
    public void tearDown() {
        if (postId != null){
            dao.deleteById(postId);
        }
        if (testId != null) {
            dao.deleteById(testId);
        }
    }
}
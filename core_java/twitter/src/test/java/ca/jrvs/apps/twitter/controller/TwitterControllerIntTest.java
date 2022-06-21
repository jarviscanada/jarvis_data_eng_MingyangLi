package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {
    private TwitterDao dao;
    private TwitterService twitterService;
    private TwitterController twitterController;
    private String postId;
    private String testId;
    private final String post = "sometext";
    private final String test = "test";
    private final Double lon = -100.0;
    private final Double lat = 10.0;

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
        this.twitterController = new TwitterController(this.twitterService);
    }

    @Test
    public void postTweet() {
        Tweet tweet = twitterController.postTweet(new String[]{"post", post, "[" + lat + ":" + lon + "]"});
        this.postId = tweet.getId_str();
        assertEquals(post, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
    }

    @Test
    public void showTweet() {
        Tweet tweet = twitterController.showTweet(new String[]{"show", testId});
        assertEquals(test, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);

        tweet = twitterController.showTweet(new String[]{"show", testId, "[text]"});
        assertEquals(test, tweet.getText());
        assertNull(tweet.getCoordinates());
    }

    @Test
    public void deleteTweet() {
        Tweet tweet = twitterController.postTweet(new String[]{"post", post, "[" + lat + ":" + lon + "]"});
        this.postId = tweet.getId_str();
        List<Tweet> tweets = twitterController.deleteTweet(new String[]{"delete", "[" + postId + "," + testId + "]"});

        assertEquals(post, tweets.get(0).getText());
        assertNotNull(tweets.get(0).getCoordinates());
        assertEquals(2,tweets.get(0).getCoordinates().getCoordinates().length);
        assertEquals(lon,tweets.get(0).getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweets.get(0).getCoordinates().getCoordinates()[1]);
        postId = null;

        assertEquals(test, tweets.get(1).getText());
        assertNotNull(tweets.get(1).getCoordinates());
        assertEquals(2,tweets.get(1).getCoordinates().getCoordinates().length);
        assertEquals(lon,tweets.get(1).getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweets.get(1).getCoordinates().getCoordinates()[1]);
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
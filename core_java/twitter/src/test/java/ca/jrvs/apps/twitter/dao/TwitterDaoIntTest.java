package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {
    static private String id_str;
    private final String text = "@Mingyang_jrvs dao_test #test #dao #junit";
    private final Double lon = -100.0;
    private final Double lat = 10.0;
    private TwitterDao dao;
    @Before
    public void setUp() throws Exception {
        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(
                System.getenv("consumerKey"), System.getenv("consumerSecret"),
                System.getenv("accessToken"), System.getenv("tokenSecret"));
        this.dao = new TwitterDao(twitterHttpHelper);
    }

    @Test
    public void create() {
        Tweet postTweet = TweetUtils.buildTweet(text, lon, lat);
        try {
            System.out.println(JsonParser.toJson(postTweet,true,false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Tweet to JSON", e);
        }
        Tweet tweet = dao.create(postTweet);
        id_str = tweet.getId_str();
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
    }

    @Test
    public void findById() {
        System.out.println(id_str);
        Tweet tweet = dao.findById(id_str);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
    }

    @Test
    public void deleteById() {
        Tweet tweet = dao.deleteById(id_str);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
    }
}
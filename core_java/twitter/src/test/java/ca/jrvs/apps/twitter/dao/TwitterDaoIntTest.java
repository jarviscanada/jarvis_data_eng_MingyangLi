package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {
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
        Tweet postTweet = new Tweet();
        String text = "sometext";
        Float lon = -1f;
        Float lat = 1f;
        Float[] floats = new Float[2];
        floats[0] = lon;
        floats[1] = lat;
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(floats);
        postTweet.setCoordinates(coordinates);
        postTweet.setText(text);
        try {
            System.out.println(JsonParser.toJson(postTweet,true,false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Tweet to JSON", e);
        }
        Tweet tweet = dao.create(postTweet);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().length);
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);


    }

    @Test
    public void findById() {
        Tweet tweet = dao.findById("1537526787642736641");
        assertEquals("sometext", tweet.getText());
    }

    @Test
    public void deleteById() {
        Tweet tweet = dao.deleteById("1537526787642736641");
        assertEquals("sometext", tweet.getText());
    }
}
package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtils {
    public static Tweet buildTweet(String text, Double longitude, Double latitude) {
        Tweet tweet = new Tweet();
        Double[] doubles = new Double[2];
        doubles[0] = longitude;
        doubles[1] = latitude;
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(doubles);
        tweet.setCoordinates(coordinates);
        tweet.setText(text);
        return tweet;
    }
}

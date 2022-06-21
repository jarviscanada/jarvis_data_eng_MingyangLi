package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {
    private CrdDao dao;

    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    /**
     * Validate and post a user input Tweet
     *
     * @param tweet tweet to be created
     * @return created tweet
     * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long out of range
     */
    @Override
    public Tweet postTweet(Tweet tweet) throws IllegalArgumentException {
        if (tweet.getText().length() > 140) {
            throw new IllegalArgumentException("over 140 characters");
        }
        Double lon = tweet.getCoordinates().getCoordinates()[0];
        Double lat = tweet.getCoordinates().getCoordinates()[1];
        if (Math.abs(lon) > 180 || Math.abs(lat) > 90) {
            throw new IllegalArgumentException("invalid geo tag");
        }
        return (Tweet) dao.create(tweet);
    }

    /**
     * Search a tweet by ID
     *
     * @param id     tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) throws IllegalArgumentException {
        Tweet tweet = (Tweet) dao.findById(id);
        tweet.select(fields);
        return tweet;
    }

    public Tweet showTweet(String id) {
        return showTweet(id, null);
    }

    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) throws IllegalArgumentException {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (String id : ids) {
            Tweet tweet = (Tweet) dao.deleteById(id);
            tweets.add(tweet);
        }
        return tweets;
    }
}

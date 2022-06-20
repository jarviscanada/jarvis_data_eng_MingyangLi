package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class TwitterDao implements CrdDao<Tweet, String> {
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy/";
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    /**
     * Create an entity(Tweet) to the underlying storage
     *
     * @param entity entity that to be created
     * @return created entity
     */
    @Override
    public Tweet create(Tweet entity) {
        URI uri;
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        try {
            uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(entity.getText())
                    + AMPERSAND + "long" + EQUAL + entity.getCoordinates().getCoordinates()[0]
                    + AMPERSAND + "lat" + EQUAL + entity.getCoordinates().getCoordinates()[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponse(response);
    }

    /**
     * Find an entity(Tweet) by its id
     *
     * @param s entity id
     * @return Tweet entity
     */
    @Override
    public Tweet findById(String s) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid tweet id", e);
        }
        HttpResponse response = httpHelper.httpGet(uri);
        return parseResponse(response);
    }

    /**
     * Delete an entity(Tweet) by its ID
     *
     * @param s of the entity to be deleted
     * @return deleted entity
     */
    @Override
    public Tweet deleteById(String s) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + DELETE_PATH + s + ".json");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid tweet id", e);
        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponse(response);
    }

    private Tweet parseResponse(HttpResponse response) {
        Tweet tweet = new Tweet();
        int status = response.getStatusLine().getStatusCode();
        if (status != HTTP_OK) {
            throw new RuntimeException("Unexpected HTTP status:" + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response");
        }

        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to String", e);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> map = mapper.readValue(jsonStr, Map.class);
            tweet.setCreated_at((String) map.get("created_at"));
            tweet.setId((Long) map.get("id"));
            tweet.setId_str((String) map.get("id_str"));
            tweet.setText((String) map.get("text"));
            Entities entities = new Entities();
            ArrayList<Hashtag> hashtagArrayList = (ArrayList<Hashtag>) ((Map) map.get("entities")).get("hashtags");
            Hashtag[] hashtags = new Hashtag[hashtagArrayList.size()];
            hashtagArrayList.toArray(hashtags);
            entities.setHashtags(hashtags);
            ArrayList<UserMention> userMentionArrayList = (ArrayList<UserMention>) ((Map) map.get("entities")).get("user_mentions");
            UserMention[] userMentions = new UserMention[userMentionArrayList.size()];
            userMentionArrayList.toArray(userMentions);
            entities.setHashtags(hashtags);
            entities.setUser_mentions(userMentions);
            tweet.setEntities(entities);
            Coordinates coordinates = new Coordinates();
            ArrayList<Double> doubleArrayList = (ArrayList<Double>) ((Map) map.get("coordinates")).get("coordinates");
            Double[] doubles = new Double[doubleArrayList.size()];
            doubleArrayList.toArray(doubles);
            coordinates.setCoordinates(doubles);
            coordinates.setType((String) ((Map) map.get("coordinates")).get("type"));
            tweet.setCoordinates(coordinates);
            tweet.setRetweet_count((Integer) map.get("retweet_count"));
            tweet.setFavorite_count((Integer) map.get("favorite_count"));
            tweet.setFavorited((Boolean) map.get("favorited"));
            tweet.setRetweeted((Boolean) map.get("retweeted"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert JSON str to Object", e);
        }

        return tweet;
    }

}



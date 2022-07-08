package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.*;

public class TweetUtils {
    public static Tweet buildTweet(String text, Double longitude, Double latitude) {

        Tweet tweet = new Tweet();
        Double[] doubles = new Double[2];
        doubles[0] = longitude;
        doubles[1] = latitude;
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(doubles);
        tweet.setCoordinates(coordinates);

        int atCount = 0;
        int tagCount = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '@') {
                atCount++;
            } else if (text.charAt(i) == '#') {
                tagCount++;
            }
        }
        UserMention[] userMentions = new UserMention[atCount];
        Hashtag[] hashtags = new Hashtag[tagCount];
        if (atCount > 0) {
            String[] users = text.split("@");
            for (int i = 0; i < atCount; i++) {
                UserMention userMention = new UserMention();
                userMention.setName(users[i + 1].split(" ")[0]);
                userMentions[i] = userMention;
            }
            text = text.split("@")[atCount].split(" ",2)[1];
        }
        if (tagCount > 0) {
            String[] tags = text.split("#");
            for (int i = 0; i < tagCount; i++) {
                Hashtag hashtag = new Hashtag();
                hashtag.setText(tags[i + 1].split(" ")[0]);
                hashtags[i] = hashtag;
            }
            text = text.split("#")[0];
            if (text.charAt(text.length() - 1) == ' ') {
                text = text.substring(0, text.length() - 1);
            }
        }
        Entities entities = new Entities();
        entities.setUser_mentions(userMentions);
        entities.setHashtags(hashtags);
        tweet.setEntities(entities);


        for (int i = userMentions.length - 1; i >= 0; i--) {
            text = "@" + userMentions[i].getName() + " " + text;
        }

        for (int i = 0; i < hashtags.length; i++) {
            text = text + " #" + hashtags[i].getText();
        }
        tweet.setText(text);
        return tweet;
    }
}

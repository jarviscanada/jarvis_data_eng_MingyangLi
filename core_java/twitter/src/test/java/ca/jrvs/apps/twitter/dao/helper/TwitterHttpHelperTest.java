package ca.jrvs.apps.twitter.dao.helper;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

public class TwitterHttpHelperTest {

    @Test
    public void httpPost() throws Exception{
        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(
                System.getenv("consumerKey"), System.getenv("consumerSecret"),
                System.getenv("accessToken"), System.getenv("tokenSecret"));
        HttpResponse response = twitterHttpHelper.httpPost(
                new URI("https://api.twitter.com/1.1/statuses/update.json?status="));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void httpGet() throws Exception{
        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(
                System.getenv("consumerKey"), System.getenv("consumerSecret"),
                System.getenv("accessToken"), System.getenv("tokenSecret"));
        HttpResponse response = twitterHttpHelper.httpGet(
                new URI("https://api.twitter.com/1.1/statuses/show.json?id=1537481761458102272"));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
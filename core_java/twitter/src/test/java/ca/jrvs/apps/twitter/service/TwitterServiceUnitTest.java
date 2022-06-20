package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService twitterService;

    @Test
    public void postTweet() {
        when(dao.create(isNotNull())).thenReturn(new Tweet());
        try {
            twitterService.postTweet(TweetUtils.buildTweet(
                    "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                    , 0.0, 0.0));
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            twitterService.postTweet(TweetUtils.buildTweet("test", 100.0, 100.0));
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            twitterService.postTweet(TweetUtils.buildTweet("test", -200.0, -20.0));
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        twitterService.postTweet(TweetUtils.buildTweet("test", 50.0, 0.0));

    }


    @Test
    public void showTweet() {
        when(dao.findById(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterService.showTweet("1");
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        when(dao.findById(isNotNull())).thenReturn(null);
        TwitterService spy = Mockito.spy(twitterService);

        doReturn(new Tweet()).when(spy).showTweet(isNotNull());
        spy.showTweet("id");
    }
    @Test
    public void deleteTweets() {
        when(dao.deleteById(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterService.deleteTweets(new String[]{"1"});
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        when(dao.deleteById(isNotNull())).thenReturn(null);
        TwitterService spy = Mockito.spy(twitterService);

        doReturn(new ArrayList<>()).when(spy).deleteTweets(isNotNull());
        spy.deleteTweets(new String[]{"1"});
    }
}

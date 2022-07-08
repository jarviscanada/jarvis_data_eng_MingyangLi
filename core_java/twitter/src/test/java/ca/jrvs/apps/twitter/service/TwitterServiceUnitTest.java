package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService twitterService;

    @Test
    public void postTweet() {
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
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            twitterService.postTweet(TweetUtils.buildTweet("service_unit_test", 100.0, 100.0));
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            twitterService.postTweet(TweetUtils.buildTweet("service_unit_test", -200.0, -20.0));
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        when(dao.create(isNotNull())).thenReturn(new Tweet());
        TwitterService spy = Mockito.spy(twitterService);
        spy.postTweet(TweetUtils.buildTweet("service_unit_test", 50.0, 0.0));

    }


    @Test
    public void showTweet() {
        try {
            twitterService.showTweet("1");
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        when(dao.findById(isNotNull())).thenReturn(new Tweet());
        TwitterService spy = Mockito.spy(twitterService);
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
        when(dao.deleteById(isNotNull())).thenReturn(new Tweet());
        TwitterService spy = Mockito.spy(twitterService);
        spy.deleteTweets(new String[]{"1"});
    }
}

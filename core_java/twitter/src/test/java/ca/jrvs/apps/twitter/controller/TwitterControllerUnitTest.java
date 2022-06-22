package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.service.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
    @Mock
    Service service;

    @InjectMocks
    TwitterController twitterController;

    @Test
    public void postTweet() {
        when(service.postTweet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterController.postTweet(new String[]{
                    "post",
                    "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890"
                            + "01234567891234567890",
                    "0.0:0.0"
            });
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            twitterController.postTweet(new String[]{"post", "controller_unit_test", "100.0:100.0"});
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            twitterController.postTweet(new String[]{"post", "controller_unit_test", "-20.0:-200.0"});
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        when(service.postTweet(isNotNull())).thenReturn(null);
        TwitterController spy = Mockito.spy(twitterController);
        spy.postTweet(new String[]{"post", "controller_unit_test", "-20.0:-20.0"});
    }

    @Test
    public void showTweet() {
        when(service.showTweet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterController.showTweet(new String[]{"show", "1"});
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        when(service.showTweet(isNotNull())).thenReturn(null);
        TwitterController spy = Mockito.spy(twitterController);
        spy.showTweet(new String[]{"show", "1"});
    }

    @Test
    public void deleteTweet() {
        when(service.deleteTweets(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            twitterController.deleteTweet(new String[]{"delete", "1,2"});
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        when(service.deleteTweets(isNotNull())).thenReturn(null);
        TwitterController spy = Mockito.spy(twitterController);
        spy.deleteTweet(new String[]{"delete", "1,2"});
    }
}

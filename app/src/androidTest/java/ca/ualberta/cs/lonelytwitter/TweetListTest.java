package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by akmcinto on 9/30/15.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 implements MyObserver {
    private boolean wasNotified;

    public TweetListTest() {
        super(LonelyTwitterActivity.class);
    }

    // Must prefix all tests with 'test'
    public void testDeleteTweet() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new NormalTweet("hello!");
        tweetList.addTweet(tweet);
        tweetList.removeTweet(tweet);
        assertFalse(tweetList.hasTweet(tweet));
    }

    public void testHasTweet() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new NormalTweet("hello!");
        assertFalse(tweetList.hasTweet(tweet));
        tweetList.addTweet(tweet);
        assertTrue(tweetList.hasTweet(tweet));
    }

    public void testAddTweet() {
        Object test = null;
        TweetList tweetList = new TweetList();
        Tweet tweet = new NormalTweet("hello!");
        tweetList.addTweet(tweet);
        assertTrue(tweetList.hasTweet(tweet));
        try {
            tweetList.addTweet(tweet);
        }
        catch (IllegalArgumentException e) {
            test = 5;
        }
        assertNotNull(test);
    }

    public void testTweetCount() {
        TweetList tweetList = new TweetList();
        Tweet tweet1 = new NormalTweet("hello!");
        tweetList.addTweet(tweet1);
        Tweet tweet2 = new NormalTweet("hello!");
        tweetList.addTweet(tweet2);
        Tweet tweet3 = new NormalTweet("hello!");
        tweetList.addTweet(tweet3);
        assertTrue(tweetList.getCount().equals(3));
        tweetList.removeTweet(tweet2);
        assertTrue(tweetList.getCount().equals(2));
    }

    public void testGetTweet() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new NormalTweet("hello!");
        tweetList.addTweet(tweet);
        Tweet returnedTweet = tweetList.getTweet(0);
        assertTrue((tweet.date.equals(returnedTweet.date)) && (tweet.getText().equals(returnedTweet.getText())));
    }

    public void testGetTweets() {
        TweetList tweetList = new TweetList();
        Tweet tweet1 = new NormalTweet("hello!");
        tweetList.addTweet(tweet1);
        for (int i = 0; i < 1000000; i++) {
            // delay for a sec
        }
        Tweet tweet2 = new NormalTweet("hello!");
        tweetList.addTweet(tweet2);
        ArrayList<Tweet> sortedTweets = tweetList.getTweets();
        assertTrue(sortedTweets.get(0).getDate().before(sortedTweets.get(1).getDate()));
    }

    public void testTweetListChanged() {
        wasNotified = false;
        TweetList tweetList = new TweetList();
        Tweet tweet = new NormalTweet("hello!");
        tweetList.addObserver(this);
        assertFalse(wasNotified);
        tweetList.addTweet(tweet);
        assertTrue(wasNotified);
    }

    public void myNotify() {
        wasNotified = true;
    }
}
package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by akmcinto on 9/30/15.
 */
public class TweetList {

    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public void addTweet(Tweet tweet) {
        if (this.hasTweet(tweet)) {
            throw new IllegalArgumentException("Tweet already added.");
        }
        else {
            tweets.add(tweet);
        }
    }

    public void removeTweet(Tweet tweet) {
        tweets.remove(tweet);
    }

    public boolean hasTweet(Tweet tweet) {
        return tweets.contains(tweet);
    }

    public Tweet getTweet(int index) {
        return tweets.get(index);
    }

    public ArrayList<Tweet> getTweets() {
        Collections.sort(tweets, new Comparator<Tweet>() {
            public int compare(Tweet lhs, Tweet rhs) {
                if (lhs.getDate().before(rhs.getDate())) {
                    return -1;
                } else if (lhs.getDate().after(rhs.getDate())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return tweets;
    }

    public Integer getCount() {
        return tweets.size();
    }

}

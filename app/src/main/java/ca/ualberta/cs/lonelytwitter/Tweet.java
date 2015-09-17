package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akmcinto on 9/16/15.
 */
public abstract class Tweet implements Tweetable {

    private String text;
    private Date date;
    private ArrayList<CurrentMood> moodList;

    public Tweet(String tweet, Date date) {
        this.setText(tweet);
        this.date = date;
    }

    public Tweet(String tweet) {
        this.setText(tweet);
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        // Some exceptions, like IOException, must be caught and handled
        if (text.length() <= 140) {
            this.text = text;
        } else {
            throw new IllegalArgumentException("Tweet was too long");
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addMood(CurrentMood mood) {
        moodList.add(mood);
    }

    public abstract Boolean isImportant();
}

package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by akmcinto on 9/16/15.
 */
public class ImportantTweet extends Tweet {

    public ImportantTweet(String tweet, Date date) {
        // Must include super declaration
        super(tweet, date);

        this.setText(tweet);
        this.setDate(date);
    }

    public ImportantTweet(String tweet) {
        super(tweet);
    }

    @Override
    public Boolean isImportant() {
        return Boolean.TRUE;
    }

    @Override
    public String getText() {
        return super.getText() + "!!!";
    }
}

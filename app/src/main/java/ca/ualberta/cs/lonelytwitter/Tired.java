package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by akmcinto on 9/16/15.
 */
public class Tired extends CurrentMood {

    public Tired(Date date) {
        super(date);
    }

    public Tired() {
        super();
    }

    @Override
    public String formatMood() {
        return "Feeling tired (*-_-)";
    }
}

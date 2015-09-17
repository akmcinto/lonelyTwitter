package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by akmcinto on 9/16/15.
 */
public class SoConfused extends CurrentMood {

    public SoConfused(Date date) {
        super(date);
    }

    public SoConfused() {
        super();
    }

    @Override
    public String formatMood() {
        return "Feeling so confused... (o.O)?";
    }

}

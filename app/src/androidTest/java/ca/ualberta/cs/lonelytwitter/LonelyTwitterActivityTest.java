package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2 {

    private EditText bodyText;
    private Button saveButton;
    private Button saveEditsButton;
    private ListView oldTweetsList;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    // UI test!
    public void testEditTweet() {
        // When you call get activity android will start up app and the activity
        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();

        // Reset the app to a known state
        activity.getTweets().clear();

        // Add a tweet using UI
        bodyText = activity.getBodyText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                bodyText.setText("test tweet");
            }
        });
        getInstrumentation().waitForIdleSync();

        saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Make sure tweet was actually added
        oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("test tweet", tweet.getText());

        // ensure tweet editor activity starts up
        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditTweetActivity.class.getName(),
                        null, false);

        // Click on the tweet to edit
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetsList.getChildAt(0);
                oldTweetsList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditTweetActivity receiverActivity = (EditTweetActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditTweetActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // test that editor starts up with the right tweet in it
        EditTweetActivity editActivity = (EditTweetActivity) getActivity();
        EditText editTweetText = editActivity.getEditText();
        assertTrue(editTweetText.getText().equals("test text"));

        // test that we can edit that tweet
        editTweetText.setText("new test text");
        assertTrue(editTweetText.getText().equals("new test text"));

        // test that we can push some kind of save button for that tweet
        saveEditsButton = editActivity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveEditsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // the new modified tweet text was actually saved
        tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("new test tweet", tweet.getText());

        // the new modified tweet text is displayed on the other activity
        oldTweetsList = activity.getOldTweetsList();
        tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("new test tweet", tweet.getText());

        // Clean up our activities at the end of our test
        editActivity.finish();
        receiverActivity.finish();
    }
}
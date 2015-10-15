package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditTweetActivity extends Activity {
    private Button saveButton;
    private EditText editText;
    private static final String FILENAME = "file.sav";
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> adapter;
    private Intent intent;
    int tweetIndex;

    public Button getSaveButton() {
        return saveButton;
    }
    public EditText getEditText() {
        return editText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);

        intent = new Intent(this, EditTweetActivity.class);
        intent.putExtra("editTweetIndex", tweetIndex);

        // Load tweets from file
        loadFromFile();

        editText = (EditText) findViewById(R.id.editTweet);
        saveButton = (Button) findViewById(R.id.saveTweetEdit);

        saveButton.setOnClickListener(new View.OnClickListener() { // Controller

            public void onClick(View v) { // Controller
                setResult(RESULT_OK); // Model
                String text = editText.getText().toString(); // Model
                tweets.set(tweetIndex, new NormalTweet(text)); // Model
                saveInFile(); // Model
                adapter.notifyDataSetChanged(); // Controller
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFromFile() {  // Model
        try {  // Controller
            FileInputStream fis = openFileInput(FILENAME);  // Model
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));  // Model
            Gson gson = new Gson();  // Model
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type arrayListType = new TypeToken<ArrayList<NormalTweet>>() {}.getType();  // Model
            tweets = gson.fromJson(in, arrayListType);  // Model

        } catch (FileNotFoundException e) {  // Controller
            tweets = new ArrayList<Tweet>();  // Model
        } catch (IOException e) {  // Controller
            throw new RuntimeException(e);  // View
        }
    }

    private void saveInFile() {  // Model
        try {  // Controller
            // Can be write only, because we are making new Gson objects each time
            FileOutputStream fos = openFileOutput(FILENAME, 0);  // Model
            // New outpu stream writer for output stream in a buffered writer
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));  // Model
            Gson gson = new Gson();  // Model
            gson.toJson(tweets, out);  // Model
            out.flush();  // Model
            fos.close();  // Model
        } catch (FileNotFoundException e) {  // Controller
            throw new RuntimeException(e);  // View
        } catch (IOException e) {  // Controller
            throw new RuntimeException(e);  // View
        }
    }
}

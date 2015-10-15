package ca.ualberta.cs.lonelytwitter;  // Model

import java.io.BufferedReader;  // Model
import java.io.BufferedWriter;  // Model
import java.io.FileInputStream;  // Model
import java.io.FileNotFoundException;  // Model
import java.io.FileOutputStream;  // Model
import java.io.IOException;  // Model
import java.io.InputStreamReader;  // Model
import java.io.OutputStreamWriter;  // Model
import java.lang.reflect.Type;  // Model
import java.util.ArrayList;  // Model

import android.app.Activity;  // Model
import android.content.Intent;
import android.os.Bundle;  // Model
import android.view.View;  // Model
import android.widget.AdapterView;
import android.widget.ArrayAdapter;  // Model
import android.widget.Button;  // Model
import android.widget.EditText;  // Model
import android.widget.ListView;  // Model

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {  // View/Controller

	private static final String FILENAME = "file.sav"; // Model
	private EditText bodyText; // View
	private ListView oldTweetsList; // View
	private ArrayAdapter<Tweet> adapter; // Controller
	private Button saveButton;
	private ArrayList<Tweet> tweets = new ArrayList<Tweet>(); // Model
	private LonelyTwitterActivity activity = this;

	public EditText getBodyText() {
		return bodyText;
	}
	public ArrayList<Tweet> getTweets() {
		return tweets;
	}
	public Button getSaveButton() {
		return saveButton;
	}
	public ListView getOldTweetsList() {
		return oldTweetsList;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {  // Controller

		super.onCreate(savedInstanceState);  // Controller
		setContentView(R.layout.main); // View

		bodyText = (EditText) findViewById(R.id.body);  // View
		saveButton = (Button) findViewById(R.id.save);
		Button clearButton = (Button) findViewById(R.id.clear); // View/Controller
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); // View

		saveButton.setOnClickListener(new View.OnClickListener() { // Controller

			public void onClick(View v) { // Controller
				setResult(RESULT_OK); // Model
				String text = bodyText.getText().toString(); // Model
				tweets.add(new NormalTweet(text)); // Model
				saveInFile(); // Model
				adapter.notifyDataSetChanged(); // Controller
			}
		});

		clearButton.setOnClickListener(new View.OnClickListener() { // Controller

			public void onClick(View v) { // Controller
				// Clear stream
				adapter.clear(); // Controller
				// Clear text input
				EditText textInput = (EditText) findViewById(R.id.body);  // View
				textInput.setText(""); // View
				// Clear file
				tweets.clear(); // Controller
				saveInFile(); // Model
			}

		});

		oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, EditTweetActivity.class);
				// Paresh Mayani, http://stackoverflow.com/questions/7074097/how-to-pass-integer-from-one-activity-to-another, 2015-10-14
				intent.putExtra("editTweetIndex", position);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onStart() { // Controller
		super.onStart(); // Controller
		loadFromFile(); // Model
		adapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweets); // Model
		oldTweetsList.setAdapter(adapter); // Model
		adapter.notifyDataSetChanged(); // Controller
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
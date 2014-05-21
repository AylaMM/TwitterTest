package com.example.twitterexample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TwitterActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);	
		//Get the textview that is going to show the tweets. (look in the layout folder)
		TextView textView =(TextView)findViewById(R.id.text); 		
		TwitterTask asyncTask = new TwitterTask();
		asyncTask.execute(textView);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_twitter, menu);
		return true;
	}
}

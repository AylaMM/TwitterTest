package com.example.twitterexample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.os.AsyncTask;
import android.widget.TextView;

/***
 * Logs in to Twitter and fetches the latest tweets from the user malmohogskola. All this is done asynchronously. 
 * @author Ayla
 *
 */
public class TwitterTask extends AsyncTask<TextView, Void, String> {

	private TextView textView;

	@Override
	protected String doInBackground(TextView... params) {
		String text = "";
		//Kids remember, always be safe!
		if(params.length > 0) {
			this.textView = params[0];
		}
		Twitter twitter = logIn();
		//Get the tweets
		try {
			List<twitter4j.Status> tweets = twitter.getUserTimeline("malmohogskola");
			for (twitter4j.Status status : tweets) {
				text += status.getUser().getName() + " " + processDate(status) + ": " + status.getText() + "\n\n";				
			}
		} catch (TwitterException e) {
			text = "Unable to connect to Twitter";
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * Returns the date and time the input tweet was created
	 * @param status
	 * @return
	 */
	private String processDate(twitter4j.Status status) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return  df.format(status.getCreatedAt());
	}

	/**
	 * Logs the user in
	 * @return Twitter object
	 */
	private Twitter logIn() {
		Twitter twitter = TwitterFactory.getSingleton();
		try {
			//Ugly way of checking if the user is already logged in. If the exception is caught, it means that the user is not logged in => log in.
			twitter.getUserTimeline();
		} catch (Exception E) {
			//It may be unwise to store the credentials openly like this...
			twitter.setOAuthConsumer("QsyDyO6A0jD6zLuNkTKaqrnfD", "ma8raxfkUJrNKMvhoSSGW5GnMwIPQCH338KWPR39xxWs1n0PVC");
			AccessToken accestoken = new AccessToken("121228957-wOM4WI62pMyLizvdbVrak4XFT0iFnXbfQEkoiUX7", "6yr9kV8uDEASIUSYV1ZCpuYFunEqGzCb0jcyIlmFd75JY");
			twitter.setOAuthAccessToken(accestoken);
		}
		return twitter;
	}

	@Override
	protected void onPostExecute (String result){
		//Called when the asynctask is complete => update the textview.
		//This method is executed on the UI thread
		//Kids remember, always be safe!
		if(textView != null) {
			textView.setText(result);
		}
	}
}

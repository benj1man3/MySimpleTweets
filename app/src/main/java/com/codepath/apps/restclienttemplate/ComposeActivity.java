package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient twitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        twitterClient = TwitterApp.getRestClient(this);
    }

    // ActivityNamePrompt.java -- launched for a result
    public void onSubmit(View v) {
        EditText etName = (EditText) findViewById(R.id.editText);
        twitterClient.sendTweet(etName.getText().toString(),
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //super.onSuccess(statusCode, headers, response);
                        try {

                            Tweet tweet= Tweet.fromJSON(response);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            // Activity finished ok, return the data
                            setResult(RESULT_OK, intent); // set result code and bundle data for response
                            finish(); // closes the activity, pass data to parent
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}

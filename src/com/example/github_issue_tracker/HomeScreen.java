package com.example.github_issue_tracker;


import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class HomeScreen extends Activity implements OnTaskCompleted {
	private String username, reponame;
	AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        builder = new AlertDialog.Builder(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }
    
    public void search(View v) {
    	username = ((EditText) findViewById(R.id.user_name)).getText().toString();
    	reponame = ((EditText) findViewById(R.id.repo_name)).getText().toString();
    	try {
			new ApiCall(this, true).execute(new URL(MainView.formURL(username, reponame)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void onTaskCompleted(String result) {
		if(result == "Error") {
			builder.setTitle("Error").setMessage("Username or Reponame are not correct");
			builder.create().show();
		} else {
			Intent intent = new Intent(this, MainView.class);
		    intent.putExtra("user_name", username);
		    intent.putExtra("repo_name", reponame);
		    startActivity(intent);
		}
	}
}

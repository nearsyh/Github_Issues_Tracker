package com.example.github_issue_tracker;


import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class HomeScreen extends Activity implements OnTaskCompleted {
	private String username, reponame;
	private EditText user_name, repo_name;
	private boolean isValid = true;
	//private boolean isChecked = false, pressed = false;
	AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        builder = new AlertDialog.Builder(this);
        user_name = ((EditText) findViewById(R.id.user_name));
    	repo_name = ((EditText) findViewById(R.id.repo_name));
    	/*
    	username = user_name.getText().toString();
		reponame = repo_name.getText().toString();
		
    	TextWatcher checkValid = new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				username = user_name.getText().toString();
				reponame = repo_name.getText().toString();
				try {
					new ApiCall(HomeScreen.this, true).execute(new URL(MainView.formURL(username, reponame)));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				pressed = false;
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				isChecked = false;
			}
    	};
    	user_name.addTextChangedListener(checkValid);
    	repo_name.addTextChangedListener(checkValid);
    	*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }
    
    public void search(View v) {
    	/*
    	pressed = true;
    	if(isChecked) {
    		onTaskCompleted(isValid ? "Yeah" : "Error");
    		return;
    	}
    	*/
    	username = user_name.getText().toString();
    	reponame = repo_name.getText().toString();
    	try {
			new ApiCall(this, true).execute(new URL(MainView.formURL(username, reponame)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void onTaskCompleted(String result) {
		//isChecked = true;
		isValid = (result != "Error");
		//if(!pressed) return;
		if(!isValid) {
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

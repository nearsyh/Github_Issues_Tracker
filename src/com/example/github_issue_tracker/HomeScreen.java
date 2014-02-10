package com.example.github_issue_tracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class HomeScreen extends Activity {
	private String username, reponame;
	private EditText user_name, repo_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        user_name = (EditText)findViewById(R.id.user_name);
        repo_name = (EditText)findViewById(R.id.repo_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }
    
    public void search(View v) {
    	username = user_name.getText().toString();
    	reponame = repo_name.getText().toString();
    	Intent intent = new Intent(this, MainView.class);
    	intent.putExtra("user_name", username);
    	intent.putExtra("repo_name", reponame);
    	startActivity(intent);
    }
}

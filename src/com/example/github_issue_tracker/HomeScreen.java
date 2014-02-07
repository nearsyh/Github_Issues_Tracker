package com.example.github_issue_tracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }
    
    public void search(View v) {
    	Intent intent = new Intent(this, MainView.class);
    	EditText user_name = (EditText) findViewById(R.id.user_name);
        intent.putExtra("user_name", user_name.getText().toString());
        EditText repo_name = (EditText) findViewById(R.id.repo_name);
        intent.putExtra("repo_name", repo_name.getText().toString());
        startActivity(intent);
    }
}

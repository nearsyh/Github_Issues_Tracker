package com.example.github_issue_tracker;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

// Contain the view
public class MainView extends ListActivity {
	private String user, repo;
	private ListView issues_list;
	private IssueData issueData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_view);
		Intent intent = getIntent();
	    user = intent.getStringExtra("user_name");
	    repo = intent.getStringExtra("repo_name");
	    issues_list = (ListView) findViewById(R.id.list);
	    issueData = new IssueData(user, repo);
	    update();
	}
	
	private void update() {
		issueData.update();
		for(Issue i : issueData.getIssuesList()) {
			IssueView temp = new IssueView(this);
			temp.setContent(i);
			issues_list.addFooterView(temp);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_view, menu);
		return true;
	}

}

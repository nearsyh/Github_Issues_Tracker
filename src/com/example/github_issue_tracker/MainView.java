package com.example.github_issue_tracker;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

// Contain the view
@SuppressLint("DefaultLocale")
public class MainView extends ListActivity implements OnTaskCompleted{
	public static final String ISSUE = "issue", COMMENT = "comment";
	private String user, repo;
	private ListView issues_list_view;
	private InfoItemList issueData, commentData;
	private AlertDialog.Builder commentDialogBuilder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_view);
		Intent intent = getIntent();
	    user = intent.getStringExtra("user_name").toLowerCase();
	    repo = intent.getStringExtra("repo_name").toLowerCase();
	    issues_list_view = (ListView) findViewById(android.R.id.list);
	    issueData = new InfoItemList(this, formURL(user, repo), Issue.class);
	    commentDialogBuilder = new AlertDialog.Builder(this);
	    issues_list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				commentDialogBuilder = showCommentDialog(position);
			}
	    	
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_view, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(String result) {
		
		if(result.equals(Issue.class.getSimpleName())) {
			InfoListAdapter adapter = new InfoListAdapter(this, issueData);
			issues_list_view.setAdapter(adapter);
		} else {
			InfoListAdapter adapter = new InfoListAdapter(this, commentData);
			commentDialogBuilder.setAdapter(adapter, null);
			commentDialogBuilder.create().show();
		}
	}

	public AlertDialog.Builder showCommentDialog(int position) {
		commentDialogBuilder.setTitle("Comments of " + ((Issue)issueData.getIssuesList().get(position)).getTitle());
	    commentData = new InfoItemList(this, ((Issue)issueData.getIssuesList().get(position)).getCommentUrl(), Comment.class);
	    return commentDialogBuilder;
	}
	
/***** Utility *******/
	protected static String formURL(String u, String r) {
		return "https://api.github.com/repos/" + u + "/" + r + "/issues";
	}
}

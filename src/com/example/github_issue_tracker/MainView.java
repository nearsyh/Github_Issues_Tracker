package com.example.github_issue_tracker;

import java.util.HashMap;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

// Contain the view
@SuppressLint("DefaultLocale")
public class MainView extends ListActivity implements OnTaskCompleted, OnScrollListener {
	private String user, repo;
	private ListView issues_list_view;
	private InfoItemList issueData, commentData;
	private HashMap<String, InfoItemList> commentsCache;
	private AlertDialog commentDialog, loadingDialog;
	private boolean isLoading = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_view);
		Intent intent = getIntent();
	    user = intent.getStringExtra("user_name").toLowerCase();
	    repo = intent.getStringExtra("repo_name").toLowerCase();
	    issueData = new InfoItemList(this, formURL(user, repo), Issue.class);
	    issues_list_view = (ListView) findViewById(android.R.id.list);
	    loadingDialog = new AlertDialog.Builder(this).create();
	    loadingDialog.setMessage("Loading");
	    loadingDialog.show();
	    commentsCache = new HashMap<String, InfoItemList>();
	    issues_list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showCommentDialog(position);
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
		loadingDialog.dismiss();
		if(result.equals(Issue.class.getSimpleName())) {
			InfoListAdapter adapter = new InfoListAdapter(this, issueData);
			issues_list_view.setAdapter(adapter);
			issues_list_view.setOnScrollListener(this);
		} else {
			if(commentData.isReadingError()) {
				TextView errorInfo = new TextView(this);
				errorInfo.setText("Error with connection or url");
				commentDialog.setView(errorInfo);
			} else {
				InfoListAdapter adapter = new InfoListAdapter(this, commentData);
				ListView comments_list_view = new ListView(this);
				if(commentData.getSize() == 0) {
					commentDialog.setMessage("No comments for this issue");
				} else {
					comments_list_view.setAdapter(adapter);
					comments_list_view.setOnScrollListener(this);
					commentDialog.setView(comments_list_view);
				}
			}
			commentDialog.show();
			
		}
	}

	public void showCommentDialog(int position) {
		AlertDialog.Builder commentDialogBuilder = new AlertDialog.Builder(this);
	    commentDialogBuilder.setTitle("Comments of " + ((Issue)issueData.getIssuesList().get(position)).getTitle());
	    String lastCommentsURL = ((Issue)issueData.getIssuesList().get(position)).getCommentUrl();
	    // TODO test
	    if(commentsCache.containsKey(lastCommentsURL)) {
	    	commentData = commentsCache.get(lastCommentsURL);
	    	commentDialog = commentDialogBuilder.create();
	    	onTaskCompleted(Comment.class.getSimpleName());
	    } else {
	    	commentData = new InfoItemList(this, lastCommentsURL, Comment.class);
	    	loadingDialog.show();
	    	commentDialog = commentDialogBuilder.create();
	    	commentsCache.put(lastCommentsURL, commentData);
	    }
	}
	
	/***** Utility *******/
	protected static String formURL(String u, String r) {
		return "https://api.github.com/repos/" + u + "/" + r + "/issues";
	}


	/***** Listener ******/
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		int loadedItems = firstVisibleItem + visibleItemCount;
		if(((InfoListAdapter)view.getAdapter()).isFinished()) return;
		if(loadedItems == totalItemCount && isLoading == false){
			new LoadList((InfoListAdapter)view.getAdapter()).execute(loadedItems);
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}
}

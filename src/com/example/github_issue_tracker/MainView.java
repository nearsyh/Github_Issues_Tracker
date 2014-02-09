package com.example.github_issue_tracker;

import java.util.HashMap;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
//import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

// Contain the view
@SuppressLint("DefaultLocale")
public class MainView extends ListActivity implements OnTaskCompleted, OnScrollListener {
	private String user, repo;
	private ListView issues_list_view;
	private InfoItemList issueData, commentData;
	private HashMap<String, InfoItemList> commentsCache;
	private AlertDialog.Builder commentDialogBuilder;
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
	    commentDialogBuilder = new AlertDialog.Builder(this);
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
		if(result.equals(Issue.class.getSimpleName())) {
			InfoListAdapter adapter = new InfoListAdapter(this, issueData);
			issues_list_view.setAdapter(adapter);
			issues_list_view.setOnScrollListener(this);
		} else {
			InfoListAdapter adapter = new InfoListAdapter(this, commentData);
			ListView comments_list_view = new ListView(this);
			comments_list_view.setAdapter(adapter);
			comments_list_view.setOnScrollListener(this);
		    commentDialogBuilder.setView(comments_list_view);
			commentDialogBuilder.show();
			
		}
	}

	public void showCommentDialog(int position) {
	    commentDialogBuilder.setTitle("Comments of " + ((Issue)issueData.getIssuesList().get(position)).getTitle());
	    String lastCommentsURL = ((Issue)issueData.getIssuesList().get(position)).getCommentUrl();
	    // TODO test
	    if(commentsCache.containsKey(lastCommentsURL)) {
	    	commentData = commentsCache.get(lastCommentsURL);
	    	onTaskCompleted(Comment.class.getSimpleName());
	    } else {
	    	commentData = new InfoItemList(this, lastCommentsURL, Comment.class);
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

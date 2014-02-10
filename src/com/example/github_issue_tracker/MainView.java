package com.example.github_issue_tracker;

import java.util.HashMap;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
//import android.util.Log;
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
	private InfoItemList data;
	private HashMap<String, InfoItemList> commentsCache;
	private AlertDialog commentDialog, loadingDialog, errorDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_view);
		Intent intent = getIntent();
	    user = intent.getStringExtra("user_name").toLowerCase();
	    repo = intent.getStringExtra("repo_name").toLowerCase();
	    data = new InfoItemList(this, formURL(user, repo), Issue.class);
	    issues_list_view = (ListView) findViewById(android.R.id.list);
	    loadingDialog = new AlertDialog.Builder(this).create();
	    loadingDialog.setMessage("Loading");
	    loadingDialog.show();
	    errorDialog = new AlertDialog.Builder(this).create();
	    errorDialog.setMessage("Error with connection or the url");
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
	public void onTaskCompleted(final String result) {
		loadingDialog.dismiss();
		if(data.isReadingError()) {
			if(result.equals(Issue.class.getSimpleName())) {
				errorDialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface arg0) {
						MainView.this.finish();
					}
				});
			}
			errorDialog.show();
			return;
		}
		InfoListAdapter adapter = new InfoListAdapter(this, data);
		if(result.equals(Issue.class.getSimpleName())) {
			issues_list_view.setAdapter(adapter);
			issues_list_view.setOnScrollListener(this);
		} else {
			ListView comments_list_view = new ListView(this);
			if(data.getSize() == 0) {
				commentDialog.setMessage("No comments for this issue");
			} else {
				comments_list_view.setAdapter(adapter);
				comments_list_view.setOnScrollListener(this);
				commentDialog.setView(comments_list_view);
			}
			commentDialog.show();
		}
	}

	public void showCommentDialog(int position) {
	    loadingDialog.show();
		AlertDialog.Builder commentDialogBuilder = new AlertDialog.Builder(this);
		Issue selected = (Issue)issues_list_view.getAdapter().getItem(position);
	    commentDialogBuilder.setTitle("Comments of " + selected.getTitle());
	    commentDialog = commentDialogBuilder.create();
	    String lastCommentsURL = selected.getCommentUrl();
	    if(commentsCache.containsKey(lastCommentsURL)) {
	    	data = commentsCache.get(lastCommentsURL);
	    	onTaskCompleted(Comment.class.getSimpleName());
	    } else {
	    	data = new InfoItemList(this, lastCommentsURL, Comment.class);
	    	commentsCache.put(lastCommentsURL, data);
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
		else if(loadedItems == totalItemCount){
			new LoadList((InfoListAdapter)view.getAdapter()).execute(loadedItems);
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}
}

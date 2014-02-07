package com.example.github_issue_tracker;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IssueView extends LinearLayout {
	private TextView title, body;
	public IssueView(Context context) {
		super(context);
		title = new TextView(context);
		body = new TextView(context);
		this.addView(title);
		this.addView(body);
	}
	
	public void setContent(Issue issue) {
		title.setText(issue.getTitle());
		body.setText(issue.getAbstract());
	}
}

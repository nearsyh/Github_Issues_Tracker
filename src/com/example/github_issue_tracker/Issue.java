package com.example.github_issue_tracker;

public class Issue {
	private String title, body, comment_url;
	public Issue(String t, String b, String u) {
		title = t;
		body = b;
		comment_url = u;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getAbstract() {
		return body.substring(0, Math.min(body.length(), 140));
	}
	
	public String getCommentUrl() {
		return comment_url;
	}
}

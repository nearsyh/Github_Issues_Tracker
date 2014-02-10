package com.example.github_issue_tracker;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment extends InfoItem {
	private String author = "exception", body = "exception";
	public void init(JSONObject jIssue) {
		try {
			author = ((JSONObject)jIssue.get("user")).getString("login");
			body = jIssue.getString("body");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getBody() {
		return body;
	}
		
	public String getAbstract() {
		String abs = body.trim().substring(0, Math.min(body.length(), 140));
		if(abs.length() == 140) abs += "...";
		return abs;
	}

	@Override
	public String getMetaInfo() {
		return author;
	}

	@Override
	public String getBodyInfo() {
		return body;
	}

	@Override
	public long getPriority() {
		return 0;
	}
}

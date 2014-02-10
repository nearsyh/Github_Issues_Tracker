package com.example.github_issue_tracker;

import org.json.JSONException;
import org.json.JSONObject;

public class Issue extends InfoItem {
	 private String title = "exception", body = "exception", comments_url = "exception";
	 private long updateTime;
	 public void init(JSONObject jIssue) {
		try {
			title = jIssue.getString("title");
			body = jIssue.getString("body");
			comments_url = jIssue.getString("comments_url");
			updateTime = Long.parseLong(jIssue.getString("updated_at").replaceAll("[^0-9]", ""));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getBody() {
		return body;
	}
		
	public String getAbstract() {
		String abs = body.trim().substring(0, Math.min(body.length(), 140));
		if(abs.length() == 140) abs += "...";
		return abs;
	}
		
	public String getCommentUrl() {
		return comments_url;
	}

	@Override
	public String getMetaInfo() {
		return title;
	}

	@Override
	public String getBodyInfo() {
		return getAbstract();
	}

	@Override
	public long getPriority() {
		return updateTime;
	}
}

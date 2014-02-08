package com.example.github_issue_tracker;

import org.json.JSONObject;

public abstract class InfoItem implements Comparable<InfoItem> {
	public abstract void init(JSONObject jIssue);
	public abstract String getMetaInfo();
	public abstract String getBodyInfo();
	public abstract long getPriority();
	@Override
	public int compareTo(InfoItem another) {
		if(getPriority() == another.getPriority()) return 0;
		return getPriority() - another.getPriority() > 0 ? -1 : 1;
	}
}

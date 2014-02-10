package com.example.github_issue_tracker;

import org.json.JSONObject;

public abstract class InfoItem implements Comparable<InfoItem> {
	public abstract void init(JSONObject jIssue);
	public abstract String getMetaInfo(); // for the title
	public abstract String getBodyInfo(); // for the body
	public abstract long getPriority();   // for sort
	@Override
	public int compareTo(InfoItem another) {
		if(getPriority() == another.getPriority()) return 0;
		return getPriority() - another.getPriority() > 0 ? -1 : 1;
	}
}

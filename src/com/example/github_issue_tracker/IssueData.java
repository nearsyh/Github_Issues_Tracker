package com.example.github_issue_tracker;

import java.util.ArrayList;

public class IssueData {
	private String user, repo;
	private ArrayList<Issue> issue_list;
	
	public IssueData(String username, String reponame) {
		user = username;
		repo = reponame;
		issue_list = new ArrayList<Issue>();
	}
	
	public ArrayList<Issue> getIssuesList() {
		return issue_list;
	}
	
	public void update() {
		WebConnection.update(user, repo, issue_list);
	}
}

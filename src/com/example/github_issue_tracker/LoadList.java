package com.example.github_issue_tracker;

import android.os.AsyncTask;

public class LoadList extends AsyncTask<Integer, Void, Void> {
	private InfoListAdapter adapter;
	public LoadList(InfoListAdapter a) {
		adapter = a;
	}
	@Override
	protected Void doInBackground(Integer... last) {
		adapter.load(last[0].intValue(), 5);
		return null;
	}

	protected void onPostExecute(Void result) {
		adapter.notifyDataSetChanged();
	}
}

package com.example.github_issue_tracker;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InfoListAdapter extends BaseAdapter {
	private Activity parent;
	private InfoItemList issueData;
	public InfoListAdapter(Activity p, InfoItemList data) {
		parent = p;
		issueData = data;
	}
	@Override
	public int getCount() {
		return issueData.getIssuesList().size();
	}

	@Override
	public Object getItem(int position) {
		return issueData.getIssuesList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		View view;
        if (convertView == null) {
            view = parent.getLayoutInflater().inflate(R.layout.issue_view, null);
        } else {
            view = convertView;
        }
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(((InfoItem)getItem(position)).getMetaInfo());
        TextView body = (TextView) view.findViewById(R.id.body);
        body.setText(((InfoItem)getItem(position)).getBodyInfo());
        return view;
	}

}

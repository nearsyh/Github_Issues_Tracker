package com.example.github_issue_tracker;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InfoListAdapter extends BaseAdapter {
	private Activity parent;
	private InfoItemList data, tempData;
	public InfoListAdapter(Activity p, InfoItemList d) {
		parent = p;
		data = d;
		tempData = new InfoItemList(data, 5);
	}
	@Override
	public int getCount() {
		return tempData.getSize();
	}

	@Override
	public Object getItem(int position) {
		return tempData.getIssuesList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void load(int p, int size) {
		tempData.add(data, p, size);
	}
	
	public boolean isFinished() {
		return tempData.getSize() == data.getSize();
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

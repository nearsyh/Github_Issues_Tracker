package com.example.github_issue_tracker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoItemList implements OnTaskCompleted{
	ArrayList<InfoItem> info_item_list;
	protected OnTaskCompleted listener;
	protected URL url = null;
	private Class<?> c;
	public ArrayList<InfoItem> getIssuesList() {
		return info_item_list;
	}
	
	public InfoItemList(OnTaskCompleted l, String u, Class<?> name) {
		info_item_list = new ArrayList<InfoItem>();
		listener = l;
		c = name;
		try {
			url = new URL(u);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		new ApiCall(this, false).execute(url);
	}
	
	public void sort() {
		Collections.sort(info_item_list);
	}
	
	public void setUpContent(String result) {
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
		} catch (JSONException e1) {
			e1.printStackTrace();
			return;
		}
		for(int i = 0; i < jArray.length(); i ++) {
			InfoItem temp = null;
			try {
				temp = (InfoItem)c.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			try {
				temp.init((JSONObject)(jArray.get(i)));
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}
			if(temp != null) info_item_list.add(temp);
		}
	}
	
	@Override
	public void onTaskCompleted(String result) {
		setUpContent(result);
		sort();
		listener.onTaskCompleted(c.getSimpleName());
	}
}

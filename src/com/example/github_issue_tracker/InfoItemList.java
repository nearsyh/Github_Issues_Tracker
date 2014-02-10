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
	private boolean readingError = false;
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
	
	public InfoItemList(InfoItemList src, int size) {
		info_item_list = new ArrayList<InfoItem>();
		for(int i = 0; i < Math.min(src.info_item_list.size(), size); i ++) {
			info_item_list.add(src.info_item_list.get(i));
		}
	}
	
	public void add(InfoItemList src, int p, int s) {
		p = Math.min(src.info_item_list.size(), p);
		s = Math.min(src.info_item_list.size(), p + s);
		for(int i = p; i < s; i ++)
			info_item_list.add(src.info_item_list.get(i));
	}
	
	public int getSize() {
		return info_item_list.size();
	}
	
	private void sort() {
		if(info_item_list.size() > 1) Collections.sort(info_item_list);
	}
	
	private void setUpContent(String result) {
		if(result == ApiCall.CONNECTION_ERROR) {
			readingError = true;
			return;
		}
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
	
	public boolean isReadingError() {
		return readingError;
	}
	
	@Override
	public void onTaskCompleted(String result) {
		setUpContent(result);
		sort();
		listener.onTaskCompleted(c.getSimpleName());
	}
}

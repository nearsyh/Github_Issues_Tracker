package com.example.github_issue_tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.HttpURLConnection;

import android.os.AsyncTask;
import android.util.Log;

public class ApiCall extends AsyncTask<URL, Integer, String> {
	private OnTaskCompleted listener;
	private boolean onlyCheck = false;
	public static final String CONNECTION_ERROR = "ERROR", CONNECTION_SUCCEED = "SUCCEED";
	public ApiCall(OnTaskCompleted l, boolean opt) {
		listener = l;
		onlyCheck = opt;
	}
	
	@Override
	protected String doInBackground(URL... url) {
		if(url == null || url[0] == null) return CONNECTION_ERROR;
		try {
			if(((HttpURLConnection)url[0].openConnection()).getResponseCode() != 200) return CONNECTION_ERROR;
		} catch (IOException e1) {
			e1.printStackTrace();
			return CONNECTION_ERROR;
		}
		if(onlyCheck) return CONNECTION_SUCCEED;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url[0].openStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(reader == null) {
			Log.e("webconnection", "open stream failed");
			return null;
		}
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
			while ((line = reader.readLine()) != null)
			{
			    sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return sb.toString();
	}
	
	protected void onPostExecute(String result) {
        listener.onTaskCompleted(result);  
    } 
}

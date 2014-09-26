package com.apjq.todolist.job;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.apjq.todolist.ToDoApplication;
import com.apjq.todolist.dao.TodoDAO;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import de.greenrobot.event.EventBus;

public class SyncDataJob extends Job {
	String strtodo;
	String response;
	public SyncDataJob(String strtodo) {
		super(new Params(Priority.HIGH).requireNetwork().persist());
		// TODO Auto-generated constructor stub
		// dbHandler = new DatabaseHandler(myapp.getInstance()

		this.strtodo = strtodo;
		Log.d("SyncDataJob", "SyncDataJob");
	}

	@Override
	public void onAdded() {
		// TODO Auto-generated method stub
		TodoDAO dao = new TodoDAO(ToDoApplication.getInstance()
				.getApplicationContext());
		
		dao.createTodo(strtodo,"Syncing");
		dao.close();
		EventBus.getDefault().post(new SyncDataPojoSaved(strtodo));
		Log.d("onAdded", "onAdded");
	}

	@Override
	public void onRun() throws Throwable {
		String fullUrl = "https://docs.google.com/forms/d/1CMFCOCpcRcY94kQpOcko7GGvudoQ36GfydRhtTshTY/formResponse";
		HttpRequest mReq = new HttpRequest();
		String col1 = strtodo;
		String col2 = "cloud";
		
		String data = "entry.1604892543=" + URLEncoder.encode(col1) + "&" + 
					  "entry.2039911714=" + URLEncoder.encode(col2);
		 response = mReq.sendPost(fullUrl, data);
		Log.i("HttpResponseSC", response);
		if(!response.isEmpty()&&response.equalsIgnoreCase("200")){
			
			 EventBus.getDefault().post(new SyncDataPojoSynced(col1));
		}else{
			throw new Exception(response);
		}
		
	
	}

	@Override
	protected void onCancel() {
		// TODO Auto-generated method stub
		Log.d("onCancel", "onCancel");
	}

	@Override
	protected boolean shouldReRunOnThrowable(Throwable throwable) {
		// TODO Auto-generated method stub

		Log.d("Throwable", "Throwable");
		
		if(throwable instanceof Exception) {
			//if it is a 4xx error, stop
			Exception todoException = (Exception) throwable;
			
			
			//return Integer.parseInt(todoException.getMessage()) < 400 || Integer.parseInt(todoException.getMessage()) > 499;
			
			return true;
		}
		
		
		return true;
		//return true;
	}
	
	

}

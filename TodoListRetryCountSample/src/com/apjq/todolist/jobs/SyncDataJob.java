package com.apjq.todolist.jobs;

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

import com.apjq.todolist.dao.TodoDAO;
import com.apjq.todolist.events.RertyCountPojo;
import com.apjq.todolist.events.SyncDataPojoSaved;
import com.apjq.todolist.events.SyncDataPojoSynFailed;
import com.apjq.todolist.events.SyncDataPojoSynced;
import com.apjq.todolistretrycounts.ToDoApplication;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import de.greenrobot.event.EventBus;

public class SyncDataJob extends Job {
	String strtodo;
	String response;
	private long localId;
	
	
	

	String LOG_TAG = "SyncDataJob";

	public SyncDataJob(String strtodo) {
		super(new Params(Priority.HIGH).requireNetwork().persist());
		// TODO Auto-generated constructor stub
		// dbHandler = new DatabaseHandler(myapp.getInstance()
		localId = System.currentTimeMillis();

		this.strtodo = strtodo;
		Log.d(LOG_TAG, "SyncDataJob");
	}

	@Override
	public void onAdded() {
		// TODO Auto-generated method stub
		TodoDAO dao = new TodoDAO(ToDoApplication.getInstance().getApplicationContext());
		
		 dao.createTodo(localId,strtodo, "Syncing");
		
		 dao.close();

		// System.out.println("Key"+key);
		EventBus.getDefault().post(new SyncDataPojoSaved(strtodo));
		Log.d(LOG_TAG, "onAdded");
	}

	@Override
	public void onRun() throws Throwable {
		Log.d(LOG_TAG, "onRun");
		
		String fullUrl = "https://docs.google.com/forms/d/1CMFCOCpcRcY94kQpOcko7GGvudoQ36GfydRhtTshTY/formResponse";
		HttpRequest mReq = new HttpRequest();
		String col1 = strtodo;
		String col2 = "cloud";

		String data = "entry.1604892543=" + URLEncoder.encode(col1) + "&"
				+ "entry.2039911714=" + URLEncoder.encode(col2);
		response = mReq.sendPost(fullUrl, data);
		Log.i("HttpResponseSC", response);
		if (!response.isEmpty() && response.equalsIgnoreCase("200")) {

			EventBus.getDefault().post(new SyncDataPojoSynced(col1));
		} else {

			Log.d(LOG_TAG, "Exception");
			throw new Exception(response);
		}

	}

	@Override
	protected boolean shouldReRunOnThrowable(Throwable throwable) {
		// TODO Auto-generated method stub

		Log.d(LOG_TAG, "shouldReRunOnThrowable");
int count = 1;

		if (throwable instanceof Exception) {

			Exception todoException = (Exception) throwable;

			Log.d(LOG_TAG, "throwable instanceof Exception");
			// if it is a 4xx error, stop
			// System.out.println(Integer.parseInt(todoException.getMessage()) <
			// 400 || Integer.parseInt(todoException.getMessage()) > 499);
			// return Integer.parseInt(todoException.getMessage()) < 400 ||
			// Integer.parseInt(todoException.getMessage()) > 499;

			// Hard coded to true for Testing Retry Logic, if it's true Jobs run
			// method will be restarted.
		//	count=count++;
			EventBus.getDefault().post(new RertyCountPojo( count));
			
			return true;
			
			
		}

		// Hard coded to true for Testing Retry Logic
		return true;
		// return true;
	}

	@Override
	protected void onCancel() {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "onCancel");
		int count = 1;
		 TodoDAO daoupdate = new TodoDAO(ToDoApplication.getInstance()
		 .getApplicationContext());
		
		 daoupdate.updateTodo(localId,"Syncing Failed");
		 daoupdate.close();
			
			EventBus.getDefault().post(new RertyCountPojo(count));

		EventBus.getDefault().post(new SyncDataPojoSynFailed("Syncing Failed"));
	}

}

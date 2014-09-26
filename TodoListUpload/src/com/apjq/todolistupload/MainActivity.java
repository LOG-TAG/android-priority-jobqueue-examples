package com.apjq.todolistupload;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.apjq.todolist.dao.TodoDAO;
import com.apjq.todolist.data.Todo;
import com.apjq.todolist.events.RertyCountPojo;
import com.apjq.todolist.events.SyncDataPojoSaved;
import com.apjq.todolist.events.SyncDataPojoSynFailed;
import com.apjq.todolist.events.SyncDataPojoSynced;
import com.apjq.todolist.jobs.SyncDataJob;
import com.apjq.todolistupload.R;
import com.apjq.todolistupload.EditNameDialog.EditNameDialogListener;
import com.path.android.jobqueue.JobManager;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity implements EditNameDialogListener {
	JobManager jobManager;

	ListView lv;

	ListAdapter lad;
	List<Todo> todoList;
	List<Todo> reloadList;
	int count;
	TextView tv;
	// DAO
	private TodoDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.listView1);
		//tv = (TextView) findViewById(R.id.retrycount);
		//tv.setText("Retry Counts");
		dao = new TodoDAO(this);
		jobManager = ToDoApplication.getInstance().getJobManager();
		// Set the list adapter and get TODOs list via DAO
		todoList = dao.getTodos();
		Collections.reverse(todoList);
		// System.out.println(todoList);
		lad = new ListAdapter(this, R.layout.listview_item, todoList);

		lv.setAdapter(lad);
		EventBus.getDefault().register(this);
//delete Logic
//		lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//				Todo todo = (Todo) parent.getItemAtPosition(position);
//
//				dao.deleteTodo(todo.getId());
//				reload();
//				Toast.makeText(getApplicationContext(), "Deleted!",
//						Toast.LENGTH_LONG).show();
//
//			}
//
//		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				Todo todo = (Todo) parent.getItemAtPosition(position);

				dao.deleteTodo(todo.getId());
				reload();
				Toast.makeText(getApplicationContext(), "Deleted!",
						Toast.LENGTH_LONG).show();
				return true;
			}

		});
		
		
		
	}

	void reload() {

		todoList.clear();
		reloadList = dao.getTodos();
		Collections.reverse(reloadList);
		todoList.addAll(reloadList);
		lad.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.add:
			showEditDialog();

			return true;
		}

		return super.onOptionsItemSelected(item);

	}

	private void showEditDialog() {
		FragmentManager fm = getFragmentManager();
		EditNameDialog editNameDialog = new EditNameDialog();
		editNameDialog.show(fm, "fragment_edit_name");
		// EventBus.getDefault().postSticky(new LoggedInEvent(groupList));
	}

	
	//interface callback from DialogFragment (We can use EventBus) 
	@Override
	public void onFinishEditDialog(String inputText) {

		if (!inputText.isEmpty()) {
			jobManager.addJobInBackground(new SyncDataJob(inputText));
		}

	}
	
	
	//========================EventBus Callbacks

	public void onEventMainThread(SyncDataPojoSaved ignored) {
		reload();
		Toast.makeText(getApplicationContext(), "New TODO added!",
				Toast.LENGTH_LONG).show();
	}

	public void onEventMainThread(SyncDataPojoSynced ignored) {

		reload();
		Toast.makeText(getApplicationContext(), "New TODO Synced!",
				Toast.LENGTH_LONG).show();
	}

	public void onEventMainThread(SyncDataPojoSynFailed ignored) {
		reload();
		Toast.makeText(getApplicationContext(), "New TODO Sync Failed!",
				Toast.LENGTH_LONG).show();
	}

	public void onEventMainThread(RertyCountPojo ignored) {
		count = count + ignored.getCount();
		// tv.setText("Retry Counts: "+" "+String.valueOf(count));

		Toast.makeText(getApplicationContext(),
				"Retry==>" + Integer.toString(count), Toast.LENGTH_SHORT)
				.show();
	}

	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			EventBus.getDefault().unregister(this);
			dao.close();
		} catch (Throwable t) {
			// this may crash if registration did not go through. just be safe
		}
	}

}

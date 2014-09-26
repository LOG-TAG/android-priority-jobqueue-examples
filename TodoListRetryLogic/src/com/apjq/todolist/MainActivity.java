package com.apjq.todolist;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.apjq.todolist.EditNameDialog.EditNameDialogListener;
import com.apjq.todolist.dao.TodoDAO;
import com.apjq.todolist.data.Todo;
import com.apjq.todolist.job.SyncDataJob;
import com.apjq.todolist.job.SyncDataPojoSynced;
import com.apjq.todolist.job.SyncDataPojoSaved;
import com.path.android.jobqueue.JobManager;

import de.greenrobot.event.EventBus;

/**
 * Main activity which displays a list of TODOs.
 * 
 * @author itcuties
 * 
 */
public class MainActivity extends ListActivity implements
		EditNameDialogListener {
	JobManager jobManager;
	// DAO
	private TodoDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create DAO object
		dao = new TodoDAO(this);
		jobManager = ToDoApplication.getInstance().getJobManager();
		// Set the list adapter and get TODOs list via DAO
		setListAdapter(new ListAdapter(this, dao.getTodos()));
		EventBus.getDefault().register(this);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO item that was clicked
		Todo todo = (Todo) getListAdapter().getItem(position);

		// Delete TODO object from the database
		dao.deleteTodo(todo.getId());

		// Set the list adapter and get TODOs list via DAO
		setListAdapter(new ListAdapter(this, dao.getTodos()));

		// Display success information
		Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG)
				.show();

	}

	/* ************************************************************* *
	 * Menu service methods
	 * *************************************************************
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Since we have only ONE option this code is not complicated :)

		/*
		 * // Create an intent Intent intent = new Intent(this,
		 * AddTodoActivity.class); // Start activity startActivity(intent); //
		 * Finish this activity this.finish();
		 * 
		 * // Close the database dao.close();
		 */

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

	//
	// @Override
	// public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	@Override
	public void onFinishEditDialog(String inputText) {
		// TODO Auto-generated method stub
		// dao.createTodo(inputText);
		// setListAdapter(new ListAdapter(this, dao.getTodos()));
		if(!inputText.isEmpty()){
		 jobManager.addJobInBackground(new SyncDataJob(inputText));
		}
	
	}

	@SuppressWarnings("UnusedDeclaration")
	public void onEventMainThread(SyncDataPojoSaved ignored) {
		setListAdapter(new ListAdapter(this, dao.getTodos()));
		Toast.makeText(getApplicationContext(), "New TODO added!",
				Toast.LENGTH_LONG).show();
	}
	
	@SuppressWarnings("UnusedDeclaration")
	public void onEventMainThread(SyncDataPojoSynced ignored) {
		Toast.makeText(getApplicationContext(), "New TODO Synced!",
				Toast.LENGTH_LONG).show();
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

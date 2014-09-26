package com.apjq.todolist.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apjq.todolist.data.Todo;
import com.apjq.todolist.sql.TodoSQLiteHelper;


public class TodoDAO {

	private SQLiteDatabase db;
	private TodoSQLiteHelper dbHelper;
	
	public TodoDAO(Context context) {
		dbHelper = new TodoSQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	// Close the db
	public void close() {
		db.close();
	}
	
	
	
	public void createTodo(long localId,String todoText,String Syncing) {
		ContentValues contentValues = new ContentValues();
		
		contentValues.put("localid", localId);
		contentValues.put("todo", todoText);
		contentValues.put("syncstate", Syncing);
	    // Insert into DB
		//long id=
		db.insert("todos", null, contentValues);
		//return id;
	}
	
	/**
	 * Delete TODO object
	 * @param todoId
	 */
	public void deleteTodo(int todoId) {
		// Delete from DB where id match
		db.delete("todos", "_id = " + todoId, null);
	}
	
	/**
	 * Get all TODOs.
	 * @return
	 */
	public List<Todo> getTodos() {
		List<Todo> todoList = new ArrayList<Todo>();
		
		// Name of the columns we want to select
		String[] tableColumns = new String[] {"_id","localid","todo","syncstate"};
		
		// Query the database
		Cursor cursor = db.query("todos", tableColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		// Iterate the results
	    while (!cursor.isAfterLast()) {
	    	Todo todo = new Todo();
	    	// Take values from the DB
	    	todo.setId(cursor.getInt(0));
	    	todo.setText(cursor.getString(2));
	    	todo.setSyncState(cursor.getString(3));
	    	
	    	// Add to the DB
	    	todoList.add(todo);
	    	
	    	// Move to the next result
	    	cursor.moveToNext();
	    }
		
		return todoList;
	}

	public void updateTodo(long key,String synState) {
		ContentValues contentValues = new ContentValues();
		
		contentValues.put("syncstate", synState);
	    
		db.update("todos", contentValues, "localid=?",
				new String[] { String.valueOf(key) });
	}
	
	
}

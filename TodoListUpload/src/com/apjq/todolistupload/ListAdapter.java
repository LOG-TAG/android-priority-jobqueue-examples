package com.apjq.todolistupload;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apjq.todolist.data.Todo;
import com.apjq.todolistupload.R;



public class ListAdapter extends ArrayAdapter<Todo> {

	// List context
    private final Context context;
    // List values
    private final List<Todo> todoList;
	
	public ListAdapter(Context context,int textViewResourceId, List<Todo> todoList) {
		super(context, R.layout.activity_main, todoList);
		this.context = context;
		this.todoList = todoList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
        View rowView = inflater.inflate(R.layout.listview_item, parent, false);
         
        TextView todoText = (TextView) rowView.findViewById(R.id.todoText);
        TextView sycState = (TextView) rowView.findViewById(R.id.sycstate);
        
        
        todoText.setText(todoList.get(position).getText());
        sycState.setText(todoList.get(position).getSyncState());
         
        return rowView;
	}
	
}

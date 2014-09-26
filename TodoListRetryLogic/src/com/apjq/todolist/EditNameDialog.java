package com.apjq.todolist;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class EditNameDialog extends DialogFragment implements
		OnEditorActionListener {
	// List<String> gglist=new ArrayList<String>();
	
	Button cancel,add;
	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputText);
	}
	
	
	 @Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		    super.onViewCreated(view, savedInstanceState);
		    
		    
		    cancel.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	                getDialog().dismiss();
	            }
	        });
		    
		    add.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	EditNameDialogListener activity = (EditNameDialogListener) getActivity();
	            	if (!mEditText.getText().toString().isEmpty()) {
	    				
	    				activity.onFinishEditDialog(mEditText.getText().toString());
//	    				// Toast.makeText(getActivity(), "mEditText",
//	    				// Toast.LENGTH_SHORT).show();
	    				getDialog().dismiss();
	    				//return false;
	    			} else {
	    				mEditText.setError("This Field Should Not be kept Empty!");
	    				mEditText.requestFocus();
	    			//	return true;
	    			}              
	                          
	            }
	        });

		
		   
		}
		 @Override
		 public void onStop() {
		 super.onStop();
		// EventBus.getDefault().removeStickyEvent(RelationAddEvent.class);
		 }

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// EventBus.getDefault().registerSticky(getActivity(),
		// LoggedInEvent.class);
	}

	private EditText mEditText;

	public EditNameDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_name, container);

		// EventBus.getDefault().registerSticky(this, LoggedInEvent.class);
		mEditText = (EditText) view.findViewById(R.id.txt_your_name);
		cancel=(Button) view.findViewById(R.id.cancel);
        add=(Button) view.findViewById(R.id.add);


		getDialog().setTitle("Enter the Text:");

		// Show soft keyboard automatically
		mEditText.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		mEditText.setOnEditorActionListener(this);

		return view;
	}

	// public void onEvent(LoggedInEvent testEvent){
	// // Toast.makeText(getActivity(), testEvent.getSomelist().get(1),
	// Toast.LENGTH_SHORT).show();
	//
	// gglist= testEvent.getSomelist();
	// }

//	@Override
//	public void onStop() {
//		super.onStop();
//		// EventBus.getDefault().removeStickyEvent(LoggedInEvent.class);
//	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		//Boolean flag1 = false;

		if (EditorInfo.IME_ACTION_DONE == actionId) {
			// Return input text to activity
			EditNameDialogListener activity = (EditNameDialogListener) getActivity();
			// Boolean flag1=false;

//			if (!mEditText.getText().toString().isEmpty()) {
//				for (String gname : gglist) {
//					if (gname.equalsIgnoreCase(mEditText.getText().toString())) {
//						flag1 = true;
//
//						// Toast.makeText(getActivity(), "mEditText",
//						// Toast.LENGTH_SHORT).show();
//					} else {
//						// Toast.makeText(getActivity(),
//						// "Entered Name Already Exists",
//						// Toast.LENGTH_SHORT).show();
//					}
//
//				}
//
//				if (!flag1) {
//
//					activity.onFinishEditDialog(mEditText.getText().toString());
//					// Toast.makeText(getActivity(), "mEditText",
//					// Toast.LENGTH_SHORT).show();
//					this.dismiss();
//					return false;
//				} else {
//					Toast.makeText(getActivity(),
//							"Entered Name Already Exists", Toast.LENGTH_SHORT)
//							.show();
//					// getDialog().getWindow().setSoftInputMode(
//					// LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//					return true;
//				}
//			}
//			{
//				mEditText.setError("This Field Should Not be kept Empty!");
//				mEditText.requestFocus();
//				return true;
//				// Toast.makeText(getActivity(),
//				// "Entered Name,This Field Should Not be kept Empty!",
//				// Toast.LENGTH_SHORT).show();
//			}

			if (!mEditText.getText().toString().isEmpty()) {
				
				activity.onFinishEditDialog(mEditText.getText().toString());
//				// Toast.makeText(getActivity(), "mEditText",
//				// Toast.LENGTH_SHORT).show();
			this.dismiss();
				return false;
			} else {
				mEditText.setError("This Field Should Not be kept Empty!");
				mEditText.requestFocus();
				return true;
			}

		}
		// flag1 = false;

		return false;
	}
}
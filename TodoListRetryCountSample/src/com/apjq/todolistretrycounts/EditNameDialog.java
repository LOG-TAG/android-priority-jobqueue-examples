package com.apjq.todolistretrycounts;

import java.util.ArrayList;
import java.util.List;

import com.apjq.todolistretrycounts.R;
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

	Button cancel, add;

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

					getDialog().dismiss();

				} else {
					mEditText.setError("This Field Should Not be kept Empty!");
					mEditText.requestFocus();

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
		cancel = (Button) view.findViewById(R.id.cancel);
		add = (Button) view.findViewById(R.id.add);

		getDialog().setTitle("Enter the Text:");

		// Show soft keyboard automatically
		mEditText.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		mEditText.setOnEditorActionListener(this);

		return view;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if (EditorInfo.IME_ACTION_DONE == actionId) {

			EditNameDialogListener activity = (EditNameDialogListener) getActivity();

			if (!mEditText.getText().toString().isEmpty()) {

				activity.onFinishEditDialog(mEditText.getText().toString());

				this.dismiss();
				return false;
			} else {
				mEditText.setError("This Field Should Not be kept Empty!");
				mEditText.requestFocus();
				return true;
			}

		}

		return false;
	}
}
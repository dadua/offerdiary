package com.socialbaba.games.pairmerge.ui.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public abstract class MecCommonActivity extends Activity {


	public static final String IS_ACTIVITY_FINISH_REQUIRED_KEY = "isActivityFinishRequiredKey";
	public static final String ALERT_MESSAGE_KEY = "createExceptionMessageKey";
	private static final String TAG = LoggerFactory.getTagForLogging();
	public static final int MESSAGE_PRINTING_DIALOG = 99;//random no. not equal to the usual ones 0, 1 


	public void showDialogWithMessage(String message, boolean isFinishRequired) {
		Bundle bundle = new Bundle();
		bundle.putString(MecCommonActivity.ALERT_MESSAGE_KEY, message);
		bundle.putBoolean(MecCommonActivity.IS_ACTIVITY_FINISH_REQUIRED_KEY, isFinishRequired);
		showDialog(MecCommonActivity.MESSAGE_PRINTING_DIALOG, bundle);
	}

	protected abstract void onActivityCreate ();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		 */
		try {
			onActivityCreate();
		} catch (Exception e) {
			showDialogWithMessage(e.getMessage(), true);
		}

		/*
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mec_status_title);
		setMecStatusOnTitle(false);
		 */
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		super.onPrepareDialog(id, dialog, args);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		switch (id) {
		case MESSAGE_PRINTING_DIALOG:
			String message = bundle.getString(ALERT_MESSAGE_KEY);
			boolean isActivityFinishRequired = bundle.getBoolean(IS_ACTIVITY_FINISH_REQUIRED_KEY, false);
			return getAlertDialogWithMessage(message, isActivityFinishRequired);

		default:
			break;
		}
		return super.onCreateDialog(id, bundle);
	}


	private AlertDialog getAlertDialogWithMessage(final String string, final boolean isActivityFinishRequired) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(string).
		setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				if (isActivityFinishRequired) {
					finish();
				}
			}
		});
		AlertDialog alertDialog = builder.create();
		return alertDialog;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}

package com.socialbaba.games.pairmerge.ui;

import java.util.Date;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.socialbaba.games.pairmerge.ui.common.OptionItemAdapter;

public class GridTileAdapter extends OptionItemAdapter {
	
	public GridTileAdapter(List<String> optionsToDisplay) {
		super(optionsToDisplay);
	}

	OnClickListener defaultOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
	private OnTouchListener onTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Date date = new Date();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Toast.makeText(v.getContext(), date.toString() + ": This is down swipe", 500).show();
				break;
			case MotionEvent.ACTION_UP:
				Toast.makeText(v.getContext(), date.toString() + ": This is up swipe", 500).show();
				break;

			default:
				break;
			}
			return false;
		}
	};
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		view.setOnClickListener(defaultOnClickListener);
		//view.setOnTouchListener(onTouchListener);
		return view;
	}

}

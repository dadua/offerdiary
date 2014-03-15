package com.socialbaba.games.pairmerge.ui.common;

import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.socialbaba.games.R;


public class OptionItemAdapter extends BaseAdapter {


	private View view;
	private final List<String> optionsToDisplay;

	public OptionItemAdapter(List<String> optionsToDisplay) {
		this.optionsToDisplay = optionsToDisplay;
	}

	@Override
	public int getCount() {
		return optionsToDisplay.size();
	}

	@Override
	public Object getItem(int arg0) {
		return optionsToDisplay.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.options_item_layout, parent, false);

		}
		TextView optionToDisplayView = (TextView) view.findViewById(R.id.optionsToDisplay);
		if (optionToDisplayView !=null) {
			optionToDisplayView.setText(optionsToDisplay.get(position));
			optionToDisplayView.setId(optionToDisplayView.getId()+position);
			optionToDisplayView.setTextColor(Color.WHITE);
		}
		return view;
	}

}

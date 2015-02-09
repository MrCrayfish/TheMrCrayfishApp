package com.mrcrayfish.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<MenuItem>
{
	public MenuAdapter(Context context, MenuItem[] items)
	{
		super(context, R.layout.menu_item, items);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.menu_item, parent, false);

		MenuItem item = getItem(position);
		ImageView background = (ImageView) row.findViewById(R.id.menuBackground);
		background.setImageResource(item.getBackground());
		
		TextView text = (TextView) row.findViewById(R.id.menuText);
		Typeface type = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		text.setTypeface(type);
		text.setText(item.getTitle());
		return row;
	}
}

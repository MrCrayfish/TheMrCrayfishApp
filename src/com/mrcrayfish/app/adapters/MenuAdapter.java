package com.mrcrayfish.app.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.objects.MenuItem;

public class MenuAdapter extends ArrayAdapter<MenuItem>
{
	public MenuAdapter(Context context, ArrayList<MenuItem> items)
	{
		super(context, R.layout.main_menu_item, items);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		final View row = layout.inflate(R.layout.main_menu_item, parent, false);
		final MenuItem item = getItem(position);
		
		ImageView overlay = (ImageView) row.findViewById(R.id.overlay);
		overlay.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				getContext().startActivity(item.getIntent());
			}
		});

		Typeface type = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");

		TextView text = (TextView) row.findViewById(R.id.menuText);
		text.setTypeface(type);
		text.setText(item.getTitle());

		TextView desc = (TextView) row.findViewById(R.id.menuDescription);
		desc.setTypeface(type);
		desc.setText(item.getDescription());

		ImageView icon = (ImageView) row.findViewById(R.id.menuIcon);
		icon.setImageResource(item.getIcon());
		return row;
	}
}

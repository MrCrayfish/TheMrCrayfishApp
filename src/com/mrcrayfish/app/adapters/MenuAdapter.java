package com.mrcrayfish.app.adapters;

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
	public MenuAdapter(Context context, MenuItem[] items)
	{
		super(context, R.layout.menu_item, items);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		final View row = layout.inflate(R.layout.menu_item, parent, false);
		final MenuItem item = getItem(position);
		
		ImageView overlay = (ImageView) row.findViewById(R.id.menuBackgroundOverlay);
		overlay.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				row.getContext().startActivity(item.getIntent());
			}	
		});
		
		ImageView background = (ImageView) row.findViewById(R.id.menuBackground);
		background.setImageResource(item.getBackground());
		
		TextView text = (TextView) row.findViewById(R.id.menuText);
		Typeface type = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		text.setTypeface(type);
		text.setText(item.getTitle());
		return row;
	}
}

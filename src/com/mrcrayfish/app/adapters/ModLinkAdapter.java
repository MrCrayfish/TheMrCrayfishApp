package com.mrcrayfish.app.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.mod.ModLink;

public class ModLinkAdapter extends ArrayAdapter<ModLink>
{
	public ModLinkAdapter(Context context, ArrayList<ModLink> links)
	{
		super(context, R.layout.mod_more_info_item, links);
	}

	@Override
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.mod_more_info_item, parent, false);
		final ModLink link = getItem(position);

		row.findViewById(R.id.buttonContainer).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link.getUrl())));
			}
		});

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		TextView text = (TextView) row.findViewById(R.id.linkText);
		text.setText(link.getText());
		text.setTypeface(bebas_neue);

		return row;
	}

}

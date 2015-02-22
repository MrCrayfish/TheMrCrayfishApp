package com.mrcrayfish.app.adapters;

import java.util.List;

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
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.objects.SocialItem;
import com.mrcrayfish.app.tasks.TaskSetBackground;

public class SocialAdapter extends ArrayAdapter<SocialItem>
{
	public SocialAdapter(Context context, List<SocialItem> objects)
	{
		super(context, R.layout.social_item, objects);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.social_item, parent, false);

		final SocialItem item = getItem(position);
		ImageView background = (ImageView) row.findViewById(R.id.socialBackground);
		ImageView icon = (ImageView) row.findViewById(R.id.socialIcon);
		TextView name = (TextView) row.findViewById(R.id.socialName);
		ImageView overlay = (ImageView) row.findViewById(R.id.socialOverlay);

		new TaskSetBackground(icon).execute(item.getIcon());

		overlay.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
				getContext().startActivity(intent);
			}
		});

		background.setBackgroundResource(item.getBackground());

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		name.setTypeface(bebas_neue);
		name.setText(item.getName());
		name.setTextColor(row.getResources().getColor(item.getColour()));
		return row;
	}
}

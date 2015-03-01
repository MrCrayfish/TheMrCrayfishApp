package com.mrcrayfish.app.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.objects.SoundItem;

public class SoundAdapter extends ArrayAdapter<SoundItem>
{
	private MediaPlayer mp;

	public SoundAdapter(Context context, List<SoundItem> objects)
	{
		super(context, R.layout.soundboard_item, objects);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.soundboard_item, parent, false);

		final SoundItem item = getItem(position);
		RelativeLayout container = (RelativeLayout) row.findViewById(R.id.soundContainer);
		ImageView button = (ImageView) row.findViewById(R.id.soundButton);
		TextView desc = (TextView) row.findViewById(R.id.soundDescription);

		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				resetSound();
				mp = MediaPlayer.create(getContext(), item.getSoundId());
				mp.start();
				mp.setOnCompletionListener(new OnCompletionListener()
				{
					@Override
					public void onCompletion(MediaPlayer mp)
					{
						resetSound();
					}
				});
			}
		});

		button.setImageResource(getButtonImageId(position));

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		desc.setTypeface(bebas_neue);
		desc.setText(item.getDesc());

		desc.requestLayout();
		return row;
	}

	private void resetSound()
	{
		if (mp != null)
		{
			mp.reset();
			mp.release();
			mp = null;
		}
	}

	public int getButtonImageId(int position)
	{
		int id = (position + 1) % 5;
		switch (id)
		{
		case 0:
			return R.drawable.button_purple;
		case 2:
			return R.drawable.button_blue;
		case 3:
			return R.drawable.button_yellow;
		case 4:
			return R.drawable.button_green;
		default:
			return R.drawable.button_red;
		}
	}
}

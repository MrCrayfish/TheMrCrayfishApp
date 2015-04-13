package com.mrcrayfish.app.adapters;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskGetBitmap;
import com.mrcrayfish.app.tasks.TaskGetBitmap.Type;
import com.mrcrayfish.app.util.SavedVideos;
import com.mrcrayfish.app.util.YouTubeUtil;

public class VideoAdapter extends ArrayAdapter<VideoItem>
{
	private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(5);

	public VideoAdapter(Context context, ArrayList<VideoItem> videos)
	{
		super(context, R.layout.video_item, videos);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.video_item, parent, false);

		final VideoItem video = getItem(position);
		final ImageView save = (ImageView) row.findViewById(R.id.saveVideo);
		ImageView thumbnail = (ImageView) row.findViewById(R.id.videoThumbnail);
		TextView title = (TextView) row.findViewById(R.id.videoTitle);
		TextView views = (TextView) row.findViewById(R.id.videoViews);
		RatingBar bar = (RatingBar) row.findViewById(R.id.videoRating);
		TextView date = (TextView) row.findViewById(R.id.videoDate);

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
		title.setText(video.getTitle());

		thumbnail.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				YouTubeUtil.openVideo(getContext(), video.getVideoId());
			}
		});

		save.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SavedVideos.add(getContext(), video.getVideoId());
				save.animate().alpha(0).setListener(new AnimatorListenerAdapter()
				{
					public void onAnimationEnd(Animator animation)
					{
						save.setVisibility(View.GONE);
					}
				});
			}
		});

		if (cache.get(video.getVideoId()) != null)
		{
			thumbnail.setImageBitmap(cache.get(video.getVideoId()));
		}
		else
		{
			thumbnail.setAlpha(0.0F);
			new TaskGetBitmap(getContext(), thumbnail, cache, Type.YOUTUBE).execute(video.getVideoId());
		}

		if (SavedVideos.has(getContext(), video.getVideoId()))
		{
			save.setVisibility(View.GONE);
		}

		views.setText(video.getViews() + " Views");
		bar.setRating(video.getRating());
		date.setText(video.getDate());
		return row;
	}
}

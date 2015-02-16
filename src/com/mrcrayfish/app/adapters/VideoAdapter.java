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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskGetThumbnail;
import com.mrcrayfish.app.util.SavedVideos;
import com.mrcrayfish.app.util.ScreenUtil;
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
		final RelativeLayout container = (RelativeLayout) row.findViewById(R.id.videoInfoContainer);
		final ImageView infoBg = (ImageView) row.findViewById(R.id.infoBackground);
		final ImageView hide_info = (ImageView) row.findViewById(R.id.buttonHideInfo);
		final ImageView save = (ImageView) row.findViewById(R.id.saveVideo);
		ImageView thumbnail = (ImageView) row.findViewById(R.id.videoThumbnail);
		TextView title = (TextView) row.findViewById(R.id.videoTitle);
		TextView views = (TextView) row.findViewById(R.id.videoViews);
		RatingBar bar = (RatingBar) row.findViewById(R.id.videoRating);
		TextView date = (TextView) row.findViewById(R.id.videoDate);

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
		title.setText(video.getTitle());

		infoBg.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				YouTubeUtil.openVideo(getContext(), video.getVideoId());
			}
		});

		hide_info.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (container.getY() == ScreenUtil.toPixels(getContext(), 6))
				{
					container.animate().setDuration(500).y(container.getHeight());
					hide_info.animate().rotation(180);
				}
				else
				{
					container.animate().setDuration(500).y(ScreenUtil.toPixels(getContext(), 6));
					hide_info.animate().rotation(0);
				}
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
			new TaskGetThumbnail(getContext(), thumbnail, cache).execute(video.getVideoId());
		}

		if (SavedVideos.has(getContext(), video.getVideoId()))
		{
			save.setVisibility(View.GONE);
		}

		views.setText(video.getViews() + " Views");
		bar.setRating(video.getRating());
		date.setText(video.getDate());
		infoBg.requestLayout();
		return row;
	}
}

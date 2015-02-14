package com.mrcrayfish.app.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeIntents;
import com.mrcrayfish.app.R;
import com.mrcrayfish.app.interfaces.IVideoList;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskGetThumbnail;

public class SavedVideoAdapter extends ArrayAdapter<VideoItem>
{
	private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(5);
	private IVideoList videoManager;

	public SavedVideoAdapter(Context context, ArrayList<VideoItem> videos, IVideoList videoManager)
	{
		super(context, R.layout.saved_video_item, videos);
		this.videoManager = videoManager;
	}

	@Override
	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.saved_video_item, parent, false);

		final VideoItem tutorial = getItem(position);
		final RelativeLayout container = (RelativeLayout) row.findViewById(R.id.videoInfoContainer);
		final ImageView infoBg = (ImageView) row.findViewById(R.id.infoBackground);
		ImageView thumbnail = (ImageView) row.findViewById(R.id.videoThumbnail);
		TextView title = (TextView) row.findViewById(R.id.videoTitle);
		TextView views = (TextView) row.findViewById(R.id.videoViews);
		RatingBar bar = (RatingBar) row.findViewById(R.id.videoRating);
		ImageView delete = (ImageView) row.findViewById(R.id.deleteVideoCross);

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
		title.setText(tutorial.getTitle());

		infoBg.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = YouTubeIntents.createPlayVideoIntent(SavedVideoAdapter.this.getContext(), tutorial.getVideoId());

				if (YouTubeIntents.isYouTubeInstalled(SavedVideoAdapter.this.getContext()))
				{
					SavedVideoAdapter.this.getContext().startActivity(intent);
				}
				else
				{
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/watch?v=" + tutorial.getVideoId()));
					SavedVideoAdapter.this.getContext().startActivity(intent);
				}
			}
		});

		thumbnail.setOnLongClickListener(new OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				if (container.getAlpha() == 0)
				{
					container.animate().setDuration(500).alpha(1);
					infoBg.setEnabled(true);
				}
				else
				{
					container.animate().setDuration(500).alpha(0);
					infoBg.setEnabled(false);
				}
				return true;
			}
		});

		delete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				videoManager.removeVideo(position);
				videoManager.updateVideoList();
			}
		});

		thumbnail.setAlpha(0.0F);
		new TaskGetThumbnail(getContext(), thumbnail, cache).execute(tutorial.getVideoId());

		views.setText(tutorial.getViews() + " Views");
		bar.setRating(tutorial.getRating());
		infoBg.requestLayout();
		return row;
	}
}

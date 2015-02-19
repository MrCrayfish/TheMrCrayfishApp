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
import com.mrcrayfish.app.interfaces.IVideoList;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskGetBitmap;
import com.mrcrayfish.app.tasks.TaskGetBitmap.Type;
import com.mrcrayfish.app.util.ScreenUtil;
import com.mrcrayfish.app.util.YouTubeUtil;

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

		final VideoItem video = getItem(position);
		final RelativeLayout videoContainer = (RelativeLayout) row.findViewById(R.id.videoContainer);
		final RelativeLayout infoContainer = (RelativeLayout) row.findViewById(R.id.videoInfoContainer);
		final ImageView infoBg = (ImageView) row.findViewById(R.id.infoBackground);
		final ImageView hide_info = (ImageView) row.findViewById(R.id.buttonHideInfo);
		ImageView thumbnail = (ImageView) row.findViewById(R.id.videoThumbnail);
		TextView title = (TextView) row.findViewById(R.id.videoTitle);
		TextView views = (TextView) row.findViewById(R.id.videoViews);
		RatingBar bar = (RatingBar) row.findViewById(R.id.videoRating);
		ImageView delete = (ImageView) row.findViewById(R.id.deleteVideoCross);
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
				if (infoContainer.getY() == ScreenUtil.toPixels(getContext(), 6))
				{
					infoContainer.animate().setDuration(500).y(infoContainer.getHeight());
					hide_info.animate().rotation(180);
				}
				else
				{
					infoContainer.animate().setDuration(500).y(ScreenUtil.toPixels(getContext(), 6));
					hide_info.animate().rotation(0);
				}
			}
		});

		delete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				videoManager.removeVideo(position);
				videoContainer.animate().setDuration(500).alpha(0.0F).setListener(new AnimatorListenerAdapter()
				{
					public void onAnimationEnd(Animator animation)
					{
						videoManager.updateVideoList();
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

		views.setText(video.getViews() + " Views");
		bar.setRating(video.getRating());
		date.setText(video.getDate());
		infoBg.requestLayout();
		return row;
	}
}

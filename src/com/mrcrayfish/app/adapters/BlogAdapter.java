package com.mrcrayfish.app.adapters;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import android.widget.Toast;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.tasks.TaskGetBitmap;
import com.mrcrayfish.app.tasks.TaskGetBitmap.Type;
import com.mrcrayfish.app.tumblr.LinkPost;
import com.mrcrayfish.app.tumblr.PhotoPost;
import com.mrcrayfish.app.tumblr.Post;
import com.mrcrayfish.app.tumblr.TextPost;
import com.mrcrayfish.app.tumblr.VideoPost;
import com.mrcrayfish.app.util.SavedVideos;
import com.mrcrayfish.app.util.ScreenUtil;
import com.mrcrayfish.app.util.YouTubeUtil;

public class BlogAdapter extends ArrayAdapter<Post>
{
	private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(5);

	public BlogAdapter(Context context, ArrayList<Post> posts)
	{
		super(context, 0, posts);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		Post post = getItem(position);
		int type = getItemViewType(post, position);
		View row = layout.inflate(type, parent, false);
		switch (type)
		{
		case R.layout.blog_text_layout:
			handleTextPost(row, (TextPost) post);
			break;
		case R.layout.blog_photo_layout:
			handlePhotoPost(row, (PhotoPost) post);
			break;
		case R.layout.blog_link_layout:
			handleLinkPost(row, (LinkPost) post);
			break;
		case R.layout.video_item:
			handleVideoPost(row, (VideoPost) post);
			break;
		}
		return row;
	}

	public void handleTextPost(View row, TextPost post)
	{
		TextPost text = (TextPost) post;
		TextView title = (TextView) row.findViewById(R.id.blogTitle);
		TextView date = (TextView) row.findViewById(R.id.blogDate);
		TextView content = (TextView) row.findViewById(R.id.blogContent);

		title.setText(text.getTitle());
		date.setText(text.getDate());

		content.setText(text.getContent());
		content.setMovementMethod(LinkMovementMethod.getInstance());

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
	}

	public void handlePhotoPost(View row, PhotoPost post)
	{
		ImageView picture = (ImageView) row.findViewById(R.id.blogPhoto);
		TextView caption = (TextView) row.findViewById(R.id.blogCaption);
		TextView date = (TextView) row.findViewById(R.id.blogDate);

		caption.setText(post.getCaption());
		date.setText(post.getDate());

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		caption.setTypeface(bebas_neue);

		System.out.println(cache.get(post.getId()));
		if (cache.get(post.getId()) != null)
		{
			picture.setImageBitmap(cache.get(post.getId()));
		}
		else
		{
			picture.setAlpha(0.0F);
			new TaskGetBitmap(getContext(), picture, cache, Type.TUMBLR).execute(post.getId(), post.getImageUrl());
		}
	}

	public void handleLinkPost(View row, final LinkPost post)
	{
		TextView title = (TextView) row.findViewById(R.id.blogTitle);
		TextView date = (TextView) row.findViewById(R.id.blogDate);
		TextView content = (TextView) row.findViewById(R.id.blogContent);
		TextView link = (TextView) row.findViewById(R.id.blogLinkUrl);
		ImageView button = (ImageView) row.findViewById(R.id.blogLinkButton);

		title.setText(post.getTitle());
		date.setText(post.getDate());
		content.setText(Html.fromHtml(post.getDescription()).toString().replaceAll("\n", ""));
		link.setText(post.getLink());

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);

		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getLink()));
				getContext().startActivity(link);
			}
		});
	}

	public void handleVideoPost(View row, final VideoPost post)
	{
		final ImageView save = (ImageView) row.findViewById(R.id.saveVideo);
		ImageView thumbnail = (ImageView) row.findViewById(R.id.videoThumbnail);
		TextView title = (TextView) row.findViewById(R.id.videoTitle);
		TextView views = (TextView) row.findViewById(R.id.videoViews);
		RatingBar bar = (RatingBar) row.findViewById(R.id.videoRating);
		TextView date = (TextView) row.findViewById(R.id.videoDate);

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
		title.setText(post.getTitle());

		thumbnail.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				YouTubeUtil.openVideo(getContext(), post.getVideoId());
			}
		});

		save.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SavedVideos.add(getContext(), post.getVideoId());
				save.animate().alpha(0).setListener(new AnimatorListenerAdapter()
				{
					public void onAnimationEnd(Animator animation)
					{
						save.setVisibility(View.GONE);
					}
				});
			}
		});

		if (cache.get(post.getVideoId()) != null)
		{
			thumbnail.setImageBitmap(cache.get(post.getVideoId()));
		}
		else
		{
			thumbnail.setAlpha(0.0F);
			new TaskGetBitmap(getContext(), thumbnail, cache, Type.YOUTUBE).execute(post.getVideoId());
		}

		if (SavedVideos.has(getContext(), post.getVideoId()))
		{
			save.setVisibility(View.GONE);
		}

		views.setText(post.getViews() + " Views");
		bar.setRating(post.getRating());
		date.setText(post.getDate());
	}

	public int getItemViewType(Post post, int position)
	{
		if (post instanceof TextPost)
		{
			return R.layout.blog_text_layout;
		}
		else if (post instanceof LinkPost)
		{
			return R.layout.blog_link_layout;
		}
		else if (post instanceof PhotoPost)
		{
			return R.layout.blog_photo_layout;
		}
		else if (post instanceof VideoPost)
		{
			return R.layout.video_item;
		}
		return 0;
	}
}

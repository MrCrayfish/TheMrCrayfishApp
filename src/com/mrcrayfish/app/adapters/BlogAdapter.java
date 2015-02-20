package com.mrcrayfish.app.adapters;

import java.util.ArrayList;

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
import com.mrcrayfish.app.util.ScreenUtil;

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

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
	}

	public void handlePhotoPost(View row, PhotoPost post)
	{
		final RelativeLayout container = (RelativeLayout) row.findViewById(R.id.blogInfoContainer);
		final ImageView hide_info = (ImageView) row.findViewById(R.id.buttonHideInfo);
		final ImageView infoBg = (ImageView) row.findViewById(R.id.infoBackground);
		ImageView picture = (ImageView) row.findViewById(R.id.blogPhoto);
		TextView caption = (TextView) row.findViewById(R.id.blogCaption);
		TextView date = (TextView) row.findViewById(R.id.blogDate);

		caption.setText(post.getCaption());
		date.setText(post.getDate());
		
		infoBg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v)
			{
				Toast.makeText(getContext(), "Hey", Toast.LENGTH_SHORT).show();
			}
			
		});

		hide_info.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (container.getY() == ScreenUtil.toPixels(getContext(), 6))
				{
					container.animate().setDuration(500).y(infoBg.getHeight() + ScreenUtil.toPixels(getContext(), 6));
					hide_info.animate().rotation(180);
				}
				else
				{
					container.animate().setDuration(500).y(ScreenUtil.toPixels(getContext(), 6));
					hide_info.animate().rotation(0);
				}
			}
		});

		if (cache.get(post.getId()) != null)
		{
			picture.setImageBitmap(cache.get(post.getId()));
		}
		else
		{
			picture.setAlpha(0.0F);
			new TaskGetBitmap(getContext(), picture, cache, Type.TUMBLR).execute(post.getId(), post.getImageUrl());
		}
		infoBg.requestLayout();
	}

	public int getItemViewType(Post post, int position)
	{
		if (post instanceof TextPost)
		{
			return R.layout.blog_text_layout;
		}
		else if (post instanceof LinkPost)
		{

		}
		else if (post instanceof PhotoPost)
		{
			return R.layout.blog_photo_layout;
		}
		else if (post instanceof VideoPost)
		{

		}
		return 0;
	}
}

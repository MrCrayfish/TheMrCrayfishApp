package com.mrcrayfish.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.activities.VideosActivity;
import com.mrcrayfish.app.objects.PlaylistItem;
import com.mrcrayfish.app.tasks.TaskGetThumbnail;
import com.mrcrayfish.app.util.YouTubeUtil;

public class PlaylistAdapter extends ArrayAdapter<PlaylistItem>
{
	private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(5);

	public PlaylistAdapter(Context context, PlaylistItem[] objects)
	{
		super(context, 0, objects);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.playlist_item, parent, false);

		final PlaylistItem playlist = getItem(position);
		ImageView thumbnail = (ImageView) row.findViewById(R.id.playlistThumbnail);
		TextView title = (TextView) row.findViewById(R.id.playlistTitle);
		TextView size = (TextView) row.findViewById(R.id.playlistSize);
		ImageView infoBg = (ImageView) row.findViewById(R.id.infoBackground);
		TextView date = (TextView) row.findViewById(R.id.videoDate);
		ImageView options = (ImageView) row.findViewById(R.id.videoOptions);

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
		title.setText(playlist.getTitle());

		infoBg.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				openPlaylist(playlist);
			}
		});

		infoBg.setOnLongClickListener(new OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				YouTubeUtil.openPlaylist(getContext(), playlist.getPlaylistId());
				return true;
			}
		});
		
		options.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PopupMenu popup = new PopupMenu(getContext(), v);
				popup.inflate(R.menu.playlist_options);
				popup.setOnMenuItemClickListener(new OnMenuItemClickListener()
				{
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch (item.getItemId())
						{
						case R.id.optionPlaylistOpen:
							YouTubeUtil.openPlaylist(getContext(), playlist.getPlaylistId());
							break;
						case R.id.optionPlaylistView:
							openPlaylist(playlist);
							break;
						}
						return true;
					}
				});
				popup.show();
			}
		});

		if (cache.get(playlist.getThumbnailId()) != null)
		{
			thumbnail.setImageBitmap(cache.get(playlist.getThumbnailId()));
		}
		else
		{
			thumbnail.setAlpha(0.0F);
			new TaskGetThumbnail(getContext(), thumbnail, cache).execute(playlist.getThumbnailId());
		}

		size.setText(playlist.getSize() + " Videos");
		date.setText(playlist.getDate());
		infoBg.requestLayout();
		return row;
	}
	
	public void openPlaylist(PlaylistItem playlist)
	{
		Intent intent = new Intent(PlaylistAdapter.this.getContext(), VideosActivity.class);
		intent.putExtra("title", playlist.getTitle());
		intent.putExtra("playlist_id", playlist.getPlaylistId());
		intent.putExtra("isPlaylist", true);
		intent.putExtra("playlistSize", playlist.getSize());
		PlaylistAdapter.this.getContext().startActivity(intent);
	}
}

package com.mrcrayfish.app.adapters;

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
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeIntents;
import com.mrcrayfish.app.R;
import com.mrcrayfish.app.activities.VideosActivity;
import com.mrcrayfish.app.objects.PlaylistItem;
import com.mrcrayfish.app.tasks.TaskGetThumbnail;

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

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
		title.setText(playlist.getTitle());

		infoBg.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(PlaylistAdapter.this.getContext(), VideosActivity.class);
				intent.putExtra("title", playlist.getTitle());
				intent.putExtra("playlist_id", playlist.getPlaylistId());
				intent.putExtra("isPlaylist", true);
				intent.putExtra("playlistSize", playlist.getSize());
				PlaylistAdapter.this.getContext().startActivity(intent);
			}
		});

		infoBg.setOnLongClickListener(new OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				Intent intent = YouTubeIntents.createPlayPlaylistIntent(PlaylistAdapter.this.getContext(), playlist.getPlaylistId());
				if (YouTubeIntents.isYouTubeInstalled(PlaylistAdapter.this.getContext()))
				{
					PlaylistAdapter.this.getContext().startActivity(intent);
				}
				else
				{
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/playlist?list=" + playlist.getPlaylistId()));
					PlaylistAdapter.this.getContext().startActivity(intent);
				}
				return true;
			}

		});

		new TaskGetThumbnail(getContext(), thumbnail, cache).execute();

		size.setText(playlist.getSize() + " Videos");
		infoBg.requestLayout();
		return row;
	}
}

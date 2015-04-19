package com.mrcrayfish.app.adapters;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.activities.RecipeActivity;
import com.mrcrayfish.app.mod.ModAbout;
import com.mrcrayfish.app.mod.ModDownload;
import com.mrcrayfish.app.mod.ModLink;
import com.mrcrayfish.app.mod.ModPart;
import com.mrcrayfish.app.mod.ModRecipes;
import com.mrcrayfish.app.mod.ModScreenshots;
import com.mrcrayfish.app.mod.ModTitle;
import com.mrcrayfish.app.mod.ModVideo;
import com.mrcrayfish.app.tasks.TaskGetBitmap;
import com.mrcrayfish.app.tasks.TaskGetBitmap.Type;
import com.mrcrayfish.app.util.SavedVideos;
import com.mrcrayfish.app.util.YouTubeUtil;

public class ModAdapter extends ArrayAdapter<ModPart>
{
	private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(1);
	
	public ModAdapter(Context context, List<ModPart> parts)
	{
		super(context, 0, parts);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		ModPart part = getItem(position);
		int type = getItemViewType(part);
		View row = layout.inflate(type, parent, false);

		switch (type)
		{
		case R.layout.mod_title:
			handleTitle(row, (ModTitle) part);
			break;
		case R.layout.mod_about:
			handleAbout(row, (ModAbout) part);
			break;
		case R.layout.mod_screenshots:
			handleScreenshots(row, (ModScreenshots) part);
			break;
		case R.layout.mod_recipes:
			handleRecipePart(row, (ModRecipes) part);
			break;
		case R.layout.mod_more_info:
			handleDownloads(row, (ModDownload) part);
			break;
		case R.layout.mod_video:
			handleVideo(row, (ModVideo) part);
			break;
		}
		return row;
	}

	public int getItemViewType(ModPart part)
	{
		if (part instanceof ModTitle)
		{
			return R.layout.mod_title;
		}
		else if (part instanceof ModAbout)
		{
			return R.layout.mod_about;
		}
		else if (part instanceof ModScreenshots)
		{
			return R.layout.mod_screenshots;
		}
		else if (part instanceof ModRecipes)
		{
			return R.layout.mod_recipes;
		}
		else if (part instanceof ModDownload)
		{
			return R.layout.mod_more_info;
		}
		else if (part instanceof ModVideo)
		{
			return R.layout.mod_video;
		}
		return 0;
	}

	public void handleTitle(View row, ModTitle part)
	{
		ImageView banner = (ImageView) row.findViewById(R.id.banner);
		TextView title = (TextView) row.findViewById(R.id.modTitle);
		ImageView icon = (ImageView) row.findViewById(R.id.modIcon);

		banner.setImageResource(part.getBanner());
		title.setText(part.getTitle());
		icon.setImageResource(part.getIcon());

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
	}

	public void handleAbout(View row, ModAbout part)
	{
		TextView title = (TextView) row.findViewById(R.id.aboutTitle);
		TextView desc = (TextView) row.findViewById(R.id.modDesc);
		desc.setText(part.getDesc());

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
	}

	public void handleScreenshots(View row, ModScreenshots part)
	{
		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		TextView title = (TextView) row.findViewById(R.id.screenshotTitle);
		title.setTypeface(bebas_neue);

		GridView grid = (GridView) row.findViewById(R.id.screenshotsGrid);
		grid.setAdapter(new ScreenshotAdapter(row.getContext(), part.getScreenshots()));
	}

	public void handleRecipePart(View row, final ModRecipes part)
	{
		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");

		TextView title = (TextView) row.findViewById(R.id.recipeTitle);
		title.setTypeface(bebas_neue);

		TextView desc = (TextView) row.findViewById(R.id.recipeDesc);
		desc.setTypeface(bebas_neue);

		row.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getContext(), RecipeActivity.class);
				intent.putExtra("recipes", part.getRecipes());
				getContext().startActivity(intent);
			}
		});
	}

	public void handleDownloads(View row, ModDownload part)
	{
		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		TextView title = (TextView) row.findViewById(R.id.downloadsTitle);
		title.setTypeface(bebas_neue);

		ListView links = (ListView) row.findViewById(R.id.linkList);
		links.setAdapter(new ModLinkAdapter(row.getContext(), convert(part.getLinks())));
	}
	
	public void handleVideo(View row, final ModVideo part)
	{
		final ImageView save = (ImageView) row.findViewById(R.id.saveVideo);
		ImageView thumbnail = (ImageView) row.findViewById(R.id.videoThumbnail);
		TextView title = (TextView) row.findViewById(R.id.videoTitle);

		Typeface bebas_neue = Typeface.createFromAsset(row.getContext().getAssets(), "fonts/bebas_neue.otf");
		title.setTypeface(bebas_neue);
		title.setText(part.getTitle());

		thumbnail.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				YouTubeUtil.openVideo(getContext(), part.getVideoId());
			}
		});

		save.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SavedVideos.add(getContext(), part.getVideoId());
				save.animate().alpha(0).setListener(new AnimatorListenerAdapter()
				{
					public void onAnimationEnd(Animator animation)
					{
						save.setVisibility(View.GONE);
					}
				});
			}
		});

		if (cache.get(part.getVideoId()) != null)
		{
			thumbnail.setImageBitmap(cache.get(part.getVideoId()));
		}
		else
		{
			thumbnail.setAlpha(0.0F);
			new TaskGetBitmap(getContext(), thumbnail, cache, Type.YOUTUBE).execute(part.getVideoId());
		}

		if (SavedVideos.has(getContext(), part.getVideoId()))
		{
			save.setVisibility(View.GONE);
		}
	}

	private ArrayList<ModLink> convert(ModLink[] links)
	{
		ArrayList<ModLink> converted = new ArrayList<ModLink>();
		for (ModLink link : links)
		{
			converted.add(link);
		}
		return converted;
	}

}

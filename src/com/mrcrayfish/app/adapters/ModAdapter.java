package com.mrcrayfish.app.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.mod.ModAbout;
import com.mrcrayfish.app.mod.ModPart;
import com.mrcrayfish.app.mod.ModRecipes;
import com.mrcrayfish.app.mod.ModScreenshots;
import com.mrcrayfish.app.mod.ModTitle;

public class ModAdapter extends ArrayAdapter<ModPart>
{
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
			handleRecipePart(row, part);
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

	public void handleRecipePart(View row, ModPart part)
	{

	}

}

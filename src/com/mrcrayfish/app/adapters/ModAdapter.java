package com.mrcrayfish.app.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.mod.ModAbout;
import com.mrcrayfish.app.mod.ModDownload;
import com.mrcrayfish.app.mod.ModPart;
import com.mrcrayfish.app.mod.ModRecipes;
import com.mrcrayfish.app.mod.ModScreenshots;

public class ModAdapter extends ArrayAdapter<ModPart>
{
	public ModAdapter(Context context, List<ModPart> objects)
	{
		super(context, 0, objects);
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
		case R.layout.mod_item:
			break;
		case R.layout.mod_screenshots_layout:
			handleScreenshotsPart(row, part);
			break;
		case R.layout.mod_recipes_layout:
			handleRecipePart(row, part);
			break;
		}
		
		return row;
	}

	public int getItemViewType(ModPart part)
	{
		if (part instanceof ModAbout)
		{
			return R.layout.mod_item;
		}
		else if (part instanceof ModScreenshots)
		{
			return R.layout.mod_screenshots_layout;
		}
		else if (part instanceof ModDownload)
		{
			return 0;
		}
		else if (part instanceof ModRecipes)
		{
			return R.layout.mod_recipes_layout;
		}
		return 0;
	}
	
	public void handleScreenshotsPart(View row, ModPart part)
	{
		
	}
	
	public void handleRecipePart(View row, ModPart part)
	{
		
	}

}

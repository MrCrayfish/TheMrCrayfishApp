package com.mrcrayfish.app.mod;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mrcrayfish.app.R;

public class ModUtil
{
	public List<ModPart> generateFurnitureMod(Context context)
	{
		List<ModPart> parts = new ArrayList<ModPart>();
		parts.add(new ModAbout(R.drawable.banner_furniture, context.getResources().getString(R.string.desc_furniture_mod)));
		return parts;
	}
}

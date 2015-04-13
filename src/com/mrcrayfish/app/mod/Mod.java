package com.mrcrayfish.app.mod;

import java.util.ArrayList;
import java.util.List;

import com.mrcrayfish.app.R;

public class Mod
{
	public static List<ModPart> generateFurnitureMod()
	{
		List<ModPart> parts = new ArrayList<ModPart>();
		parts.add(new ModTitle(R.drawable.furniture_banner, "Furniture Mod", R.drawable.chair));
		parts.add(new ModAbout("This mod adds in over 40 unique pieces of Furniture into Minecraft. It includes furniture from the kitchen, living room, bedroom, garden, and much more!"));
		parts.add(new ModScreenshots(new int[] { R.drawable.furniture_screenshot_1, R.drawable.furniture_screenshot_2, R.drawable.furniture_screenshot_3, R.drawable.furniture_screenshot_4, R.drawable.furniture_screenshot_5, R.drawable.furniture_screenshot_6 }));
		return parts;
	}
}

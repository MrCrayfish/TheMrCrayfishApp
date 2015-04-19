package com.mrcrayfish.app.mod;

import java.util.ArrayList;
import java.util.List;

import com.mrcrayfish.app.R;

public class Mod
{
	public static List<ModPart> getFromModId(String mod_id)
	{
		if (mod_id.equals("cfm"))
			return generateFurnitureMod();
		if (mod_id.equals("csm"))
			return generateSkateboardingMod();
		if (mod_id.equals("ccm"))
			return generateConstructionMod();
		if (mod_id.equals("ct"))
			return generateCrayTokens();
		return null;
	}

	public static List<ModPart> generateFurnitureMod()
	{
		List<ModPart> parts = new ArrayList<ModPart>();
		parts.add(new ModTitle(R.drawable.furniture_banner, "Furniture Mod", R.drawable.chair));
		parts.add(new ModAbout("This mod adds in over 40 unique pieces of Furniture into Minecraft. It includes furniture from the kitchen, living room, bedroom, garden, and much more!"));
		parts.add(new ModScreenshots(new int[] { R.drawable.furniture_screenshot_1, R.drawable.furniture_screenshot_2, R.drawable.furniture_screenshot_3, R.drawable.furniture_screenshot_4, R.drawable.furniture_screenshot_5, R.drawable.furniture_screenshot_6 }));
		parts.add(new ModRecipes(R.drawable.furniture_recipe_1));
		parts.add(new ModVideo("Mod Showcase", "hQ24SL-MG18"));
		parts.add(new ModDownload(new ModLink("Website", "http://www.mrcrayfish.com/furniture.php"), new ModLink("Minecraft Forum", "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1282349"), new ModLink("Planet Minecraft", "http://www.planetminecraft.com/mod/11-furniture-mod-v10-simple-addtion/")));
		return parts;
	}

	public static List<ModPart> generateSkateboardingMod()
	{
		List<ModPart> parts = new ArrayList<ModPart>();
		parts.add(new ModTitle(R.drawable.skateboarding_banner, "Skateboarding Mod", R.drawable.ic_skateboard));
		parts.add(new ModAbout("This mod adds skateboards into the game. Not only that, you can perform tricks, grind, and grabs. Earn points to unlock new skateboards. Warning, this mod is still a work in progress."));
		parts.add(new ModScreenshots(new int[] { R.drawable.skateboard_screenshot_1, R.drawable.skateboard_screenshot_2}));
		parts.add(new ModRecipes(R.drawable.furniture_recipe_1));
		parts.add(new ModVideo("Development Log", "4GvOpR5sd-w"));
		parts.add(new ModDownload(new ModLink("Planet Minecraft", "http://www.planetminecraft.com/mod/181710-mrcrayfishs-skateboarding-mod-in-development/")));
		return parts;
	}

	public static List<ModPart> generateConstructionMod()
	{
		List<ModPart> parts = new ArrayList<ModPart>();
		parts.add(new ModTitle(R.drawable.construction_banner, "Construction Mod", R.drawable.ic_hammer));
		parts.add(new ModAbout("This mod allows you to construct buildings without any skills. Simply provide the materials and you can build your dream house with a few clicks."));
		parts.add(new ModScreenshots(new int[] { R.drawable.construction_screenshot_1, R.drawable.construction_screenshot_2, R.drawable.construction_screenshot_3, R.drawable.construction_screenshot_4}));
		parts.add(new ModRecipes(R.drawable.furniture_recipe_1));
		parts.add(new ModVideo("Tutorial", "Gvn9M48BIws"));
		parts.add(new ModDownload(new ModLink("Website", "http://www.mrcrayfish.com/construction.php"), new ModLink("Minecraft Forum", "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1292698"), new ModLink("Planet Minecraft", "http://www.planetminecraft.com/mod/162forgesspsmp-mrcrayfishs-auto-buildings-mod-build1---need-help-building-no-worries/")));
		return parts;
	}

	public static List<ModPart> generateCrayTokens()
	{
		List<ModPart> parts = new ArrayList<ModPart>();
		parts.add(new ModTitle(R.drawable.craytokens_banner, "Cray Tokens", R.drawable.ic_token));
		parts.add(new ModAbout("Cray Tokens is a simple yet advanced currency system to add to your server. This mod includes 4 tiers of Tokens; Copper, Silver, Gold and Platinum. You can obtain Tokens by killing hostile mobs."));
		parts.add(new ModScreenshots(new int[] { R.drawable.tokens_screenshot_1, R.drawable.tokens_screenshot_2, R.drawable.tokens_screenshot_3, R.drawable.tokens_screenshot_4}));
		parts.add(new ModRecipes(R.drawable.furniture_recipe_1));
		parts.add(new ModVideo("Mod Showcase", "RV5fdmapDXw"));
		parts.add(new ModDownload(new ModLink("Minecraft Forum", "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1291718")));
		return parts;
	}
}

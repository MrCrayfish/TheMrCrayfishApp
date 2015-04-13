package com.mrcrayfish.app.mod.database;

import android.provider.BaseColumns;

public class ModInfo
{
	private int version; 
	private String banner; 
	private String title; 
	private String descritpion; 
	private String screenshots;
	private String recipes;
	private String download_link;
	private String mod_version;
	
	public ModInfo(int version, String banner, String title, String descritpion, String screenshots, String recipes, String download_link, String mod_version)
	{
		this.version = version;
		this.banner = banner;
		this.title = title;
		this.descritpion = descritpion;
		this.screenshots = screenshots;
		this.recipes = recipes;
		this.download_link = download_link;
		this.mod_version = mod_version;
	}

	public int getVersion()
	{
		return version;
	}

	public String getBanner()
	{
		return banner;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDescritpion()
	{
		return descritpion;
	}

	public String getScreenshots()
	{
		return screenshots;
	}

	public String getRecipes()
	{
		return recipes;
	}

	public String getDownloadLink()
	{
		return download_link;
	}

	public String getModVersion()
	{
		return mod_version;
	}

	public static abstract class ModEntry implements BaseColumns
	{
		public static final String TABLE_NAME = "mods";
		public static final String ID = "id";
		public static final String VERSION = "info_version";
		public static final String BANNER = "banner";
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String SCREENSHOTS = "screenshots";
		public static final String RECIPES = "recipes";
		public static final String DOWNLOAD_LINK = "link";
		public static final String MOD_VERSION = "mod_version";
		public static final String MOD_ID = "mod_id";
	}
}

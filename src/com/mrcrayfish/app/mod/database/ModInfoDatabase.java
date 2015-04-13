package com.mrcrayfish.app.mod.database;

import com.mrcrayfish.app.mod.database.ModInfo.ModEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ModInfoDatabase extends SQLiteOpenHelper
{
	private static final String CREATE_TABLE = "CREATE TABLE " + ModEntry.TABLE_NAME + " (" + ModEntry.ID + " INTEGER PRIMARY KEY," + ModEntry.VERSION + "," + ModEntry.BANNER + " INTEGER," + ModEntry.TITLE + " TEXT," + ModEntry.DESCRIPTION + " TEXT," + ModEntry.SCREENSHOTS + " TEXT," + ModEntry.RECIPES + " TEXT," + ModEntry.DOWNLOAD_LINK + " TEXT," + ModEntry.MOD_VERSION + " TEXT)";

	public static final String DATABASE_NAME = "General.db";

	public ModInfoDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onUpgrade(db, oldVersion, newVersion);
	}

}

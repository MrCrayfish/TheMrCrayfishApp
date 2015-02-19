package com.mrcrayfish.app.tasks;

import android.os.AsyncTask;

import com.mrcrayfish.app.interfaces.IMenu;
import com.mrcrayfish.app.objects.MenuItem;

public class TaskLoadMenu extends AsyncTask<Void, MenuItem, Void>
{
	private IMenu menu;

	public TaskLoadMenu(IMenu menu)
	{
		this.menu = menu;
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		for (MenuItem item : menu.getItems())
		{
			this.publishProgress(item);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(MenuItem... item)
	{
		menu.getMenuAdapter().add(item[0]);
	}
}

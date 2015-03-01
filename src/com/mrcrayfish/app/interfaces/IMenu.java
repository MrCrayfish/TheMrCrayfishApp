package com.mrcrayfish.app.interfaces;

import java.util.ArrayList;

import android.widget.ArrayAdapter;

import com.mrcrayfish.app.objects.MenuItem;

public interface IMenu
{
	public ArrayAdapter<MenuItem> getMenuAdapter();

	public ArrayList<MenuItem> getItems();
}

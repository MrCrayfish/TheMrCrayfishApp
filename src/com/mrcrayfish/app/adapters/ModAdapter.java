package com.mrcrayfish.app.adapters;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.mrcrayfish.app.mod.Mod;

public class ModAdapter extends ArrayAdapter<Mod>
{
	public ModAdapter(Context context, List<Mod> objects)
	{
		super(context, 0, objects);
	}

}

package com.mrcrayfish.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.mrcrayfish.app.R;

public class RecipeAdapter extends ArrayAdapter<Integer>
{
	public RecipeAdapter(Context context, int[] recipes)
	{
		super(context, R.layout.mod_recipe, convert(recipes));
	}

	@Override
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(getContext());
		View row = layout.inflate(R.layout.mod_recipe, parent, false);
		ImageView recipe = (ImageView) row.findViewById(R.id.recipeImage);
		recipe.setImageResource(getItem(position));
		return row;
	}

	private static Integer[] convert(int[] oldArray)
	{
		Integer[] newArray = new Integer[oldArray.length];
		for (int i = 0; i < oldArray.length; i++)
		{
			newArray[i] = oldArray[i];
		}
		return newArray;
	}
}

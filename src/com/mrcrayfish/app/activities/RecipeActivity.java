package com.mrcrayfish.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.RecipeAdapter;

public class RecipeActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
		
		ListView list = (ListView) findViewById(R.id.recipeList);
		list.setAdapter(new RecipeAdapter(list.getContext(), getIntent().getIntArrayExtra("recipes")));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.recipe, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.mrcrayfish.app;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class GridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);

		setupActionBar();
		
		/*SpannableString s = new SpannableString(getResources().getText(R.string.menu_name));
		s.setSpan(new TypefaceSpan(this, "lobster.otf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		s.setSpan(new TextAppearanceSpan(null, 0, 100, null, null), 0,
				s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(s);*/

		ListView menu = (ListView) findViewById(R.id.menuList);
		menu.setDivider(null);
		menu.setDividerHeight(0);
		
		List<MenuItem> items = new ArrayList<MenuItem>();
		items.add(new MenuItem("Latest Videos", R.drawable.menu_item_bg_1, EmptyActivity.class));
		items.add(new MenuItem("Playlists", R.drawable.menu_item_bg_2, EmptyActivity.class));
		items.add(new MenuItem("Mods", R.drawable.menu_item_bg_3, EmptyActivity.class));
		items.add(new MenuItem("Furnture Server", R.drawable.menu_item_bg_4, EmptyActivity.class));
		items.add(new MenuItem("Category", R.drawable.menu_item_bg_5, EmptyActivity.class));
		items.add(new MenuItem("Category", R.drawable.menu_item_bg_6, EmptyActivity.class));
		menu.setAdapter(new MenuAdapter(this, items.toArray(new MenuItem[0])));
	}
	
	@SuppressLint("InflateParams")
	public void setupActionBar()
	{
		ActionBar ab = getActionBar();
		ab.setDisplayShowHomeEnabled(false);
		ab.setDisplayShowTitleEnabled(false);
		
		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.app_bar, null);
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/bebas_neue.otf"); 
		TextView title = (TextView) v.findViewById(R.id.barTitle);
		title.setTypeface(type);
		
		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

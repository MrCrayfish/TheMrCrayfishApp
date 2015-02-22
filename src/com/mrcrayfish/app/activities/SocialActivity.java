package com.mrcrayfish.app.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.SocialAdapter;
import com.mrcrayfish.app.objects.SocialItem;

public class SocialActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_social);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		GridView grid = (GridView) findViewById(R.id.socialGrid);
		grid.setAdapter(new SocialAdapter(this, compile()));

		setupActionBar();

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item)
	{
		int id = item.getItemId();
		if (id == android.R.id.home)
		{
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition(R.anim.animation_slide_right_1, R.anim.animation_slide_right_2);
	}

	@SuppressLint("InflateParams")
	public void setupActionBar()
	{
		ActionBar ab = getActionBar();
		ab.setDisplayShowHomeEnabled(false);
		ab.setDisplayShowTitleEnabled(false);

		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.app_bar, null);
		Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bebas_neue.otf");
		TextView title = (TextView) v.findViewById(R.id.barTitle);
		title.setTypeface(type);
		title.setText("Social Media");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public List<SocialItem> compile()
	{
		List<SocialItem> items = new ArrayList<SocialItem>();
		items.add(new SocialItem("Twitter", "https://twitter.com/MrCraayfish", R.drawable.twitter, R.color.twitter, R.drawable.repeatable_twitter_background));
		items.add(new SocialItem("Facebook", "https://www.facebook.com/MrCrayfish", R.drawable.facebook, R.color.facebook, R.drawable.repeatable_facebook_background));
		items.add(new SocialItem("Reddit", "http://www.reddit.com/user/MrCrayfish/", R.drawable.reddit, R.color.reddit, R.drawable.repeatable_reddit_background));
		items.add(new SocialItem("Instagram", "https://instagram.com/mrcraayfish", R.drawable.instagram, R.color.instagram, R.drawable.repeatable_instagram_background));
		items.add(new SocialItem("GitHub", "https://github.com/MrCrayfish", R.drawable.github, R.color.github, R.drawable.repeatable_github_background));
		items.add(new SocialItem("Vine", "https://vine.co/u/1009468775671943168", R.drawable.vine, R.color.vine, R.drawable.repeatable_vine_background));
		items.add(new SocialItem("PlanetMC", "http://www.planetminecraft.com/member/mr_crayfish/", R.drawable.planetmc, R.color.planetmc, R.drawable.repeatable_planetmc_background));
		items.add(new SocialItem("MC Forums", "http://www.minecraftforum.net/members/Mr_Crayfish", R.drawable.minecraftforum, R.color.mcforums, R.drawable.repeatable_mcforums_background));
		items.add(new SocialItem("Website", "http://www.mrcrayfish.com", R.drawable.website, R.color.red, R.drawable.repeatable_mrcrayfish_background));
		return items;
	}
}

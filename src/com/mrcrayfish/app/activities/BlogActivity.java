package com.mrcrayfish.app.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.BlogAdapter;
import com.mrcrayfish.app.tasks.TaskFetchBlogPosts;
import com.mrcrayfish.app.tumblr.Post;
import com.mrcrayfish.app.util.ScreenUtil;

public class BlogActivity extends Activity implements OnRefreshListener
{
	public SwipeRefreshLayout swipeLayout;
	public RelativeLayout loadingContainer;
	private TextView loadingText;
	private ListView postList;
	private BlogAdapter blogAdapter;
	private ArrayList<Post> posts = null;
	private boolean loaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_blog);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		setupActionBar();

		loadingContainer = (RelativeLayout) findViewById(R.id.loadingContainer);
		loadingText = (TextView) findViewById(R.id.loadingText);

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
		swipeLayout.setOnRefreshListener(this);

		postList = (ListView) findViewById(R.id.blogList);
		postList.setDivider(null);
		postList.setDividerHeight(ScreenUtil.toPixels(this, 5));

		Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bebas_neue.otf");
		loadingText.setTypeface(type);
		loadingText.setText("Loading Posts");

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (posts == null)
		{
			new TaskFetchBlogPosts(this).execute();
		}
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

	public void initList()
	{
		if (posts != null && !loaded)
		{
			blogAdapter = new BlogAdapter(this, posts);
			postList.setAdapter(blogAdapter);
			loaded = true;
		}
	}

	public void setPostList(ArrayList<Post> posts)
	{
		this.posts = posts;
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
		title.setText("Blog");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public TextView getLoadingText()
	{
		return loadingText;
	}

	public ArrayAdapter<Post> getAdapter()
	{
		return blogAdapter;
	}

	public void updatePosts()
	{
		blogAdapter.clear();
		blogAdapter.addAll(posts);
		blogAdapter.notifyDataSetChanged();
	}

	@Override
	public void onRefresh()
	{
		new TaskFetchBlogPosts(this).execute();
	}
}

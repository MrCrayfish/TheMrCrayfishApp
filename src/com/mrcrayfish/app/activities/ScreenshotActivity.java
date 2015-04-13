package com.mrcrayfish.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.mrcrayfish.app.R;

public class ScreenshotActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screenshot);
		ImageView screenshot = (ImageView) findViewById(R.id.screenshotImage);
		screenshot.setImageResource(getIntent().getIntExtra("screenshot", R.drawable.furniture_screenshot_1));
	}
}

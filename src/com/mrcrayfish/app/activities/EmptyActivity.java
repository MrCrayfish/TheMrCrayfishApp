package com.mrcrayfish.app.activities;

import android.app.Activity;
import android.os.Bundle;

import com.mrcrayfish.app.R;

public class EmptyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empty);
		overridePendingTransition(R.anim.animation_slide_right_1, R.anim.animation_slide_right_2);
	}
}

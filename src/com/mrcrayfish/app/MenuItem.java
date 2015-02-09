package com.mrcrayfish.app;

public class MenuItem {

	private String title;
	private int background;
	private Class<?> activity;

	public MenuItem(String title, int background, Class<?> activity) {
		this.title = title;
		this.background = background;
		this.activity = activity;
	}

	public String getTitle() {
		return title;
	}

	public int getBackground() {
		return background;
	}

	public Class<?> getActivity() {
		return activity;
	}
}

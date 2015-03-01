package com.mrcrayfish.app.objects;

public class SoundItem
{
	private String desc;
	private int sound_id;

	public SoundItem(String desc, int sound_id)
	{
		this.desc = desc;
		this.sound_id = sound_id;
	}

	public String getDesc()
	{
		return desc;
	}

	public int getSoundId()
	{
		return sound_id;
	}
}

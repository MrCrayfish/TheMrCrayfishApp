package com.mrcrayfish.app.mod;

public class ModRecipes extends ModPart
{
	private int[] recipes;

	public ModRecipes(int ... recipes)
	{
		this.recipes = recipes;
	}
	public int[] getRecipes()
	{
		return recipes;
	}
}

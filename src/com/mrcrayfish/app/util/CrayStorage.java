package com.mrcrayfish.app.util;

/**
 * CrayStorage
 * Copyright (C) 2014 MrCrayfish
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CrayStorage
{
	private static HashMap<String, Object> parent = new HashMap<String, Object>();
	private int spacing = 0;
	private int count = 0;
	private File file;
	private boolean loaded = false;

	public CrayStorage(File file)
	{
		this.file = file;
	}

	public CrayStorage load()
	{
		if (!loaded)
		{
			parseFile(file);
			loaded = true;
		}
		return this;
	}

	public CrayStorage save()
	{
		try
		{
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			saveMap(bw, parent);
			bw.flush();
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	private void saveMap(BufferedWriter bw, HashMap<String, Object> map)
	{
		try
		{
			count++;
			for (String key : map.keySet())
			{

				Object object = map.get(key);
				if (object instanceof HashMap)
				{
					if (count > 1)
						bw.newLine();
					bw.write(getSpacing() + key);
					bw.newLine();
					bw.write(getSpacing() + "{");

					spacing++;
					saveMap(bw, (HashMap<String, Object>) object);
					spacing--;

					bw.newLine();
					bw.write(getSpacing() + "}");
					if (count == 1)
						bw.newLine();
				}
				else
				{
					bw.newLine();
					bw.write(getSpacing() + key + ": ");
					bw.write(object.toString());
				}
			}
			count--;

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void print()
	{
		print(parent);
	}

	@SuppressWarnings("unchecked")
	private void print(HashMap<String, Object> map)
	{
		count++;
		for (String key : map.keySet())
		{

			Object object = map.get(key);
			if (object instanceof HashMap)
			{
				if (count > 1)
					System.out.println();
				System.out.println(getSpacing() + key);
				System.out.print(getSpacing() + "{");

				spacing++;
				print((HashMap<String, Object>) object);
				spacing--;

				System.out.println();
				System.out.print(getSpacing() + "}");
			}
			else
			{
				System.out.println();
				System.out.print(getSpacing() + key + ": ");
				System.out.print(object.toString());
			}
		}
		count--;
	}

	private String getSpacing()
	{
		String space = "";
		for (int i = 0; i < spacing; i++)
		{
			space += "  ";
		}
		return space;
	}

	public void set(String path, Object value)
	{
		getMap(path).put(getKeyFromPath(path), value);
	}

	public Object get(String path)
	{
		return getMap(path).get(getKeyFromPath(path));
	}

	@SuppressWarnings("unchecked")
	private void createEntry(String[] pathData)
	{
		HashMap<String, Object> previous = parent;
		for (int i = 0; i < pathData.length - 1; i++)
		{
			if (!previous.containsKey(pathData[i]))
			{
				HashMap<String, Object> temp = new HashMap<String, Object>();
				previous.put(pathData[i], temp);
				previous = temp;
			}
			else
			{
				previous = (HashMap<String, Object>) previous.get(pathData[i]);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getMap(String path)
	{
		String[] pathData = path.split("\\.");
		if (pathData.length == 1)
		{
			return parent;
		}
		else
		{
			createEntry(pathData);
			HashMap<String, Object> map = parent;
			for (int i = 0; i < pathData.length - 1; i++)
			{
				HashMap<String, Object> subMap = (HashMap<String, Object>) map.get(pathData[i]);
				map = subMap;
			}
			return map;
		}
	}

	private String getKeyFromPath(String path)
	{
		return path.split("\\.")[path.split("\\.").length - 1];
	}

	private HashMap<String, Object> parseFile(File file)
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader(file));
			return parseMap(br, parent);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private HashMap<String, Object> parseMap(BufferedReader br, HashMap<String, Object> map)
	{
		HashMap<String, Object> prevMap = map;
		String prevKey = null;
		String line = null;
		try
		{
			while ((line = br.readLine()) != null)
			{
				line = line.replaceAll(" ", "");
				if (line.equals("{"))
				{
					HashMap<String, Object> temp = new HashMap<String, Object>();
					parseMap(br, temp);
					prevMap.put(prevKey, temp);
				}
				else if (line.equals("}"))
				{
					return prevMap;
				}
				else if (line.contains(":"))
				{
					String[] values = line.split(":");
					prevMap.put(values[0], values[1]);
				}
				else
				{
					prevKey = line;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return prevMap;
	}

	public int getSize(String path)
	{
		if (path.equals("parent"))
		{
			return parent.size();
		}
		Object obj = get(path);
		if (obj instanceof HashMap<?, ?>)
		{
			HashMap<?, ?> map = (HashMap<?, ?>) obj;
			return map.size();
		}
		return -1;
	}
}
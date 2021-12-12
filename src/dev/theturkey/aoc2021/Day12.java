package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 extends AOCPuzzle
{
	//This is bad code....
	public Day12()
	{
		super("12");
	}

	public Map<String, List<String>> paths;

	@Override
	void solve(List<String> input)
	{
		paths = new HashMap<>();
		for(String s : input)
		{
			String[] pathParts = s.trim().split("-");
			paths.computeIfAbsent(pathParts[0], k -> new ArrayList<>()).add(pathParts[1]);
			paths.computeIfAbsent(pathParts[1], k -> new ArrayList<>()).add(pathParts[0]);
		}

		//Part 1
		lap(navigate("start", new ArrayList<>(), false).size());
		//Part 2
		List<List<String>> part2List = navigate("start", new ArrayList<>(), true);

		for(int i = part2List.size() - 1; i >= 0; i--)
		{
			List<String> l1 = part2List.get(i);
			boolean remove = false;
			for(int j = part2List.size() - 1; j >= 0; j--)
			{
				if(i == j)
					continue;
				List<String> l2 = part2List.get(j);
				if(l1.equals(l2))
				{
					remove = true;
					break;
				}
			}

			if(remove)
				part2List.remove(i);
		}
		lap(part2List.size());
	}

	public List<List<String>> navigate(String pos, List<String> visited, boolean allowTwice)
	{
		if(pos.equalsIgnoreCase("end"))
		{
			List<String> one = new ArrayList<>();
			one.add("end");
			List<List<String>> two = new ArrayList<>();
			two.add(one);
			return two;
		}

		List<List<String>> toReturn = new ArrayList<>();

		if(allowTwice && !pos.equals("start"))
		{
			for(String dest : paths.get(pos))
			{
				if(dest.charAt(0) >= 'a' && dest.charAt(0) <= 'z' && visited.contains(dest))
					continue;
				List<List<String>> toAdd = navigate(dest, new ArrayList<>(visited), false);
				for(int i = toAdd.size() - 1; i >= 0; i--)
				{
					boolean remove = true;
					for(String s : toAdd.get(i))
					{
						if(s.equals(pos))
						{
							remove = false;
							break;
						}
					}

					if(remove)
						toAdd.remove(i);
				}
				toReturn.addAll(toAdd);
			}
		}

		visited.add(pos);
		for(String dest : paths.get(pos))
		{
			if(dest.charAt(0) >= 'a' && dest.charAt(0) <= 'z' && visited.contains(dest))
				continue;
			toReturn.addAll(navigate(dest, new ArrayList<>(visited), allowTwice));
		}

		for(List<String> sp : toReturn)
			sp.add(pos);

		return toReturn;
	}
}

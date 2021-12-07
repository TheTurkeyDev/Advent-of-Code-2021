package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day07 extends AOCPuzzle
{
	public Day07()
	{
		super("7");
	}

	@Override
	void solve(List<String> input)
	{
		List<Integer> crabPos = new ArrayList<>();
		for(String s : input.get(0).split(","))
			crabPos.add(Integer.parseInt(s));

		int min = crabPos.stream().min(Integer::compareTo).get();
		int max = crabPos.stream().max(Integer::compareTo).get();

		//PART 1
		long lowestCost = Integer.MAX_VALUE;

		for(int loc = min; loc <= max; loc++)
		{
			long cost = getCostToPos(crabPos, loc);
			if(cost < lowestCost)
				lowestCost = cost;
		}

		lap(lowestCost);

		//PART 2
		lowestCost = Integer.MAX_VALUE;

		for(int loc = min; loc <= max; loc++)
		{
			long cost = getCostToPos2(crabPos, loc);
			if(cost < lowestCost)
				lowestCost = cost;
		}

		lap(lowestCost);

	}

	public long getCostToPos(List<Integer> pos, int toPos)
	{
		long cost = 0;
		for(int i : pos)
			cost += Math.abs(toPos - i);

		return cost;
	}

	public long getCostToPos2(List<Integer> pos, int toPos)
	{
		long cost = 0;
		for(int i : pos)
		{
			long dist = Math.abs(toPos - i);
			cost += (dist * (dist + 1)) / 2L;
		}

		return cost;
	}

}

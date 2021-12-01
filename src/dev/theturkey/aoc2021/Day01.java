package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day01 extends AOCPuzzle
{
	public Day01()
	{
		super("1");
	}

	@Override
	void solve(List<String> input)
	{
		List<Integer> depths = convertToInts(input);

		//PART 1
		int changes = 0;
		for(int i = 1; i < depths.size(); i++)
			if(depths.get(i - 1) < depths.get(i))
				changes++;

		lap(changes);

		//PART 2
		List<Integer> windows = new ArrayList<>();
		for(int i = 2; i < depths.size(); i++)
			windows.add(depths.get(i) + depths.get(i - 1) + depths.get(i - 2));

		changes = 0;
		for(int i = 1; i < windows.size(); i++)
			if(windows.get(i - 1) < windows.get(i))
				changes++;

		lap(changes);
	}
}

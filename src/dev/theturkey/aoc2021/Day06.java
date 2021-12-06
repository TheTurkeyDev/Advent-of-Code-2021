package dev.theturkey.aoc2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 extends AOCPuzzle
{
	public Day06()
	{
		super("6");
	}

	@Override
	void solve(List<String> input)
	{
		Map<Integer, Long> fish = new HashMap<>();

		for(String s : input.get(0).split(","))
		{
			int life = Integer.parseInt(s);
			fish.put(life, fish.getOrDefault(life, 0L) + 1);
		}

		//PART 1
		Map<Integer, Long> copy = new HashMap<>(fish);
		for(int day = 0; day < 80; day++)
			tick(copy);

		long sum = 0;
		for(long fishCount : copy.values())
			sum += fishCount;
		lap(sum);

		//PART 2
		for(int day = 0; day < 256; day++)
			tick(fish);

		sum = 0;
		for(long fishCount : fish.values())
			sum += fishCount;
		lap(sum);
	}

	public void tick(Map<Integer, Long> fish)
	{
		for(int fishLife = 0; fishLife <= 8; fishLife++)
			fish.put(fishLife - 1, fish.getOrDefault(fishLife, 0L));
		fish.put(6, fish.getOrDefault(6, 0L) + fish.get(-1));
		fish.put(8, fish.remove(-1));
	}
}

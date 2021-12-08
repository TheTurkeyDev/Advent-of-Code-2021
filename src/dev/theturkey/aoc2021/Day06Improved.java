package dev.theturkey.aoc2021;

import java.util.Arrays;
import java.util.List;

public class Day06Improved extends AOCPuzzle
{
	public Day06Improved()
	{
		super("6");
	}

	@Override
	void solve(List<String> input)
	{
		long[] fish = new long[9];

		for(String s : input.get(0).split(","))
			fish[Integer.parseInt(s)] += 1;

		//PART 1
		long[] copy = Arrays.copyOf(fish, fish.length);
		for(int day = 0; day < 80; day++)
			tick(copy);

		long sum = 0;
		for(long fishCount : copy)
			sum += fishCount;
		lap(sum);

		//PART 2
		for(int day = 0; day < 256; day++)
			tick(fish);

		sum = 0;
		for(long fishCount : fish)
			sum += fishCount;
		lap(sum);
	}

	public void tick(long[] fish)
	{
		long zero = fish[0];
		for(int fishLife = 1; fishLife < 9; fishLife++)
			fish[fishLife - 1] = fish[fishLife];
		fish[6] += zero;
		fish[8] = zero;
	}
}

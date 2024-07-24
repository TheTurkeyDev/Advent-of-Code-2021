package dev.theturkey.aoc2021;

import java.util.Arrays;
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
	public void solve(List<String> input) {
		System.out.println(process(80, input));
	        System.out.println(process(256, input));
    	}

    	private long process(int days, List<String> input) {
        	Map<Integer, Long> fish = new HashMap<>();

        	Arrays.stream(input.get(0).split(",")).forEach(s -> fish.put(Integer.parseInt(s),
                                                     fish.getOrDefault(Integer.parseInt(s), 0L) + 1));
        	for(int day = 0; day < days; day++) {
            		tick(fish);
		}

        	long sum = 0;
        	for(long fishCount : fish.values()) {
            		sum += fishCount;
		}
        	return sum;
    	}	

	public void tick(Map<Integer, Long> fish)
	{
		for(int fishLife = 0; fishLife <= 8; fishLife++)
			fish.put(fishLife - 1, fish.getOrDefault(fishLife, 0L));
		fish.put(6, fish.getOrDefault(6, 0L) + fish.get(-1));
		fish.put(8, fish.remove(-1));
	}
}

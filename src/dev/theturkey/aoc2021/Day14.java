package dev.theturkey.aoc2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends AOCPuzzle
{
	public Day14()
	{
		super("14");
	}

	public Map<String, String> insertions;

	@Override
	void solve(List<String> input)
	{
		String polymer = input.get(0);

		insertions = new HashMap<>();
		for(int i = 2; i < input.size(); i++)
		{
			String s = input.get(i);
			String[] parts = s.split(" -> ");
			insertions.put(parts[0], parts[1]);
		}

		//PART 1
		Map<String, Long> calcPairCounts = calcPairCounts(polymer, 10);
		lap(countLetters(calcPairCounts));

		//PART 2
		calcPairCounts = calcPairCounts(polymer, 40);
		lap(countLetters(calcPairCounts));

	}

	public Map<String, Long> calcPairCounts(String polymer, int steps)
	{
		Map<String, Long> pairCounts = new HashMap<>();
		for(int i = 0; i < polymer.length() - 1; i++)
		{
			String pair = polymer.substring(i, i + 2);
			pairCounts.put(pair, pairCounts.getOrDefault(pair, 0L) + 1);
		}

		for(int i = 0; i < steps; i++)
		{
			Map<String, Long> newPairCounts = new HashMap<>();
			for(String pair : pairCounts.keySet())
			{
				long pairCount = pairCounts.get(pair);
				String insert = insertions.get(pair);
				String newPair1 = pair.charAt(0) + insert;
				String newPair2 = insert + pair.charAt(1);
				newPairCounts.put(newPair1, newPairCounts.getOrDefault(newPair1, 0L) + pairCount);
				newPairCounts.put(newPair2, newPairCounts.getOrDefault(newPair2, 0L) + pairCount);
			}
			pairCounts = newPairCounts;
		}
		return pairCounts;
	}

	public long countLetters(Map<String, Long> pairCounts)
	{
		Map<Character, Long> counts = new HashMap<>();

		for(String s : pairCounts.keySet())
		{
			long sCount = pairCounts.get(s);

			char c0 = s.charAt(0);
			counts.put(c0, counts.getOrDefault(c0, 0L) + sCount);

			char c1 = s.charAt(1);
			counts.put(c1, counts.getOrDefault(c1, 0L) + sCount);
		}

		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;

		for(long l : counts.values())
		{
			if(min > l)
				min = l;
			if(max < l)
				max = l;
		}

		return (max / 2) - (min / 2) + 1;
	}
}

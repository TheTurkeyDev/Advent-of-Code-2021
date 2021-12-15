package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day15 extends AOCPuzzle
{
	public Day15()
	{
		super("15");
	}

	@Override
	void solve(List<String> input)
	{
		int[][] map = new int[input.size()][input.get(0).length()];
		int[][] map2 = new int[input.size() * 5][input.get(0).length() * 5];

		for(int row = 0; row < input.size(); row++)
		{
			String s = input.get(row);
			for(int col = 0; col < s.length(); col++)
			{
				int value = s.charAt(col) - 48;
				for(int rowRep = 0; rowRep < 5; rowRep++)
				{
					for(int colRep = 0; colRep < 5; colRep++)
					{
						int newVal = (value + rowRep + colRep);
						while(newVal > 9)
							newVal -= 9;

						map2[row + (rowRep * input.size())][col + (colRep * s.length())] = newVal;
						if(rowRep == 0 && colRep == 0)
							map[row][col] = newVal;
					}
				}

			}
		}

		pathFind(map);
		pathFind(map2);
	}

	public void pathFind(int[][] map)
	{
		Set<Point> visited = new HashSet<>();
		Map<Point, Long> available = new HashMap<>();
		available.put(new Point(0, 0), 0L);
		while(available.size() > 0)
		{
			long lowestVal = Long.MAX_VALUE;
			Point lowest = null;
			for(Point p : available.keySet())
			{
				long pVal = available.get(p);
				if(pVal < lowestVal)
				{
					lowest = p;
					lowestVal = pVal;
				}
			}

			if(lowest.row == map.length - 1 && lowest.col == map[0].length - 1)
			{
				lap(lowestVal);
				break;
			}

			available.remove(lowest);
			visited.add(lowest);

			List<Point> toCheck = new ArrayList<>();
			if(lowest.col - 1 >= 0)
				toCheck.add(new Point(lowest.row, lowest.col - 1));
			if(lowest.col + 1 < map[lowest.row].length)
				toCheck.add(new Point(lowest.row, lowest.col + 1));
			if(lowest.row - 1 >= 0)
				toCheck.add(new Point(lowest.row - 1, lowest.col));
			if(lowest.row + 1 < map.length)
				toCheck.add(new Point(lowest.row + 1, lowest.col));

			for(Point newPoint : toCheck)
			{
				if(!visited.contains(newPoint))
				{
					long pointVal = map[newPoint.row][newPoint.col] + lowestVal;
					if(available.getOrDefault(newPoint, Long.MAX_VALUE) > pointVal)
						available.put(newPoint, pointVal);
				}
			}
		}
	}
}

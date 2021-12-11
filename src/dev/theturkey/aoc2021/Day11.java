package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day11 extends AOCPuzzle
{
	public Day11()
	{
		super("11");
	}

	@Override
	void solve(List<String> input)
	{
		//PART 1
		int[][] octopi = parseInput(input);

		int flashes = 0;
		for(int step = 0; step < 100; step++)
		{
			flashes += flashOctopi(octopi);
			resetFlashedOctopi(octopi);
		}
		lap(flashes);

		// PART 2
		octopi = parseInput(input);

		int step = 0;
		flashes = 0;
		while(flashes < octopi.length * octopi[0].length)
		{
			step++;
			flashes = flashOctopi(octopi);
			resetFlashedOctopi(octopi);
		}
		lap(step);
	}

	public int[][] parseInput(List<String> input)
	{
		int[][] octopi = new int[input.size()][input.get(0).length()];
		for(int i = 0; i < input.size(); i++)
		{
			String s = input.get(i);
			for(int j = 0; j < s.length(); j++)
				octopi[i][j] = s.charAt(j) - 48;
		}
		return octopi;
	}

	public int flashOctopi(int[][] octopi)
	{
		int flashes = 0;
		for(int i = 0; i < octopi.length; i++)
		{
			for(int j = 0; j < octopi[i].length; j++)
			{
				List<Point> toVisit = new ArrayList<>();
				toVisit.add(new Point(i, j));
				while(toVisit.size() > 0)
				{
					Point p = toVisit.remove(0);
					octopi[p.row][p.col] += 1;
					if(octopi[p.row][p.col] == 10)
					{
						flashes++;
						addAllAdjacentPoints(octopi, toVisit, p.row, p.col);
					}
				}
			}
		}
		return flashes;
	}

	public void addAllAdjacentPoints(int[][] octopi, List<Point> toVisit, int row, int col)
	{
		if(row - 1 >= 0)
			toVisit.add(new Point(row - 1, col));
		if(row - 1 >= 0 && col + 1 < octopi[row].length)
			toVisit.add(new Point(row - 1, col + 1));
		if(col + 1 < octopi[row].length)
			toVisit.add(new Point(row, col + 1));
		if(row + 1 < octopi.length && col + 1 < octopi[row].length)
			toVisit.add(new Point(row + 1, col + 1));
		if(row + 1 < octopi.length)
			toVisit.add(new Point(row + 1, col));
		if(row + 1 < octopi.length && col - 1 >= 0)
			toVisit.add(new Point(row + 1, col - 1));
		if(col - 1 >= 0)
			toVisit.add(new Point(row, col - 1));
		if(row - 1 >= 0 && col - 1 >= 0)
			toVisit.add(new Point(row - 1, col - 1));
	}

	public void resetFlashedOctopi(int[][] octopi)
	{
		for(int i = 0; i < octopi.length; i++)
			for(int j = 0; j < octopi[i].length; j++)
				if(octopi[i][j] > 9)
					octopi[i][j] = 0;
	}

	public static class Point
	{
		public int row;
		public int col;

		public Point(int row, int col)
		{
			this.row = row;
			this.col = col;
		}
	}
}

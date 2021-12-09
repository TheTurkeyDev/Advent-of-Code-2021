package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Day09 extends AOCPuzzle
{
	public Day09()
	{
		super("9");
	}

	@Override
	void solve(List<String> input)
	{
		int[][] map = new int[input.size()][input.get(0).length()];

		for(int row = 0; row < map.length; row++)
		{
			String s = input.get(row).strip();
			for(int col = 0; col < map[row].length; col++)
				map[row][col] = Integer.parseInt(s.substring(col, col + 1));
		}

		List<Point> lows = new ArrayList<>();
		//PART 1
		long sum = 0;
		for(int row = 0; row < map.length; row++)
		{
			for(int col = 0; col < map[row].length; col++)
			{
				int curr = map[row][col];
				if(row - 1 >= 0 && curr >= map[row - 1][col])
					continue;
				if(row + 1 < map.length && curr >= map[row + 1][col])
					continue;
				if(col - 1 >= 0 && curr >= map[row][col - 1])
					continue;
				if(col + 1 < map[row].length && curr >= map[row][col + 1])
					continue;

				lows.add(new Point(row, col));
				sum += curr + 1;
			}
		}

		lap(sum);

		//PART 2
		List<Long> largest = new ArrayList<>();
		for(Point p : lows)
		{
			List<Point> visited = new ArrayList<>();
			List<Point> toVisit = new ArrayList<>();
			toVisit.add(p);
			while(toVisit.size() > 0)
			{
				Point nextPoint = toVisit.remove(0);
				if(visited.contains(nextPoint))
					continue;

				visited.add(nextPoint);
				int row = nextPoint.row;
				int col = nextPoint.col;
				if(row - 1 >= 0 && map[row - 1][col] != 9)
				{
					Point down = new Point(row - 1, col);
					if(!visited.contains(down))
						toVisit.add(down);
				}
				if(row + 1 < map.length && map[row + 1][col] != 9)
				{
					Point up = new Point(row + 1, col);
					if(!visited.contains(up))
						toVisit.add(up);
				}
				if(col - 1 >= 0 && map[row][col - 1] != 9)
				{
					Point left = new Point(row, col - 1);
					if(!visited.contains(left))
						toVisit.add(left);
				}
				if(col + 1 < map[row].length && map[row][col + 1] != 9)
				{
					Point right = new Point(row, col + 1);
					if(!visited.contains(right))
						toVisit.add(right);
				}
			}

			if(largest.size() < 3)
			{
				largest.add((long) visited.size());
			}
			else
			{
				if(largest.get(2) < (long) visited.size())
					largest.set(2, (long) visited.size());
			}
			largest.sort(Long::compare);
			largest.sort(Collections.reverseOrder());
		}

		lap(largest.get(0) * largest.get(1) * largest.get(2));
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

		@Override
		public boolean equals(Object o)
		{
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			Point point = (Point) o;
			return row == point.row && col == point.col;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(row, col);
		}
	}
}

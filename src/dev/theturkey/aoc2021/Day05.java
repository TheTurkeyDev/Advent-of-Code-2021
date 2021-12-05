package dev.theturkey.aoc2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day05 extends AOCPuzzle
{
	public Day05()
	{
		super("5");
	}

	@Override
	void solve(List<String> input)
	{
		Map<Point, Point> inputLines = new HashMap<>();
		for(String s : input)
		{
			String[] linePoints = s.split(" -> ");
			String[] point1 = linePoints[0].split(",");
			String[] point2 = linePoints[1].split(",");
			inputLines.put(new Point(point1), new Point(point2));
		}

		//Part 1
		Map<Point, Integer> points = new HashMap<>();
		for(Map.Entry<Point, Point> line : inputLines.entrySet())
		{
			Point p1 = line.getKey();
			Point p2 = line.getValue();
			if(p1.y == p2.y)
			{
				for(int i = Math.min(p1.x, p2.x); i <= Math.max(p1.x, p2.x); i++)
					points.merge(new Point(i, p1.y), 1, Integer::sum);
			}
			else if(p1.x == p2.x)
			{
				for(int i = Math.min(p1.y, p2.y); i <= Math.max(p1.y, p2.y); i++)
					points.merge(new Point(p1.x, i), 1, Integer::sum);
			}
		}

		int count = 0;
		for(int visits : points.values())
			if(visits > 1)
				count++;
		lap(count);


		//Part 2
		points = new HashMap<>();
		for(Map.Entry<Point, Point> line : inputLines.entrySet())
		{
			Point p1 = line.getKey();
			Point p2 = line.getValue();
			if(p1.y == p2.y)
			{
				for(int i = Math.min(p1.x, p2.x); i <= Math.max(p1.x, p2.x); i++)
					points.merge(new Point(i, p1.y), 1, Integer::sum);
			}
			else if(p1.x == p2.x)
			{
				for(int i = Math.min(p1.y, p2.y); i <= Math.max(p1.y, p2.y); i++)
					points.merge(new Point(p1.x, i), 1, Integer::sum);
			}
			else
			{
				int xDir = p2.x - p1.x > 0 ? 1 : -1;
				int yDir = p2.y - p1.y > 0 ? 1 : -1;
				for(int i = 0; i <= Math.abs(p2.x - p1.x); i++)
					points.merge(new Point(p1.x + (xDir * i), p1.y + (yDir * i)), 1, Integer::sum);
			}
		}

		count = 0;
		for(int visits : points.values())
			if(visits > 1)
				count++;

		lap(count);
	}

	private static class Point
	{
		public int x;
		public int y;

		public Point(String[] vals)
		{
			this.x = Integer.parseInt(vals[0]);
			this.y = Integer.parseInt(vals[1]);
		}

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o)
		{
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			Point point = (Point) o;
			return x == point.x && y == point.y;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(x, y);
		}

		@Override
		public String toString()
		{
			return "Point{" +
					"x=" + x +
					", y=" + y +
					'}';
		}
	}
}

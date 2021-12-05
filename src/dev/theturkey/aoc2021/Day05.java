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
			Point p1 = new Point(Integer.parseInt(point1[0]), Integer.parseInt(point1[1]));
			Point p2 = new Point(Integer.parseInt(point2[0]), Integer.parseInt(point2[1]));
			inputLines.put(p1, p2);
		}

		//Part 1
		Map<Point, Integer> points = new HashMap<>();
		for(Map.Entry<Point, Point> line : inputLines.entrySet())
		{
			Point p1 = line.getKey();
			Point p2 = line.getValue();
			if(p1.y == p2.y)
			{
				int xMin = Math.min(p1.x, p2.x);
				for(int i = xMin; i <= Math.max(p1.x, p2.x); i++)
				{
					Point p = new Point(i, p1.y);
					points.put(p, points.getOrDefault(p, 0) + 1);
				}
			}
			else if(p1.x == p2.x)
			{
				int yMin = Math.min(p1.y, p2.y);
				for(int i = yMin; i <= Math.max(p1.y, p2.y); i++)
				{
					Point p = new Point(p1.x, i);
					points.put(p, points.getOrDefault(p, 0) + 1);
				}
			}
		}

		int count = 0;
		for(Point p : points.keySet())
		{
			if(points.get(p) > 1)
				count++;
		}
		lap(count);


		//Part 2
		points = new HashMap<>();
		for(Map.Entry<Point, Point> line : inputLines.entrySet())
		{
			Point p1 = line.getKey();
			Point p2 = line.getValue();
			if(p1.y == p2.y)
			{
				int xMin = Math.min(p1.x, p2.x);
				for(int i = xMin; i <= Math.max(p1.x, p2.x); i++)
				{
					Point p = new Point(i, p1.y);
					points.put(p, points.getOrDefault(p, 0) + 1);
				}
			}
			else if(p1.x == p2.x)
			{
				int yMin = Math.min(p1.y, p2.y);
				for(int i = yMin; i <= Math.max(p1.y, p2.y); i++)
				{
					Point p = new Point(p1.x, i);
					points.put(p, points.getOrDefault(p, 0) + 1);
				}
			}
			else
			{
				int steps = Math.abs(p2.x - p1.x);
				int xDir = p2.x - p1.x > 0 ? 1 : -1;
				int yDir = p2.y - p1.y > 0 ? 1 : -1;
				for(int i = 0; i <= steps; i++)
				{
					Point p = new Point(p1.x + (xDir * i), p1.y + (yDir * i));
					points.put(p, points.getOrDefault(p, 0) + 1);
				}
			}
		}

		count = 0;
		for(Point p : points.keySet())
		{
			if(points.get(p) > 1)
				count++;
		}
		lap(count);

	}

	private static class Point
	{
		public int x;
		public int y;

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

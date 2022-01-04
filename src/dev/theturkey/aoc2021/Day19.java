package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day19 extends AOCPuzzle
{
	public Day19()
	{
		super("19");
	}

	@Override
	void solve(List<String> input)
	{
		List<List<Point3>> points = new ArrayList<>();
		int index = 0;
		for(String s : input)
		{
			if(s.startsWith("---"))
			{
				index = Integer.parseInt(s.replace("--- scanner ", "").replace(" ---", "").trim());
				continue;
			}

			if(s.trim().equals(""))
				continue;

			List<Point3> indexPoints;
			if(points.size() <= index)
			{
				indexPoints = new ArrayList<>();
				points.add(index, indexPoints);
			}
			else
			{
				indexPoints = points.get(index);
			}

			indexPoints.add(new Point3(s.split(",")));
		}

		List<Integer> options = new ArrayList<>();
		for(int n = 1; n < 4; n++)
			for(int m = 1; m > -2; m -= 2)
				options.add(n * m);
		List<Transformation> combos = combinations(options, new int[3], 0);


		List<MatchTransform> matchTransforms = new ArrayList<>();

		for(int i = 0; i < points.size(); i++)
		{
			List<Point3> scannerPoints = points.get(i);
			for(int j = 1; j < points.size(); j++)
			{
				if(i == j)
					continue;

				int matching = 0;

				for(Transformation t : combos)
				{
					List<Point3> jPointsTransformed = translatePoints(points.get(j), t);
					for(Point3 jPoint : jPointsTransformed)
					{
						for(Point3 mainPoint : scannerPoints)
						{
							matching = 0;
							Point3 offset = jPoint.sub(mainPoint);

							for(Point3 offsetPoint : this.offsetPoints(jPointsTransformed, offset))
								if(scannerPoints.contains(offsetPoint))
									matching++;

							if(matching >= 12)
							{
								matchTransforms.add(new MatchTransform(i, j, t, offset));
								break;
							}
						}
						if(matching >= 12)
							break;
					}
					if(matching >= 12)
						break;
				}
			}
		}

		Map<Integer, Integer> tree = new HashMap<>();
		for(MatchTransform t : matchTransforms)
			if(t.scannerMatched == 0)
				tree.put(t.scannerRotated, t.scannerMatched);
		while(tree.size() < points.size() - 1)
			for(MatchTransform t : matchTransforms)
				if(tree.containsKey(t.scannerMatched) && !tree.containsKey(t.scannerRotated))
					tree.put(t.scannerRotated, t.scannerMatched);

		Map<Integer, Point3> mapped = new HashMap<>();
		mapped.put(0, new Point3(0, 0, 0));
		Set<Point3> beacons = new HashSet<>(points.get(0));
		for(MatchTransform transform : matchTransforms)
		{
			if(mapped.containsKey(transform.scannerRotated))
				continue;

			Point3 scanner = new Point3(0, 0, 0);
			int from = transform.scannerRotated;
			List<Point3> beaconsToMap = points.get(transform.scannerRotated);
			do
			{
				int dest = tree.get(from);
				for(MatchTransform t : matchTransforms)
				{
					if(t.scannerMatched == dest && t.scannerRotated == from)
					{
						beaconsToMap = transformToScanner(beaconsToMap, t.transformation, t.offset);
						scanner = t.transformation.translate(scanner).sub(t.offset);
						break;
					}
				}
				from = dest;
			} while(from != 0);
			beacons.addAll(beaconsToMap);

			mapped.put(transform.scannerRotated, scanner);
		}

		lap(beacons.size());

		int maxApart = Integer.MIN_VALUE;
		for(int i = 0; i < mapped.size(); i++)
		{
			for(int j = i + 1; j < mapped.size(); j++)
			{
				Point3 iPoint = mapped.get(i);
				Point3 jPoint = mapped.get(j);
				maxApart = Math.max(maxApart, Math.abs(iPoint.x - jPoint.x) + Math.abs(iPoint.y - jPoint.y) + Math.abs(iPoint.z - jPoint.z));
			}
		}

		lap(maxApart);
	}

	public List<Point3> transformToScanner(List<Point3> points, Transformation transformation, Point3 offset)
	{
		List<Point3> beaconsToMap = points;
		beaconsToMap = translatePoints(beaconsToMap, transformation);
		beaconsToMap = offsetPoints(beaconsToMap, offset);
		return beaconsToMap;
	}

	public List<Point3> offsetPoints(List<Point3> points, Point3 offset)
	{
		List<Point3> offsetPoints = new ArrayList<>();
		for(Point3 p : points)
			offsetPoints.add(p.sub(offset));
		return offsetPoints;
	}

	public List<Point3> translatePoints(List<Point3> points, Transformation t)
	{
		List<Point3> translated = new ArrayList<>();
		for(Point3 jp : points)
			translated.add(t.translate(jp));
		return translated;
	}

	public static List<Transformation> combinations(List<Integer> options, int[] arr, int curPos)
	{
		if(curPos == 3)
			return List.of(new Transformation(arr[0], arr[1], arr[2]));

		List<Transformation> combos = new ArrayList<>();
		for(int i = 0; i < options.size(); i++)
		{
			List<Integer> optionsLeft = new ArrayList<>(options);
			Integer removed = optionsLeft.remove(i);
			arr[curPos] = removed;
			Integer other = removed * -1;
			optionsLeft.remove(other);
			combos.addAll(combinations(optionsLeft, arr, curPos + 1));
		}
		return combos;
	}

	private static class MatchTransform
	{
		public int scannerRotated;
		public int scannerMatched;

		public Transformation transformation;
		public Point3 offset;

		public MatchTransform(int scannerMatched, int scannerRotated, Transformation transformation, Point3 offset)
		{
			this.scannerMatched = scannerMatched;
			this.scannerRotated = scannerRotated;
			this.transformation = transformation;
			this.offset = offset;
		}


	}

	private static class Transformation
	{
		public int x;
		public int y;
		public int z;

		public Transformation(int x, int y, int z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Point3 translate(Point3 p)
		{
			int newX = (x < 0 ? -1 : 1) * (Math.abs(x) == 1 ? p.x : (Math.abs(x) == 2 ? p.y : p.z));
			int newY = (y < 0 ? -1 : 1) * (Math.abs(y) == 1 ? p.x : (Math.abs(y) == 2 ? p.y : p.z));
			int newZ = (z < 0 ? -1 : 1) * (Math.abs(z) == 1 ? p.x : (Math.abs(z) == 2 ? p.y : p.z));
			return new Point3(newX, newY, newZ);
		}

		@Override
		public String toString()
		{
			return "Transformation{" +
					"x=" + x +
					", y=" + y +
					", z=" + z +
					'}';
		}
	}
}

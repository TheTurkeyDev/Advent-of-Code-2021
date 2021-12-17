package dev.theturkey.aoc2021;

import java.util.List;

public class Day17 extends AOCPuzzle
{
	public Day17()
	{
		super("17");
	}

	@Override
	void solve(List<String> input)
	{
		String[] parts = input.get(0).replace("target area: x=", "").replace(" y=", "").split(",");
		int minX = Integer.parseInt(parts[0].split("\\.\\.")[0]);
		int maxX = Integer.parseInt(parts[0].split("\\.\\.")[1]);
		int minY = Integer.parseInt(parts[1].split("\\.\\.")[0]);
		int maxY = Integer.parseInt(parts[1].split("\\.\\.")[1]);

		int maxHeight = Integer.MIN_VALUE;
		int hitTarget = 0;
		for(int y = minY; y < 500; y++)
		{
			for(int x = 1; x < maxX + 1; x++)
			{
				int height = shootProbe(x, y, minX, maxX, minY, maxY);
				if(height > maxHeight)
					maxHeight = height;
				if(height > Integer.MIN_VALUE)
					hitTarget++;
			}
		}

		lap(maxHeight);
		lap(hitTarget);
	}

	public int shootProbe(int xVel, int yVel, int targetMinX, int targetMaxX, int targetMinY, int targetMaxY)
	{
		int maxY = Integer.MIN_VALUE;
		int x = 0;
		int y = 0;
		boolean pastTarget = false;
		while(!pastTarget)
		{
			x += xVel;
			y += yVel;
			if(y > maxY)
				maxY = y;

			if(targetMinX <= x && targetMaxX >= x && targetMinY <= y && targetMaxY >= y)
				return maxY;
			else if(targetMinY > y)
				pastTarget = true;

			xVel += Integer.compare(0, xVel);
			yVel -= 1;
		}
		return Integer.MIN_VALUE;
	}
}

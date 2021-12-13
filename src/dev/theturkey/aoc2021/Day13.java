package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends AOCPuzzle
{
	public Day13()
	{
		super("13");
	}

	@Override
	void solve(List<String> input)
	{
		List<Point> dots = new ArrayList<>();
		boolean loadingPoints = true;
		boolean part1 = false;

		for(String s : input)
		{
			if(s.equals(""))
			{
				loadingPoints = false;
				continue;
			}

			if(loadingPoints)
			{
				String[] pos = s.split(",");
				dots.add(new Point(Integer.parseInt(pos[1]), Integer.parseInt(pos[0])));
			}
			else
			{
				String[] parts = s.replace("fold along ", "").split("=");
				int line = Integer.parseInt(parts[1]);
				for(int i = dots.size() - 1; i >= 0; i--)
				{
					Point p = dots.get(i);
					if(parts[0].equals("x"))
					{
						if(p.col > line)
						{
							int newX = line - (p.col - line);
							if(dots.contains(new Point(p.row, newX)))
								dots.remove(i);
							else
								p.col = line - (p.col - line);
						}
					}
					else
					{
						if(p.row > line)
						{
							int newY = line - (p.row - line);
							if(dots.contains(new Point(newY, p.col)))
								dots.remove(i);
							else
								p.row = line - (p.row - line);
						}
					}
				}

				if(!part1)
				{
					lap(dots.size());
					part1 = true;
				}
			}
		}

		int maxCol = -1;
		int maxRow = -1;

		for(Point p : dots)
		{
			if(p.row > maxRow)
				maxRow = p.row;
			if(p.col > maxCol)
				maxCol = p.col;
		}

		for(int row = 0; row <= maxRow; row++)
		{
			for(int col = 0; col <= maxCol; col++)
				System.out.print(dots.contains(new Point(row, col)) ? "█" : " ");
			System.out.println();
		}

		lap("Above");
	}
}

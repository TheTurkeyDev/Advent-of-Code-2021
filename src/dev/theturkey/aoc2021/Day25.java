package dev.theturkey.aoc2021;

import java.util.List;

public class Day25 extends AOCPuzzle
{
	public Day25()
	{
		super("25");
	}

	@Override
	void solve(List<String> input)
	{
		int[][] cucumbers = new int[input.size()][input.get(0).length()];
		for(int row = 0; row < input.size(); row++)
		{
			String rowStr = input.get(row);
			for(int col = 0; col < rowStr.length(); col++)
			{
				char c = rowStr.charAt(col);
				cucumbers[row][col] = c == '.' ? 0 : (c == '>' ? 1 : 2);
			}
		}

		int moves = 0;
		boolean movement = true;
		while(movement)
		{
			moves++;
			movement = false;
			int[][] copy = new int[cucumbers.length][cucumbers[0].length];

			for(int row = 0; row < cucumbers.length; row++)
				System.arraycopy(cucumbers[row], 0, copy[row], 0, cucumbers[row].length);

			for(int row = 0; row < copy.length; row++)
			{
				for(int col = 0; col < copy[row].length; col++)
				{
					int nextCol = (col + 1) % copy[row].length;
					if(cucumbers[row][col] == 1 && cucumbers[row][nextCol] == 0)
					{
						copy[row][col] = 0;
						copy[row][nextCol] = 1;
						col++;
						movement = true;
					}
				}
			}
			for(int col = 0; col < copy[0].length; col++)
			{
				for(int row = 0; row < copy.length; row++)
				{
					int nextRow = (row + 1) % copy.length;
					if(cucumbers[row][col] == 2 && copy[nextRow][col] == 0)
					{
						copy[row][col] = 0;
						copy[nextRow][col] = 2;
						row++;
						movement = true;
					}
				}
			}
			cucumbers = copy;
		}

		lap(moves);
	}
}

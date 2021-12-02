package dev.theturkey.aoc2021;

import java.util.List;

public class Day02 extends AOCPuzzle
{
	public Day02()
	{
		super("2");
	}

	@Override
	void solve(List<String> input)
	{
		long depth = 0;
		long horizontal = 0;
		for(String s : input)
		{
			String[] instructionParts = s.split(" ");
			switch(instructionParts[0])
			{
				case "up":
					depth -= Integer.parseInt(instructionParts[1]);
					break;
				case "down":
					depth += Integer.parseInt(instructionParts[1]);
					break;
				case "forward":
					horizontal += Integer.parseInt(instructionParts[1]);
					break;
			}
		}

		lap(depth * horizontal);

		depth = 0;
		horizontal = 0;
		long aim = 0;
		for(String s : input)
		{
			String[] instructionParts = s.split(" ");
			switch(instructionParts[0])
			{
				case "up":
					aim -= Integer.parseInt(instructionParts[1]);
					break;
				case "down":
					aim += Integer.parseInt(instructionParts[1]);
					break;
				case "forward":
					horizontal += Integer.parseInt(instructionParts[1]);
					depth += aim * Integer.parseInt(instructionParts[1]);
					break;
			}
		}

		lap(depth * horizontal);
	}
}

package dev.theturkey.aoc2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AOCPuzzle
{
	private int part = 1;
	private long timerStart;

	public AOCPuzzle(String day)
	{
		File file = new File("res/day" + day + ".txt");
		if(!file.exists())
		{
			solve(new ArrayList<>());
			return;
		}

		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(file));
		} catch(FileNotFoundException e)
		{
			System.err.println("File not found!!");
			solve(new ArrayList<>());
			return;
		}

		List<String> inputLines = new ArrayList<>();
		try
		{
			String line;
			while((line = reader.readLine()) != null)
				inputLines.add(line);

			reader.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		timerStart = System.nanoTime();
		solve(inputLines);
	}

	abstract void solve(List<String> input);

	public void lap(long answer)
	{
		lap(String.valueOf(answer));
	}

	public void lap(String answer)
	{
		long timeSpent = (System.nanoTime() - timerStart) / 1000;
		System.out.println("Part " + part + ": " + answer + ", Duration: " + timeToString(timeSpent));
		timerStart = System.nanoTime();
		part++;
	}

	public String timeToString(long timeSpent)
	{
		if(timeSpent < 1000)
			return timeSpent + "µs";
		if(timeSpent < 1000000)
			return (timeSpent / 1000.0) + "ms";
		return (timeSpent / 1000000.0) + "s";
	}

	public List<Integer> convertToInts(List<String> input)
	{
		List<Integer> ints = new ArrayList<>();
		for(String s : input)
			ints.add(Integer.parseInt(s));
		return ints;
	}

	public List<Long> convertToLongs(List<String> input)
	{
		List<Long> ints = new ArrayList<>();
		for(String s : input)
			ints.add(Long.parseLong(s));
		return ints;
	}

}
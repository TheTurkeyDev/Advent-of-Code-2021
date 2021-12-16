package dev.theturkey.aoc2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day16 extends AOCPuzzle
{
	public Day16()
	{
		super("16");
	}

	public String binary;
	public int cursor;

	public long part1;

	@Override
	void solve(List<String> input)
	{
		String hex = input.get(0);
		StringBuilder builder = new StringBuilder();
		for(char c : hex.toCharArray())
		{
			String binary = new BigInteger(String.valueOf(c), 16).toString(2);
			builder.append("0".repeat(4 - binary.length())).append(binary);
		}
		binary = builder.toString();
		cursor = 0;
		part1 = 0;
		long value = parsePacket();
		lap(part1);
		lap(value);
	}

	public long parsePacket()
	{
		int version = readPartInt(3);
		part1 += version;

		switch(readPartInt(3))
		{
			case 0:
				return parseSum();
			case 1:
				return parseProduct();
			case 2:
				return parseMin();
			case 3:
				return parseMax();
			case 4:
				return parseLiteral();
			case 5:
				return parseGreaterThan();
			case 6:
				return parseLessThan();
			case 7:
				return parseEqualTo();
		}
		return 0;
	}

	public long parseSum()
	{
		long toReturn = 0;

		for(long l : parseOP())
			toReturn += l;

		return toReturn;
	}

	public long parseProduct()
	{
		long toReturn = 1;

		for(long l : parseOP())
			toReturn *= l;

		return toReturn;
	}

	public long parseMin()
	{
		long min = Long.MAX_VALUE;

		for(long l : parseOP())
			min = Math.min(l, min);

		return min;
	}

	public long parseMax()
	{
		long max = Long.MIN_VALUE;

		for(long l : parseOP())
			max = Math.max(l, max);

		return max;
	}

	public long parseGreaterThan()
	{
		List<Long> nums = parseOP();
		return nums.get(0) > nums.get(1) ? 1 : 0;
	}

	public long parseLessThan()
	{
		List<Long> nums = parseOP();
		return nums.get(0) < nums.get(1) ? 1 : 0;
	}

	public long parseEqualTo()
	{
		List<Long> nums = parseOP();
		return Objects.equals(nums.get(0), nums.get(1)) ? 1 : 0;
	}

	public List<Long> parseOP()
	{
		List<Long> nums = new ArrayList<>();
		if(readPartInt(1) == 0)
		{
			int length = readPartInt(15);
			int startCursor = cursor;
			while(cursor < startCursor + length)
				nums.add(parsePacket());
		}
		else
		{
			int NumPackets = readPartInt(11);
			for(int p = 0; p < NumPackets; p++)
				nums.add(parsePacket());
		}

		return nums;
	}

	public long parseLiteral()
	{
		int start = readPartInt(1);
		StringBuilder builder = new StringBuilder();
		while(start == 1)
		{
			builder.append(readPart(4));
			start = readPartInt(1);
		}
		builder.append(readPart(4));
		return Long.parseLong(builder.toString(), 2);
	}

	public String readPart(int length)
	{
		String s = binary.substring(cursor, cursor + length);
		cursor += length;
		return s;
	}

	public int readPartInt(int length)
	{
		return Integer.parseInt(readPart(length), 2);
	}
}

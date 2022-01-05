package dev.theturkey.aoc2021;

import java.util.List;

public class Day24 extends AOCPuzzle
{
	public Day24()
	{
		super("24");
	}

	@Override
	void solve(List<String> input)
	{
		System.out.println("===== Translated Code =====");
		System.out.println("int w = 0;");
		System.out.println("int x = 0;");
		System.out.println("int y = 0;");
		System.out.println("int z = 0;");

		for(String line : input)
		{
			String[] parts = line.split(" ");
			switch(parts[0])
			{
				case "inp":
					System.out.println(parts[1] + " = ?;");
					break;
				case "add":
					System.out.println(parts[1] + " += " + parts[2] + ";");
					break;
				case "mul":
					System.out.println(parts[1] + " *= " + parts[2] + ";");
					break;
				case "div":
					System.out.println(parts[1] + " /= " + parts[2] + ";");
					break;
				case "mod":
					System.out.println(parts[1] + " %= " + parts[2] + ";");
					break;
				case "eql":
					System.out.println(parts[1] + " = " + parts[1] + "==" + parts[2] + "? 1 : 0;");
					break;
			}
		}

		System.out.println("===== Solutions =====");
		int[] numbers = new int[]{1, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
		while(!isDone(numbers))
		{
			if(theProgram(numbers))
				break;
			dec(numbers);
		}
		lap(getNumberFromArray(numbers));

		numbers = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 8, 9};
		while(!isDone(numbers))
		{
			if(theProgram(numbers))
				break;
			inc(numbers);
		}
		lap(getNumberFromArray(numbers));
	}

	public long getNumberFromArray(int[] numbers)
	{
		long finalNum = 0;
		for(int n : numbers)
			finalNum = finalNum * 10 + n;
		return finalNum;
	}

	public boolean isDone(int[] number)
	{
		for(int n : number)
			if(n != 1)
				return false;
		return true;
	}

	public void dec(int[] number)
	{
		for(int i = number.length - 3; i >= 2; i--)
		{
			if((number[i]--) == 0)
				number[i] = 9;
			else
				return;
		}
	}

	public void inc(int[] number)
	{
		for(int i = number.length - 3; i >= 2; i--)
		{
			if((number[i]++) == 10)
				number[i] = 1;
			else
				return;
		}
	}

	// Here's the very simplified program
	public boolean theProgram(int[] numbers)
	{
		int z = ((numbers[0] + 13) * 26) + numbers[1] + 10;

		if(numbers[2] - 6 != numbers[3])
			z = (z * 26) + (numbers[3] + 14);

		if(numbers[4] + 5 != numbers[5])
			z = (z * 26) + (numbers[5] + 15);

		z = (z * 26) + numbers[6] + 4;
		z = (z * 26) + numbers[7] + 11;

		if(numbers[8] - 5 != numbers[9])
			z = (z * 26) + (numbers[9] + 15);

		int x = z % 26;
		z /= 26;

		if(x - 10 != numbers[10])
			z = (z * 26) + (numbers[10] + 12);

		x = z % 26;
		z /= 26;

		if(x - 12 != numbers[11])
			z = (z * (26) + (numbers[11] + 8));

		x = z % 26;
		z /= 26;
		x = x - 3 != numbers[12] ? 1 : 0; // x = 4,5,6,7,8,9,10,11,12
		if(x != 0)
			return false;

		x = z % 26; // numbers[0]
		z /= 26;
		if(z != 0)
			return false;
		return x - 5 == numbers[13]; // x = 6,7,8,9,10,11,12,13,14

		// Number Rules
		// n0 + 8 = n13 -> largest & smallest are both 1 & 9
		// n1 + 7 = n12 -> largest = 2 & 9, smallest = 1 & 8

		//12__________99 -> largest
		//11__________89 -> smallest
	}

	//12394998949199
	//12934998949199
}

package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day03 extends AOCPuzzle
{
	public Day03()
	{
		super("3");
	}

	@Override
	void solve(List<String> input)
	{
		//PART 1
		int[] ones = new int[input.get(0).length()];
		int[] zeros = new int[input.get(0).length()];
		for(String s : input)
		{
			for(int i = 0; i < s.length(); i++)
			{
				if(s.charAt(i) == '0')
					zeros[i]++;
				else
					ones[i]++;
			}
		}

		long gamma = 0;
		long epsilon = 0;
		for(int i = 0; i < ones.length; i++)
		{
			double inc = Math.pow(2, ones.length - (i + 1));
			if(ones[i] > zeros[i])
				gamma += inc;
			else
				epsilon += inc;
		}

		lap(gamma * epsilon);


		//PART 2
		List<String> oxygenNums = new ArrayList<>(input);
		List<String> co2Nums = new ArrayList<>(input);

		int index = 0;
		while(oxygenNums.size() > 1)
		{
			List<int[]> newCalc = calc(oxygenNums);
			ones = newCalc.get(0);
			zeros = newCalc.get(1);
			for(int i = oxygenNums.size() - 1; i >= 0; i--)
			{
				String oxyNum = oxygenNums.get(i);
				if(ones[index] >= zeros[index] && oxyNum.charAt(index) == '0')
					oxygenNums.remove(i);
				else if(ones[index] < zeros[index] && oxyNum.charAt(index) == '1')
					oxygenNums.remove(i);
			}
			index++;
		}

		index = 0;
		while(co2Nums.size() > 1)
		{
			List<int[]> newCalc = calc(co2Nums);
			ones = newCalc.get(0);
			zeros = newCalc.get(1);
			for(int i = co2Nums.size() - 1; i >= 0; i--)
			{
				String co2Num = co2Nums.get(i);
				if(ones[index] >= zeros[index] && co2Num.charAt(index) == '1')
					co2Nums.remove(i);
				else if(ones[index] < zeros[index] && co2Num.charAt(index) == '0')
					co2Nums.remove(i);
			}
			index++;
		}

		long oxyNum = Integer.parseInt(oxygenNums.get(0), 2);
		long co2Num = Integer.parseInt(co2Nums.get(0), 2);
		lap(oxyNum * co2Num);
	}

	public static List<int[]> calc(List<String> input)
	{
		int[] ones = new int[input.get(0).length()];
		int[] zeros = new int[input.get(0).length()];
		for(String s : input)
		{
			for(int i = 0; i < s.length(); i++)
			{
				if(s.charAt(i) == '0')
					zeros[i]++;
				else
					ones[i]++;
			}
		}

		return Arrays.asList(ones, zeros);
	}
}

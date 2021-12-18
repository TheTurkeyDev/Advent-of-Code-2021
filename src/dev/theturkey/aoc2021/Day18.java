package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day18 extends AOCPuzzle
{
	public Day18()
	{
		super("18");
	}

	@Override
	void solve(List<String> input)
	{
		SnailNumber solution = null;

		for(String s : input)
		{
			List<Character> chars = new ArrayList<>();
			for(char c : s.toCharArray())
				chars.add(c);
			SnailNumber num = SnailNumber.parse(chars);
			if(solution == null)
				solution = num;
			else
				solution = solution.add(num);

			boolean complete = false;
			while(!complete)
			{
				complete = true;
				if(explode(solution, 0))
				{
					complete = false;
					continue;
				}

				if(split(solution))
					complete = false;
			}
		}

		if(solution != null)
			lap(solution.getMagnitude());

		int maxVal = Integer.MIN_VALUE;
		for(int i = 0; i < input.size(); i++)
		{
			for(int j = i + 1; j < input.size(); j++)
			{
				List<Character> chars = new ArrayList<>();
				for(char c : input.get(i).toCharArray())
					chars.add(c);
				SnailNumber num1 = SnailNumber.parse(chars);
				chars = new ArrayList<>();
				for(char c : input.get(j).toCharArray())
					chars.add(c);
				SnailNumber num2 = SnailNumber.parse(chars);

				SnailNumber sum = num1.add(num2);

				boolean complete = false;
				while(!complete)
				{
					complete = true;
					if(explode(sum, 0))
					{
						complete = false;
						continue;
					}

					if(split(sum))
						complete = false;
				}

				int magnitude = sum.getMagnitude();
				if(magnitude > maxVal)
					maxVal = magnitude;
			}
		}

		lap(maxVal);
	}

	public boolean split(SnailNumber snailNumber)
	{
		if(snailNumber instanceof SnailNumberPair)
		{
			SnailNumberPair pair = (SnailNumberPair) snailNumber;
			if(split(pair.left))
				return true;
			return split(pair.right);
		}
		else
		{
			SnailNumberPrimitive primitive = (SnailNumberPrimitive) snailNumber;
			if(primitive.number > 9)
			{
				SnailNumberPrimitive leftPrimitive = new SnailNumberPrimitive(null, primitive.number / 2);
				SnailNumberPrimitive rightPrimitive = new SnailNumberPrimitive(null, primitive.number - leftPrimitive.number);
				SnailNumberPair pair = new SnailNumberPair(primitive.parent, leftPrimitive, rightPrimitive);

				if(((SnailNumberPair) primitive.parent).left.equals(snailNumber))
					((SnailNumberPair) primitive.parent).left = pair;
				if(((SnailNumberPair) primitive.parent).right.equals(snailNumber))
					((SnailNumberPair) primitive.parent).right = pair;

				leftPrimitive.parent = pair;
				rightPrimitive.parent = pair;
				return true;
			}
		}
		return false;
	}

	public boolean explode(SnailNumber snailNumber, int depth)
	{
		if(snailNumber instanceof SnailNumberPair)
		{
			SnailNumberPair pair = (SnailNumberPair) snailNumber;
			if(depth >= 4 && pair.left instanceof SnailNumberPrimitive && pair.right instanceof SnailNumberPrimitive)
			{
				SnailNumber ToAdd = pair;
				int step = 0;
				while(step < 2)
				{
					if(step == 0)
					{
						if(ToAdd.parent == null)
						{
							break;
						}
						else if(((SnailNumberPair) ToAdd.parent).left.equals(ToAdd))
						{
							ToAdd = ToAdd.parent;
						}
						else
						{
							step++;
							ToAdd = ((SnailNumberPair) ToAdd.parent).left;
						}

					}
					else if(step == 1)
					{
						if(ToAdd instanceof SnailNumberPrimitive)
						{
							((SnailNumberPrimitive) ToAdd).addNum((SnailNumberPrimitive) pair.left);
							step++;
						}
						else
						{
							ToAdd = ((SnailNumberPair) ToAdd).right;
						}
					}
				}

				ToAdd = pair;
				step = 0;
				while(step < 2)
				{
					if(step == 0)
					{
						if(ToAdd.parent == null)
						{
							break;
						}
						if(((SnailNumberPair) ToAdd.parent).right.equals(ToAdd))
						{
							ToAdd = ToAdd.parent;
						}
						else
						{
							step++;
							ToAdd = ((SnailNumberPair) ToAdd.parent).right;
						}

					}
					else if(step == 1)
					{
						if(ToAdd instanceof SnailNumberPrimitive)
						{
							((SnailNumberPrimitive) ToAdd).addNum((SnailNumberPrimitive) pair.right);
							step++;
						}
						else
						{
							ToAdd = ((SnailNumberPair) ToAdd).left;
						}
					}
				}

				((SnailNumberPair) pair.parent).zeroOut(pair);
				return true;
			}

			if(explode(pair.left, depth + 1))
				return true;
			return explode(pair.right, depth + 1);
		}
		return false;
	}

	private static abstract class SnailNumber
	{
		public SnailNumber parent;

		public SnailNumber(SnailNumber parent)
		{
			this.parent = parent;
		}

		public static SnailNumber parse(List<Character> chars)
		{
			if(chars.get(0) == '[')
			{
				chars.remove(0); // Remove [
				SnailNumber left = SnailNumber.parse(chars);
				chars.remove(0); // Remove ,
				SnailNumber right = SnailNumber.parse(chars);
				chars.remove(0); // Remove ]
				SnailNumberPair snailNumberPair = new SnailNumberPair(null, left, right);
				left.parent = snailNumberPair;
				right.parent = snailNumberPair;
				return snailNumberPair;
			}
			else
			{
				StringBuilder number = new StringBuilder();
				char c = chars.remove(0);
				boolean isNumber = true;
				while(isNumber)
				{
					number.append(c);
					c = chars.remove(0);
					isNumber = c >= '0' && c <= '9';
				}
				chars.add(0, c);

				return new SnailNumberPrimitive(null, Integer.parseInt(number.toString()));
			}
		}

		public SnailNumber add(SnailNumber toAdd)
		{
			SnailNumberPair newSnailNumber = new SnailNumberPair(null, this, toAdd);
			this.parent = newSnailNumber;
			toAdd.parent = newSnailNumber;
			return newSnailNumber;
		}

		public abstract int getMagnitude();
	}

	private static class SnailNumberPair extends SnailNumber
	{
		public SnailNumber left;
		public SnailNumber right;

		public SnailNumberPair(SnailNumber parent, SnailNumber left, SnailNumber right)
		{
			super(parent);
			this.left = left;
			this.right = right;
		}

		public void zeroOut(SnailNumber snailNumber)
		{
			if(left.equals(snailNumber))
				left = new SnailNumberPrimitive(this, 0);
			if(right.equals(snailNumber))
				right = new SnailNumberPrimitive(this, 0);
		}

		public int getMagnitude()
		{
			return 3 * left.getMagnitude() + 2 * right.getMagnitude();
		}

		@Override
		public String toString()
		{
			return "[" + left + "," + right + "]";
		}
	}

	private static class SnailNumberPrimitive extends SnailNumber
	{
		public int number;

		public SnailNumberPrimitive(SnailNumber parent, int number)
		{
			super(parent);
			this.number = number;
		}

		public void addNum(SnailNumberPrimitive primitive)
		{
			this.number += primitive.number;
		}

		public int getMagnitude()
		{
			return number;
		}

		@Override
		public String toString()
		{
			return String.valueOf(number);
		}
	}
}

package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends AOCPuzzle
{
	public Day10()
	{
		super("10");
	}

	@Override
	void solve(List<String> input)
	{
		//PART 1
		long score = 0;
		for(int i = input.size() - 1; i >= 0; i--)
		{
			// It's a stack, not a queue
			List<Character> stack = new ArrayList<>();
			for(char c : input.get(i).toCharArray())
			{
				boolean exit = false;
				switch(c)
				{
					case '(':
					case '[':
					case '{':
					case '<':
						stack.add(c);
						break;
					case ')':
						if(stack.remove(stack.size() - 1) != '(')
						{
							score += 3;
							exit = true;
						}
						break;
					case ']':
						if(stack.remove(stack.size() - 1) != '[')
						{
							score += 57;
							exit = true;
						}
						break;
					case '}':
						if(stack.remove(stack.size() - 1) != '{')
						{
							score += 1197;
							exit = true;
						}
						break;
					case '>':
						if(stack.remove(stack.size() - 1) != '<')
						{
							score += 25137;
							exit = true;
						}
						break;
				}

				if(exit)
				{
					input.remove(i);
					break;
				}
			}
		}
		lap(score);

		//PART 2
		List<Long> scores = new ArrayList<>();
		for(int i = input.size() - 1; i >= 0; i--)
		{
			List<Character> stack = new ArrayList<>();
			for(char c : input.get(i).toCharArray())
			{
				switch(c)
				{
					case '(':
					case '[':
					case '{':
					case '<':
						stack.add(c);
						break;
					case ')':
					case ']':
					case '}':
					case '>':
						stack.remove(stack.size() - 1);
						break;
				}
			}

			score = 0;
			while(stack.size() > 0)
			{
				char c = stack.remove(stack.size() - 1);
				score *= 5;
				switch(c)
				{
					case '(':
						score += 1;
						break;
					case '[':
						score += 2;
						break;
					case '{':
						score += 3;
						break;
					case '<':
						score += 4;
						break;
				}
			}
			scores.add(score);
		}
		scores.sort(Long::compareTo);
		lap(scores.get(scores.size() / 2));
	}
}

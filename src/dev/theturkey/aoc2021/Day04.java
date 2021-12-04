package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day04 extends AOCPuzzle
{
	public Day04()
	{
		super("4");
	}

	@Override
	void solve(List<String> input)
	{
		String[] randNums = input.get(0).split(",");

		//PART 1
		List<int[][]> boards = genBoards(input);

		for(String numStr : randNums)
		{
			int num = Integer.parseInt(numStr);
			markBoards(boards, num);

			//Check winning board
			boolean done = false;
			for(int[][] board : boards)
			{
				if(isBoardWon(board))
				{
					long leftOver = calcLeftover(board);
					lap(leftOver * num);
					done = true;
					break;
				}
			}

			if(done)
				break;
		}

		//PART 2
		boards = genBoards(input);

		for(String numStr : randNums)
		{
			int num = Integer.parseInt(numStr);
			markBoards(boards, num);

			//Check winning board
			for(int k = boards.size() - 1; k >= 0; k--)
			{
				if(isBoardWon(boards.get(k)))
				{
					if(boards.size() == 1)
					{
						long leftOver = calcLeftover(boards.get(0));
						lap(leftOver * num);
						return;
					}
					boards.remove(k);
				}
			}
		}
	}

	public long calcLeftover(int[][] board)
	{
		long leftOver = 0;
		for(int[] row : board)
			for(int col : row)
				if(col != -1)
					leftOver += col;
		return leftOver;
	}

	public void markBoards(List<int[][]> boards, int num)
	{
		for(int[][] board : boards)
			for(int row = 0; row < board.length; row++)
				for(int col = 0; col < board[row].length; col++)
					if(board[row][col] == num)
						board[row][col] = -1;
	}

	public List<int[][]> genBoards(List<String> input)
	{
		List<int[][]> boards = new ArrayList<>();

		int firstLine = 2;
		int currentLine;

		for(int i = 0; i < 5; i++)
		{
			currentLine = firstLine + i;
			while(currentLine < input.size())
			{
				int[][] board;
				if(i == 0)
				{
					board = new int[5][5];
					boards.add(board);
				}
				else
				{
					board = boards.get((currentLine - i - firstLine) / 6);
				}

				String line = input.get(currentLine);

				for(int j = 0; j < 5; j++)
				{
					int num = Integer.parseInt(line.substring(j * 3, j * 3 + 2).strip());
					board[i][j] = num;
				}

				currentLine += 6;
			}
		}

		return boards;
	}

	public boolean isBoardWon(int[][] board)
	{
		boolean won;
		for(int row = 0; row < 5; row++)
		{
			won = true;
			for(int col = 0; col < 5; col++)
			{
				if(board[row][col] != -1)
				{
					won = false;
					break;
				}
			}

			if(won)
				return true;
		}

		for(int col = 0; col < 5; col++)
		{
			won = true;
			for(int row = 0; row < 5; row++)
			{
				if(board[row][col] != -1)
				{
					won = false;
					break;
				}
			}

			if(won)
				return true;
		}

		if(board[0][0] == -1 && board[1][1] == -1 && board[2][2] == -1 && board[3][3] == -1 && board[4][4] == -1)
			return true;

		return board[0][4] == -1 && board[1][3] == -1 && board[2][2] == -1 && board[3][1] == -1 && board[4][0] == -1;
	}
}

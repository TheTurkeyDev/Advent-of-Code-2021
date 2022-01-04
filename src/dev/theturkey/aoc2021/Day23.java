package dev.theturkey.aoc2021;

import java.util.List;

public class Day23 extends AOCPuzzle
{
	public Day23()
	{
		super("23");
	}

	private static final int[] costs = new int[]{1, 10, 100, 1000};

	@Override
	void solve(List<String> input)
	{
		int[] hallway = new int[11];
		int[][] rooms = new int[4][2];

		for(int i = 0; i < 4; i++)
		{
			int charVal = 3 + (i * 2);
			rooms[i][0] = (input.get(2).charAt(charVal) - 'A') + 1;
			rooms[i][1] = (input.get(5).charAt(charVal) - 'A') + 1;
		}

		long energy = play(hallway, rooms, 0);
		lap(energy);

		hallway = new int[11];
		rooms = new int[4][4];

		for(int i = 0; i < 4; i++)
		{
			int charVal = 3 + (i * 2);
			rooms[i][0] = (input.get(2).charAt(charVal) - 'A') + 1;
			rooms[i][1] = (input.get(3).charAt(charVal) - 'A') + 1;
			rooms[i][2] = (input.get(4).charAt(charVal) - 'A') + 1;
			rooms[i][3] = (input.get(5).charAt(charVal) - 'A') + 1;
		}

		energy = play(hallway, rooms, 0);
		lap(energy);
	}

	private long play(int[] hallway, int[][] rooms, int depth)
	{
		if(isDone(rooms))
			return 0;
		long energyUsed = Integer.MAX_VALUE;
		for(int room = 0; room < rooms.length; room++)
		{
			for(int pos = 0; pos < rooms[room].length; pos++)
			{
				if(rooms[room][pos] == 0)
					continue;
				if(!aboveRoomsClear(rooms[room], pos))
					continue;
				if(rooms[room][pos] == room + 1 && belowRoomsSet(rooms[room], pos, room + 1))
					continue;

				int piece = rooms[room][pos];
				int hallStart = getHallPosForRoom(room);
				for(int hallPos = 0; hallPos < hallway.length; hallPos++)
				{
					if(hallway[hallPos] != 0)
						continue;
					if(hallPos == 2 || hallPos == 4 || hallPos == 6 || hallPos == 8)
						continue;

					if(canMoveTo(hallStart, hallPos, hallway))
					{
						hallway[hallPos] = piece;
						rooms[room][pos] = 0;
						int moveCost = (Math.abs(hallPos - hallStart) + (pos + 1)) * costs[piece - 1];
						long energyCost = play(hallway, rooms, depth + 1);
						energyUsed = Math.min(energyUsed, energyCost + moveCost);
						hallway[hallPos] = 0;
						rooms[room][pos] = piece;
					}
				}
			}
		}

		for(int hallPos = 0; hallPos < hallway.length; hallPos++)
		{
			if(hallway[hallPos] == 0)
				continue;

			int piece = hallway[hallPos];
			int roomNum = piece - 1;
			int toGoTo = getHallPosForRoom(roomNum);

			int roomPos = 0;
			boolean roomSet = true;
			for(int i = roomPos; i < rooms[roomNum].length; i++)
			{
				int val = rooms[roomNum][i];
				if(val == 0)
					roomPos = i;
				else if(val != piece)
					roomSet = false;
			}
			if(roomSet && canMoveTo(hallPos, toGoTo, hallway))
			{
				rooms[roomNum][roomPos] = piece;
				hallway[hallPos] = 0;
				int moveCost = (Math.abs(hallPos - toGoTo) + (roomPos + 1)) * costs[piece - 1];
				long energyCost = play(hallway, rooms, depth + 1);
				energyUsed = Math.min(energyUsed, energyCost + moveCost);
				hallway[hallPos] = piece;
				rooms[roomNum][roomPos] = 0;
			}
		}

		return energyUsed;
	}

	public boolean aboveRoomsClear(int[] room, int roomPos)
	{
		for(int i = 0; i < roomPos; i++)
			if(room[i] != 0)
				return false;
		return true;
	}

	public boolean belowRoomsSet(int[] room, int roomPos, int roomVal)
	{
		for(int i = roomPos + 1; i < room.length; i++)
			if(room[i] != roomVal)
				return false;
		return true;
	}

	public int getHallPosForRoom(int hall)
	{
		return 2 + (hall * 2);
	}

	public boolean canMoveTo(int from, int to, int[] hallway)
	{
		if(to > from)
		{
			for(int i = from + 1; i <= to; i++)
				if(hallway[i] != 0)
					return false;
		}
		else
		{
			for(int i = from - 1; i >= to; i--)
				if(hallway[i] != 0)
					return false;
		}
		return true;
	}

	public boolean isDone(int[][] rooms)
	{
		for(int room = 0; room < rooms.length; room++)
			for(int pos = 0; pos < rooms[room].length; pos++)
				if(rooms[room][pos] != room + 1)
					return false;
		return true;
	}
}

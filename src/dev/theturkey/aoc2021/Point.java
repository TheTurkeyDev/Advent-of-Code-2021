package dev.theturkey.aoc2021;

import java.util.Objects;

public class Point
{
	public int row;
	public int col;

	public Point(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Point point = (Point) o;
		return row == point.row && col == point.col;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(row, col);
	}

	@Override
	public String toString()
	{
		return "Point{" +
				"row=" + row +
				", col=" + col +
				'}';
	}
}
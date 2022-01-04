package dev.theturkey.aoc2021;

import java.util.Objects;

public class Point3
{
	public int x;
	public int y;
	public int z;

	public Point3(String[] values)
	{
		this.x = Integer.parseInt(values[0]);
		this.y = Integer.parseInt(values[1]);
		this.z = Integer.parseInt(values[2]);
	}

	public Point3(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3 sub(Point3 p)
	{
		return new Point3(this.x - p.x, this.y - p.y, this.z - p.z);
	}

	public Point3 add(Point3 p)
	{
		return new Point3(this.x + p.x, this.y + p.y, this.z + p.z);
	}

	public Point3 invert()
	{
		return new Point3(-this.x, -this.y, -this.z);
	}


	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Point3 point = (Point3) o;
		return x == point.x && y == point.y && point.z == z;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}

	@Override
	public String toString()
	{
		return "Point3{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
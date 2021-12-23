package dev.theturkey.aoc2021;

import java.util.*;

public class Day22 extends AOCPuzzle {
    public Day22() {
        super("22");
    }

    @Override
    void solve(List<String> input) {
        List<Cube> cubes = new ArrayList<>();

        for (String s : input) {
            String[] parts = s.trim().split(" ");
            boolean on = parts[0].equals("on");
            String[] coordParts = parts[1].split(",");
            int[] xVals = minMax(coordParts[0].replace("x=", "").split("\\.\\."));
            int[] yVals = minMax(coordParts[1].replace("y=", "").split("\\.\\."));
            int[] zVals = minMax(coordParts[2].replace("z=", "").split("\\.\\."));
            Cube newCube = new Cube(xVals[0], xVals[1], yVals[0], yVals[1], zVals[0], zVals[1]);
            for (int i = cubes.size() - 1; i >= 0; i--) {
                Cube c = cubes.get(i);
                if (c.overlapped(newCube)) {
                    cubes.remove(i);
                    cubes.addAll(c.split(newCube));
                }
            }

            if (on)
                cubes.add(newCube);
        }

        long lit = 0;
        for (Cube c : cubes) {
            lit += c.getVolume();
        }
        lap(lit);
    }

    private int[] minMax(String[] nums) {
        int num1 = Integer.parseInt(nums[0]);
        int num2 = Integer.parseInt(nums[1]);
        return new int[]{Math.min(num1, num2), Math.max(num1, num2)};
    }

    private static class Cube {
        public int xMin;
        public int xMax;
        public int yMin;
        public int yMax;
        public int zMin;
        public int zMax;

        public Cube(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.zMin = zMin;
            this.zMax = zMax;
        }

        public boolean overlapped(Cube c) {
            return (this.xMin <= c.xMax && this.xMax >= c.xMin) &&
                    (this.yMin <= c.yMax && this.yMax >= c.yMin) &&
                    (this.zMin <= c.zMax && this.zMax >= c.zMin);
        }

        public List<Cube> split(Cube c) {
            List<Cube> cubes = new ArrayList<>();

            if (this.xMin < c.xMin) {
                cubes.add(new Cube(this.xMin, c.xMin - 1, this.yMin, this.yMax, this.zMin, this.zMax));
                this.xMin = c.xMin;
            }
            if (this.xMax > c.xMax) {
                cubes.add(new Cube(c.xMax + 1, this.xMax, this.yMin, this.yMax, this.zMin, this.zMax));
                this.xMax = c.xMax;
            }
            if (this.yMin < c.yMin) {
                cubes.add(new Cube(this.xMin, this.xMax, this.yMin, c.yMin - 1, this.zMin, this.zMax));
                this.yMin = c.yMin;
            }
            if (this.yMax > c.yMax) {
                cubes.add(new Cube(this.xMin, this.xMax, c.yMax + 1, this.yMax, this.zMin, this.zMax));
                this.yMax = c.yMax;
            }
            if (this.zMin < c.zMin)
                cubes.add(new Cube(this.xMin, this.xMax, this.yMin, this.yMax, this.zMin, c.zMin - 1));
            if (this.zMax > c.zMax)
                cubes.add(new Cube(this.xMin, this.xMax, this.yMin, this.yMax, c.zMax + 1, this.zMax));

            return cubes;
        }

        public long getVolume() {
            return (((long) xMax - xMin) + 1) * (((long) yMax - yMin) + 1) * (((long) zMax - zMin) + 1);
        }

        @Override
        public String toString() {
            return "Cube{" + xMin +
                    ", xMax=" + xMax +
                    ", yMin=" + yMin +
                    ", yMax=" + yMax +
                    ", zMin=" + zMin +
                    ", zMax=" + zMax +
                    '}';
        }
    }
}

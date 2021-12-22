package dev.theturkey.aoc2021;

import java.util.*;

public class Day20 extends AOCPuzzle {
    public Day20() {
        super("20");
    }

    @Override
    void solve(List<String> input) {
        String key = input.get(0);
        Map<Point, Boolean> pixels = new HashMap<>();
        int iWidth = input.get(2).length();
        int iHeight = input.size();

        for (int i = 2; i < input.size(); i++) {
            String s = input.get(i);
            for (int j = 0; j < s.length(); j++) {
                Point p = new Point(i, j);
                pixels.put(p, s.charAt(j) == '#');
            }
        }

        int iterations = 50;
        for (int exp = 1; exp < iterations + 1; exp++) {
            Map<Point, Boolean> pixelsUpdate = new HashMap<>();
            for (int row = -exp; row < iHeight + exp; row++) {
                for (int col = -exp; col < iWidth + exp; col++) {
                    Point p = new Point(row, col);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int rowOff = -1; rowOff <= 1; rowOff++) {
                        for (int colOff = -1; colOff <= 1; colOff++) {
                            Point p2 = new Point(row + rowOff, col + colOff);
                            stringBuilder.append(pixels.getOrDefault(p2, exp % 2 == 0) ? '1' : '0');
                        }
                    }

                    int val = Integer.parseInt(stringBuilder.toString(), 2);
                    pixelsUpdate.put(p, key.charAt(val) == '#');
                }
            }
            pixels = pixelsUpdate;
            if(exp == 3)
            {
                int lit = 0;
                for (Point p : pixels.keySet())
                    if (pixels.get(p))
                        lit++;

                lap(lit);
            }
        }

        int lit = 0;
        for (Point p : pixels.keySet())
            if (pixels.get(p))
                lit++;

        lap(lit);
    }
}

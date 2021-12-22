package dev.theturkey.aoc2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day19 extends AOCPuzzle {
    public Day19() {
        super("19");
    }

    @Override
    void solve(List<String> input) {
        List<List<Point3>> points = new ArrayList<>();
        int index = 0;
        for (String s : input) {
            if (s.startsWith("---")) {
                index = Integer.parseInt(s.replace("--- scanner ", "").replace(" ---", "").trim());
                continue;
            }

            if (s.trim().equals(""))
                continue;

            List<Point3> indexPoints;
            if (points.size() <= index) {
                indexPoints = new ArrayList<>();
                points.add(index, indexPoints);
            } else {
                indexPoints = points.get(index);
            }

            indexPoints.add(new Point3(s.split(",")));
        }

        Set<Point3> beacons = new HashSet<>(points.get(0));
        for (int i = 0; i < points.size(); i++) {
            List<Point3> scannerPoints = points.get(i);
            for (int j = 1; j < points.size(); j++) {
                if(i == j)
                    continue;
                List<Point3> jPoints = points.get(j);

                int matching = 0;
                for (int zRot = 0; zRot < 4; zRot++) {
                    for (int yRot = 0; yRot < 4; yRot++) {
                        for (int xRot = 0; xRot < 4; xRot++) {
                            for (Point3 mainPoint : scannerPoints) {
                                matching = 0;
                                List<Point3> matches = new ArrayList<>();
                                List<Point3> jPointsOffset = this.offsetPoints(jPoints, mainPoint.sub(jPoints.get(0)));
                                for (Point3 offsetPoint : jPointsOffset)
                                    if (scannerPoints.contains(offsetPoint)) {
                                        matching++;
                                        matches.add(offsetPoint);
                                    }

                                if (matching >= 12) {
                                    beacons.addAll(jPointsOffset);
                                    System.out.println("Match! " + i + " " + j + " " + matching);
                                    //System.out.println(matches);
                                    break;
                                }
                            }
                            if (matching >= 12)
                                break;

                            jPoints = rotateXAxis(jPoints);
                        }

                        if (matching >= 12)
                            break;
                        jPoints = rotateYAxis(jPoints);
                    }
                    if (matching >= 12)
                        break;
                    jPoints = rotateZAxis(jPoints);
                }
            }
        }

        lap(beacons.size());
    }


    public List<Point3> offsetPoints(List<Point3> points, Point3 offset) {
        List<Point3> offsetPoints = new ArrayList<>();
        for (Point3 p : points)
            offsetPoints.add(p.offset(offset));
        return offsetPoints;
    }

    public List<Point3> rotateXAxis(List<Point3> points) {
        List<Point3> toReturn = new ArrayList<>();
        for (Point3 p : points)
            toReturn.add(new Point3(p.x, -p.z, p.y));
        return toReturn;
    }

    public List<Point3> rotateYAxis(List<Point3> points) {
        List<Point3> toReturn = new ArrayList<>();
        for (Point3 p : points)
            toReturn.add(new Point3(-p.z, p.y, p.x));
        return toReturn;
    }

    public List<Point3> rotateZAxis(List<Point3> points) {
        List<Point3> toReturn = new ArrayList<>();
        for (Point3 p : points)
            toReturn.add(new Point3(-p.y, p.x, p.z));
        return toReturn;
    }
}

import util.MultiDimPos;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day22 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var inputs = Util.readStrings();
        //Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static long part2(List<String> inputs) {
        long on = 0;
        List<Cube> cubes = new ArrayList<>();
        for (String input : inputs) {
            var split = input.split(" ");
            boolean isOn = split[0].equals("on");
            var split2 = split[1].split(",");
            int minX = 0, maxX = 0, minY = 0, maxY = 0, minZ = 0, maxZ = 0;
            for (String s : split2) {
                var ss = s.split("=");
                var sss = ss[1].split("\\.\\.");
                if (ss[0].equals("x")) {
                    minX = Integer.parseInt(sss[0]);
                    maxX = Integer.parseInt(sss[1]);
                } else if (ss[0].equals("y")) {
                    minY = Integer.parseInt(sss[0]);
                    maxY = Integer.parseInt(sss[1]);
                } else if (ss[0].equals("z")) {
                    minZ = Integer.parseInt(sss[0]);
                    maxZ = Integer.parseInt(sss[1]);
                }
            }
            cubes.add(new Cube(
                    Math.min(minX, maxX), Math.max(minX, maxX),
                    Math.min(minY, maxY), Math.max(minY, maxY),
                    Math.min(minZ, maxZ), Math.max(minZ, maxZ),
                    isOn
            ));
        }
        return on;
    }

    private static int part1(List<String> inputs) {
        Set<MultiDimPos> on = new HashSet<>();
        for (String input : inputs) {
            var split = input.split(" ");
            boolean isOn = split[0].equals("on");
            var split2 = split[1].split(",");
            int minX = 0, maxX = 0, minY = 0, maxY = 0, minZ = 0, maxZ = 0;
            for (String s : split2) {
                var ss = s.split("=");
                var sss = ss[1].split("\\.\\.");
                if (ss[0].equals("x")) {
                    minX = Integer.parseInt(sss[0]);
                    maxX = Integer.parseInt(sss[1]);
                } else if (ss[0].equals("y")) {
                    minY = Integer.parseInt(sss[0]);
                    maxY = Integer.parseInt(sss[1]);
                } else if (ss[0].equals("z")) {
                    minZ = Integer.parseInt(sss[0]);
                    maxZ = Integer.parseInt(sss[1]);
                }
            }
            for (int x = Math.max(-50, Math.min(minX, maxX)); x <= Math.min(50, Math.max(minX, maxX)); ++x) {
                for (int y = Math.max(-50, Math.min(minY, maxY)); y <= Math.min(50, Math.max(minY, maxY)); ++y) {
                    for (int z = Math.max(-50, Math.min(minZ, maxZ)); z <= Math.min(50, Math.max(minZ, maxZ)); ++z) {
                        var pos = new MultiDimPos(x, y, z);
                        if (isOn) {
                            on.add(pos);
                        } else {
                            on.remove(pos);
                        }
                    }
                }
            }
        }
        return on.size();
    }

    private static class Cube {
        final int minX, maxX, minY, maxY, minZ, maxZ;
        final boolean on;

        private Cube(int minX, int maxX, int minY, int maxY, int minZ, int maxZ, boolean on) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
            this.on = on;
        }
    }
}

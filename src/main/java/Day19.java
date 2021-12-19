import util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day19 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
    }

    private static int part1(List<String> inputs) {
        List<List<Pos>> scanners = new ArrayList<>();
        for (int i = 0; i < inputs.size(); ++i) {
            if (inputs.get(i).charAt(0) == '-') {
                ++i;
                List<Pos> beacons = new ArrayList<>();
                while (i < inputs.size() && inputs.get(i).length() > 0) {
                    beacons.add(new Pos(inputs.get(i)));
                    ++i;
                }
                scanners.add(beacons);
            }
        }

        Set<Pos> beacons = new HashSet<>(scanners.remove(0));
        while (scanners.size() > 0) {
            boolean anyMatch = false;
            int index = 0;
            for (; index < scanners.size(); ++index) {
                if (match(beacons, scanners.get(index))) {
                    scanners.remove(index);
                    System.out.println("Matched index " + index);
                    anyMatch = true;
                    --index;
                }
            }
            if (!anyMatch) {
                throw new RuntimeException("Could not match any scanner");
            }
        }

        return beacons.size();
    }

    private static boolean match(Set<Pos> beacons, List<Pos> pos) {
        for (Pos beacon : beacons) {
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 4; ++j) {
                    List<Pos> moved = move(pos, i, j);
                    for (Pos p : moved) {
                        List<Pos> mapped = new ArrayList<>();
                        int dx = beacon.x - p.x;
                        int dy = beacon.y - p.y;
                        int dz = beacon.z - p.z;
                        for (Pos pp : moved) {
                            mapped.add(pp.move(dx, dy, dz));
                        }
                        int count = 0;
                        for (Pos pp : mapped) {
                            if (beacons.contains(pp)) {
                                ++count;
                            }
                        }
                        if (count >= 12) {
                            beacons.addAll(mapped);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static List<Pos> move(List<Pos> pos, int i, int j) {
        if (i == 0) {
            return move(pos.stream().map(p -> new Pos(p.x, p.y, p.z)).collect(Collectors.toList()), j);
        }
        if (i == 1) {
            return move(pos.stream().map(p -> new Pos(p.y, -p.x, p.z)).collect(Collectors.toList()), j);
        }
        if (i == 2) {
            return move(pos.stream().map(p -> new Pos(-p.x, -p.y, p.z)).collect(Collectors.toList()), j);
        }
        if (i == 3) {
            return move(pos.stream().map(p -> new Pos(-p.y, p.x, p.x)).collect(Collectors.toList()), j);
        }
        if (i == 4) {
            return move(pos.stream().map(p -> new Pos(p.z, p.x, p.y)).collect(Collectors.toList()), j);
        }
        if (i == 5) {
            return move(pos.stream().map(p -> new Pos(p.z, p.y, p.x)).collect(Collectors.toList()), j);
        }
        throw new IllegalStateException();
    }

    private static List<Pos> move(List<Pos> pos, int j) {
        if (j == 0) {
            return pos.stream().map(p -> new Pos(p.x, p.y, p.z)).collect(Collectors.toList());
        }
        if (j == 1) {
            return pos.stream().map(p -> new Pos(p.x, p.z, -p.y)).collect(Collectors.toList());
        }
        if (j == 2) {
            return pos.stream().map(p -> new Pos(p.x, -p.y, -p.z)).collect(Collectors.toList());
        }
        if (j == 3) {
            return pos.stream().map(p -> new Pos(p.x, -p.z, p.y)).collect(Collectors.toList());
        }
        throw new IllegalStateException();
    }

    private static class Pos {
        final int x, y, z;

        public Pos(String row) {
            var split = row.split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            z = Integer.parseInt(split[2]);
        }

        public Pos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y && z == pos.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        public Pos move(int dx, int dy, int dz) {
            return new Pos(x+dx, y+dy, z+dz);
        }
    }
}

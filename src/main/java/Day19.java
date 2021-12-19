import util.Util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day19 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        solve(inputs);
    }

    private static void solve(List<String> inputs) {
        List<List<Pos>> scanners = parseScanners(inputs);

        List<Pos> scannerPos = new ArrayList<>();
        scannerPos.add(new Pos(0, 0, 0));
        Set<Pos> beacons = new HashSet<>(scanners.remove(0));
        while (scanners.size() > 0) {
            boolean anyMatch = false;
            int index = 0;
            for (; index < scanners.size(); ++index) {
                if (match(beacons, scanners.get(index), scannerPos)) {
                    scanners.remove(index);
                    anyMatch = true;
                    --index;
                }
            }
            if (!anyMatch) {
                throw new RuntimeException("Could not match any scanner");
            }
        }
        Util.submitPart1(beacons.size());

        int max = 0;
        for (int i = 0; i < scannerPos.size(); ++i) {
            for (int j = i+1; j < scannerPos.size(); ++j) {
                max = Math.max(max, scannerPos.get(i).dist(scannerPos.get(j)));
            }
        }
        Util.submitPart2(max);
    }

    private static List<List<Pos>> parseScanners(List<String> inputs) {
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
        return scanners;
    }

    private static boolean match(Set<Pos> beacons, List<Pos> pos, List<Pos> scannerPos) {
        for (int i = 0; i < 24; ++i) {
            List<Pos> moved = move(pos, i);
            for (Pos beacon : beacons) {
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
                        scannerPos.add(new Pos(dx, dy, dz));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static List<Pos> move(List<Pos> pos, int j) {
        List<Function<List<Pos>, List<Pos>>> actions = Arrays.asList(
                Day19::roll, Day19::turn, Day19::turn, Day19::turn,
                Day19::roll, Day19::turn, Day19::turn, Day19::turn,
                Day19::roll, Day19::turn, Day19::turn, Day19::turn,
                p -> roll(turn(roll(p))),
                Day19::roll, Day19::turn, Day19::turn, Day19::turn,
                Day19::roll, Day19::turn, Day19::turn, Day19::turn,
                Day19::roll, Day19::turn, Day19::turn, Day19::turn
        );
        for (int i = 1; i <= j; ++i) {
            pos = actions.get(i).apply(pos);
        }
        return pos;
    }

    private static List<Pos> roll(List<Pos> pos) {
        return pos.stream().map(p -> new Pos(p.x, p.z, -p.y)).collect(Collectors.toList());
    }

    private static List<Pos> turn(List<Pos> pos) {
        return pos.stream().map(p -> new Pos(-p.y, p.x, p.z)).collect(Collectors.toList());
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
            return new Pos(x + dx, y + dy, z + dz);
        }

        public int dist(Pos pos) {
            return Math.abs(x-pos.x) + Math.abs(y-pos.y) + Math.abs(z-pos.z);
        }
    }
}

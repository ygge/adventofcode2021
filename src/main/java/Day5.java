import util.Pos;
import util.Util;

import java.util.*;

public class Day5 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        return countDuplicates(inputs, true);
    }

    private static int part1(List<String> inputs) {
        return countDuplicates(inputs, false);
    }

    private static int countDuplicates(List<String> inputs, boolean considerDiagonals) {
        Map<Pos, Integer> seen = new HashMap<>();
        inputs.stream()
                .map(row -> parse(row, considerDiagonals))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .forEach(pos -> {
                    var prev = seen.get(pos);
                    seen.put(pos, prev == null ? 1 : prev + 1);
                });
        return (int)seen.values().stream()
                .filter(v -> v > 1)
                .count();
    }

    private static List<Pos> parse(String row, boolean considerDiagonals) {
        var s = row.split(" -> ");
        var s1 = s[0].split(",");
        int x1 = Integer.parseInt(s1[0]);
        int y1 = Integer.parseInt(s1[1]);
        var s2 = s[1].split(",");
        int x2 = Integer.parseInt(s2[0]);
        int y2 = Integer.parseInt(s2[1]);
        if (x1 == x2) {
            List<Pos> pos = new ArrayList<>();
            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); ++i) {
                pos.add(new Pos(x1, i));
            }
            return pos;
        }
        if (y1 == y2) {
            List<Pos> pos = new ArrayList<>();
            for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); ++i) {
                pos.add(new Pos(i, y1));
            }
            return pos;
        }
        if (considerDiagonals) {
            List<Pos> pos = new ArrayList<>();
            for (int i = 0; i <= Math.max(x1, x2)-Math.min(x1, x2); ++i) {
                pos.add(new Pos(x1+(x1 < x2 ? 1 : -1)*i, y1+(y1 < y2 ? 1 : -1)*i));
            }
            return pos;
        }
        return null;
    }
}

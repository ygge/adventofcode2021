import util.Pos;
import util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) {
        var inputs = Util.readPosLists();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<List<Pos>> inputs) {
        return countDuplicates(inputs, true);
    }

    private static int part1(List<List<Pos>> inputs) {
        return countDuplicates(inputs, false);
    }

    private static int countDuplicates(List<List<Pos>> inputs, boolean considerDiagonals) {
        var seen = inputs.stream()
                .map(row -> parse(row, considerDiagonals))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return (int)seen.values().stream()
                .filter(v -> v > 1)
                .count();
    }

    private static List<Pos> parse(List<Pos> row, boolean considerDiagonals) {
        int x1 = row.get(0).x;
        int y1 = row.get(0).y;
        int x2 = row.get(1).x;
        int y2 = row.get(1).y;
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

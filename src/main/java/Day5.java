import util.Pos;
import util.Util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) {
        var inputs = Util.readLines();
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
                .filter(line -> considerDiagonals || !isDiagonal(line))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return (int)seen.values().stream()
                .filter(v -> v > 1)
                .count();
    }

    private static boolean isDiagonal(List<Pos> line) {
        if (line.size() == 1) {
            return false;
        }
        return line.get(0).x != line.get(1).x && line.get(0).y != line.get(1).y;
    }
}

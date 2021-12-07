import util.Util;

import java.util.List;

public class Day7 {

    public static void main(String[] args) {
        var inputs = Util.readIntLists().get(0);
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<Integer> inputs) {
        int min = inputs.stream().mapToInt(a -> a).min().orElseThrow();
        int max = inputs.stream().mapToInt(a -> a).max().orElseThrow();
        int fuel = Integer.MAX_VALUE;
        for (int i = min; i < max; ++i) {
            int f = 0;
            for (Integer input : inputs) {
                int steps = Math.abs(input-i);
                while (steps > 0) {
                    f += steps;
                    --steps;
                }
            }
            if (f < fuel) {
                fuel = f;
            }
        }
        return fuel;
    }

    private static int part1(List<Integer> inputs) {
        int min = inputs.stream().mapToInt(a -> a).min().orElseThrow();
        int max = inputs.stream().mapToInt(a -> a).max().orElseThrow();
        int fuel = Integer.MAX_VALUE;
        for (int i = min; i < max; ++i) {
            int f = 0;
            for (Integer input : inputs) {
                f += Math.abs(input-i);
            }
            if (f < fuel) {
                fuel = f;
            }
        }
        return fuel;
    }
}

import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var inputs = Util.readIntLists().get(0);
        Util.submitPart1(solve(inputs, 80));
        Util.submitPart2(solve(inputs, 256));
    }

    private static long solve(List<Integer> inputs, int turns) {
        Map<Integer, Long> fish = inputs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (int i = 0; i < turns; ++i) {
            fish = calc(fish);
        }
        long sum = 0;
        for (Long value : fish.values()) {
            sum += value;
        }
        return sum;
    }

    private static Map<Integer, Long> calc(Map<Integer, Long> fish) {
        Map<Integer, Long> newFish = new HashMap<>();
        for (Map.Entry<Integer, Long> entry : fish.entrySet()) {
            int key = entry.getKey();
            if (key == 0) {
                var prevKey = newFish.get(6);
                newFish.put(6, entry.getValue() + (prevKey == null ? 0 : prevKey));
                newFish.put(8, entry.getValue());
            } else {
                var prevKey = newFish.get(key-1);
                newFish.put(key-1, entry.getValue() + (prevKey == null ? 0 : prevKey));
            }
        }
        return newFish;
    }
}

import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static long part2(List<String> inputs) {
        String p = inputs.get(0);
        Map<String, String> trans = new HashMap<>();
        for (int i = 2; i < inputs.size(); ++i) {
            var split = inputs.get(i).split(" -> ");
            trans.put(split[0], split[1]);
        }

        Map<String, Long> pairs = new HashMap<>();
        for (int i = 1; i < p.length(); ++i) {
            var s = p.substring(i-1, i+1);
            var v = pairs.get(s);
            pairs.put(s, v == null ? 1 : (v+1));
        }

        for (int i = 0; i < 40; ++i) {
            Map<String, Long> newPairs = new HashMap<>();
            for (Map.Entry<String, Long> entry : pairs.entrySet()) {
                var c = trans.get(entry.getKey());
                var a = entry.getKey().charAt(0) + c;
                var b = c + entry.getKey().charAt(1);
                var va = newPairs.get(a);
                newPairs.put(a, va == null ? entry.getValue() : (va+entry.getValue()));
                var vb = newPairs.get(b);
                newPairs.put(b, vb == null ? entry.getValue() : (vb+entry.getValue()));
            }
            pairs = newPairs;
        }

        Map<Character, Long> count = new HashMap<>();
        for (Map.Entry<String, Long> entry : pairs.entrySet()) {
            var a = entry.getKey().charAt(0);
            var b = entry.getKey().charAt(1);
            var va = count.get(a);
            count.put(a, va == null ? entry.getValue() : (va+entry.getValue()));
            var vb = count.get(b);
            count.put(b, vb == null ? entry.getValue() : (vb+entry.getValue()));
        }
        var max = (count.values().stream().mapToLong(i -> i).max().orElseThrow()+1)/2;
        var min = (count.values().stream().mapToLong(i -> i).min().orElseThrow()+1)/2;
        return max-min;
    }

    private static int part1(List<String> inputs) {
        String p = inputs.get(0);
        Map<String, String> trans = new HashMap<>();
        for (int i = 2; i < inputs.size(); ++i) {
            var split = inputs.get(i).split(" -> ");
            trans.put(split[0], split[1]);
        }

        for (int i = 0; i < 10; ++i) {
            StringBuilder v = new StringBuilder(p.substring(0, 1));
            for (int j = 1; j < p.length(); ++j) {
                v.append(trans.get(p.substring(j - 1, j + 1))).append(p.charAt(j));
            }
            p = v.toString();
        }
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < p.length(); ++i) {
            var c = p.charAt(i);
            var v = count.get(c);
            count.put(c, v == null ? 1 : (v+1));
        }
        int max = count.values().stream().mapToInt(i -> i).max().orElseThrow();
        int min = count.values().stream().mapToInt(i -> i).min().orElseThrow();
        return max-min;
    }
}

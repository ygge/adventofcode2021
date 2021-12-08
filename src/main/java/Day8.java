import util.Util;

import java.util.*;

public class Day8 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        //Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        int sum = 0;
        for (String input : inputs) {
            Map<Integer, Set<Character>> map = new HashMap<>();
            Map<String, Integer> solution = new HashMap<>();
            var split = input.split("\\|");
            var displays = split[0].split(" ");
            var output = split[1].split(" ");
            for (String s : output) {
                if (s.length() == 2) {
                    map.put(1, set(s));
                    solution.put(s, 1);
                } else if (s.length() == 4) {
                    map.put(4, set(s));
                    solution.put(s, 4);
                } else if (s.length() == 3) {
                    map.put(7, set(s));
                    solution.put(s, 7);
                } else if (s.length() == 7) {
                    map.put(8, set(s));
                    solution.put(s, 8);
                }
            }
            for (String s : output) {
                if (!solution.containsKey(s)) {
                    var set = set(s);
                    if (s.length() == 6) {
                        if (map.containsKey(1)) {
                            var t = new HashSet<>(set);
                            t.retainAll(map.get(1));
                            if (t.size() == 0)
                        }
                    } else if (s.length() == 5) {

                    }
                }
            }
        }
        return sum;
    }

    private static Set<Character> set(String s) {
        var chars = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for (char c : chars) {
            set.add(c);
        }
        return set;
    }

    private static int part1(List<String> inputs) {
        int sum = 0;
        for (String input : inputs) {
            var output = input.split("\\|")[1].split(" ");
            for (String s : output) {
                if (s.length() == 2 || s.length() == 4 || s.length() == 3 || s.length() == 7) {
                    ++sum;
                }
            }
        }
        return sum;
    }
}
/**
 * 1 = 2
 * 4 = 4
 * 7 = 3
 * 8 = 7

 * 6 = 6 (saknar en som finns för 1)
 * 0 = 6 (saknar en som finns för 4)
 * 9 = 6 (resten)

 * 3 = 5 (innehåller bägge för 4)
 * 5 = 5 (båda som finns i 4:an men inte 1:an)
 * 2 = 5 (resten

 *
 *   0:      1:      2:      3:      4:
 *  aaaa    ....    aaaa    aaaa    ....
 * b    c  .    c  .    c  .    c  b    c
 * b    c  .    c  .    c  .    c  b    c
 *  ....    ....    dddd    dddd    dddd
 * e    f  .    f  e    .  .    f  .    f
 * e    f  .    f  e    .  .    f  .    f
 *  gggg    ....    gggg    gggg    ....
 *
 *   5:      6:      7:      8:      9:
 *  aaaa    aaaa    aaaa    aaaa    aaaa
 * b    .  b    .  .    c  b    c  b    c
 * b    .  b    .  .    c  b    c  b    c
 *  dddd    dddd    ....    dddd    dddd
 * .    f  e    f  .    f  e    f  .    f
 * .    f  e    f  .    f  e    f  .    f
 *  gggg    gggg    ....    gggg    gggg
 */
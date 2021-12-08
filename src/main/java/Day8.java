import util.Util;

import java.util.*;

public class Day8 {

    public static void main(String[] args) {
        Util.verifySubmission();
        //var inputs = Util.readStrings();
        var inputs = Util.toStringList("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | " +
                "fdgacbe cefdb cefbgd gcbe\n" +
                "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | " +
                "fcgedb cgb dgebacf gc\n" +
                "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | " +
                "cg cg fdcagb cbg\n" +
                "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | " +
                "efabcd cedba gadfec cb\n" +
                "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | " +
                "gecf egdcabf bgf bfgea\n" +
                "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | " +
                "gebdcfa ecba ca fadegcb\n" +
                "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | " +
                "cefg dcbef fcge gbcadfe\n" +
                "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | " +
                "ed bcgafe cdgba cbgef\n" +
                "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | " +
                "gbdfcae bgc cg cgb\n" +
                "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | " +
                "fgae cfgab fg bagce");
        //Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        int sum = 0;
        for (String input : inputs) {
            Map<Integer, Set<Character>> map = new HashMap<>();
            Map<Set<Character>, Integer> solution = new HashMap<>();
            var split = input.split(" \\| ");
            var displays = split[0].split(" ");
            var output = split[1].split(" ");
            for (String s : displays) {
                var set = set(s);
                if (s.length() == 2) {
                    map.put(1, set);
                    solution.put(set(s), 1);
                } else if (s.length() == 4) {
                    map.put(4, set);
                    solution.put(set(s), 4);
                } else if (s.length() == 3) {
                    map.put(7, set);
                    solution.put(set(s), 7);
                } else if (s.length() == 7) {
                    map.put(8, set);
                    solution.put(set(s), 8);
                }
            }
            for (String s : displays) {
                if (!solution.containsKey(set(s))) {
                    var set = set(s);
                    if (s.length() == 6) {
                        var solved = false;
                        if (map.containsKey(1)) {
                            var t = new HashSet<>(set);
                            t.retainAll(map.get(1));
                            if (t.size() == 1) {
                                map.put(0, set);
                                solution.put(set(s), 0);
                                solved = true;
                            }
                        }
                        if (map.containsKey(4)) {
                            var t = new HashSet<>(set);
                            t.retainAll(map.get(4));
                            if (t.size() == 3) {
                                map.put(6, set);
                                solution.put(set(s), 6);
                                solved = true;
                            }
                            if (t.size() == 4) {
                                map.put(9, set(s));
                                solution.put(set(s), 9);
                                solved = true;
                            }
                        }
                        if (!solved) {
                            throw new RuntimeException("Not solved: " + input);
                        }
                    } else if (s.length() == 5) {
                        var solved = false;
                        if (map.containsKey(1)) {
                            var t = new HashSet<>(set);
                            t.retainAll(map.get(1));
                            if (t.size() == 2) {
                                map.put(5, set(s));
                                solution.put(set(s), 5);
                                solved = true;
                            }
                        }
                        if (map.containsKey(4)) {
                            var t = new HashSet<>(set);
                            t.retainAll(map.get(4));
                            if (t.size() == 3) {
                                map.put(3, set(s));
                                solution.put(set(s), 3);
                                solved = true;
                            }
                            if (t.size() == 2) {
                                map.put(2, set(s));
                                solution.put(set(s), 2);
                                solved = true;
                            }
                        }
                        if (!solved) {
                            throw new RuntimeException("Not solved: " + input);
                        }
                    }
                }
            }
            int s = 0;
            for (String display : output) {
                s = 10*s + solution.get(set(display));
            }
            System.out.println(s);
            sum += s;
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
 * 2 = 5 (resten)

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
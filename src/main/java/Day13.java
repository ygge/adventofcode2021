import util.Pos;
import util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        part2(inputs);
    }

    private static void part2(List<String> inputs) {
        Set<Pos> mark = new HashSet<>();
        int r = readInitialMarkings(inputs, mark);
        for (++r; r < inputs.size(); ++r) {
            var ins = inputs.get(r);
            mark = fold(mark, ins);
        }
        int maxX = mark.stream().mapToInt(p -> p.x).max().orElseThrow();
        int maxY = mark.stream().mapToInt(p -> p.y).max().orElseThrow();
        for (int y = 0; y <= maxY; ++y) {
            for (int x = 0; x <= maxX; ++x) {
                if (mark.contains(new Pos(x, y))) {
                    System.out.print('X');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    private static int part1(List<String> inputs) {
        Set<Pos> mark = new HashSet<>();
        int r = readInitialMarkings(inputs, mark);
        return fold(mark, inputs.get(++r)).size();
    }

    private static Set<Pos> fold(Set<Pos> mark, String ins) {
        var fold = ins.split(" ")[2].split("=");
        var f = Integer.parseInt(fold[1]);
        Set<Pos> folded = new HashSet<>();
        if (fold[0].charAt(0) == 'x') {
            for (Pos pos : mark) {
                if (pos.x > f) {
                    folded.add(new Pos(f-(pos.x-f), pos.y));
                } else {
                    folded.add(pos);
                }
            }
        } else {
            for (Pos pos : mark) {
                if (pos.y > f) {
                    folded.add(new Pos(pos.x, f-(pos.y-f)));
                } else {
                    folded.add(pos);
                }
            }
        }
        mark = folded;
        return mark;
    }

    private static int readInitialMarkings(List<String> inputs, Set<Pos> mark) {
        int r = 0;
        for (; r < inputs.size(); ++r) {
            var input = inputs.get(r);
            if (input.length() == 0) {
                break;
            }
            var split = input.split(",");
            var x = Integer.parseInt(split[0]);
            var y = Integer.parseInt(split[1]);
            mark.add(new Pos(x, y));
        }
        return r;
    }
}

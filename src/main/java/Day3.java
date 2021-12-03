import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        var oxy = calc(new ArrayList<>(inputs), true);
        var co2 = calc(new ArrayList<>(inputs), false);
        return oxy*co2;
    }

    private static int calc(List<String> inputs, boolean most) {
        for (int i = 0; inputs.size() > 1; ++i) {
            int z = 0;
            int o = 0;
            for (String input : inputs) {
                if (input.charAt(i) == '1') {
                    ++o;
                } else {
                    ++z;
                }
            }
            final var index = i;
            var target = most ? (z > o ? '0' : '1') : (o < z ? '1' : '0');
            inputs = inputs.stream().filter(in -> in.charAt(index) == target).collect(Collectors.toList());
        }
        return Integer.parseInt(inputs.get(0), 2);
    }

    private static int part1(List<String> inputs) {
        var g = new StringBuilder();
        var e = new StringBuilder();
        for (int i = 0; i < inputs.get(0).length(); ++i) {
            int z = 0;
            int o = 0;
            for (String input : inputs) {
                if (input.charAt(i) == '1') {
                    ++o;
                } else {
                    ++z;
                }
            }
            if (z > o) {
                g.append("0");
                e.append("1");
            } else {
                g.append("1");
                e.append("0");
            }
        }
        return Integer.parseInt(g.toString(), 2) * Integer.parseInt(e.toString(), 2);
    }
}

import util.Util;

import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        var d = 0;
        var p = 0;
        var a = 0;
        for (String input : inputs) {
            var split = input.split(" ");
            if (split[0].equals("down")) {
                a += Integer.parseInt(split[1]);
            } else if (split[0].equals("up")) {
                a -= Integer.parseInt(split[1]);
            } else if (split[0].equals("forward")) {
                p += Integer.parseInt(split[1]);
                d += a*Integer.parseInt(split[1]);
            }
        }
        return p*d;
    }

    private static int part1(List<String> inputs) {
        var d = 0;
        var p = 0;
        for (String input : inputs) {
            var split = input.split(" ");
            if (split[0].equals("down")) {
                d += Integer.parseInt(split[1]);
            } else if (split[0].equals("up")) {
                d -= Integer.parseInt(split[1]);
            } else if (split[0].equals("forward")) {
                p += Integer.parseInt(split[1]);
            }
        }
        return p*d;
    }
}

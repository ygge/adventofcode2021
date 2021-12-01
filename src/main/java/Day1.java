import util.Util;

import java.util.List;

public class Day1 {

    public static void main(String[] args) {
        var inputs = Util.readInts();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<Integer> inputs) {
        int increase = 0;
        int last = -1;
        for (int i = 2; i < inputs.size(); ++i) {
            int sum = inputs.get(i)+inputs.get(i-1)+inputs.get(i-2);
            if (last != -1 && last < sum) {
                ++increase;
            }
            last = sum;
        }
        return increase;
    }

    private static int part1(List<Integer> inputs) {
        int increase = 0;
        int last = -1;
        for (Integer input : inputs) {
            if (last != -1 && last < input) {
                ++increase;
            }
            last = input;
        }
        return increase;
    }
}

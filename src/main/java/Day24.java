import util.Util;

import java.util.*;

public class Day24 {

    public static void main(String[] args) {
        //Löst för hand:
        /*
Om w5 = w4+1 ingen ändring på z
Om w7 = w6+8-15 = w6-7 ingen ändring på z
Om w10 = w9+8-13=w9-5
Om w11 = w8+10-13=w8-3
Om w12 = w3+6-14=w3-8
Om w13 = w2+6-2=w2+4
Om w14 = w1+14-9=w1+5

störst: 459 899 299 461 99
minst: 119 128 146 111 56
         */
        int[] add = new int[14];
        int[] add2 = new int[14];
        int[] dep = new int[14];
        Arrays.fill(dep, -1);
        int index = -1;
        boolean next = false;
        boolean next2 = false;
        Deque<Integer> stack = new LinkedList<>();
        for (String ins : Util.readStrings()) {
            if (ins.equals("inp w")) {
                ++index;
            } else if (ins.equals("div z 1")) {
                stack.add(index);
                next2 = true;
            } else if (ins.equals("div z 26")) {
                dep[index] = stack.pollLast();
                next2 = true;
            } else if (ins.equals("add y w")) {
                next = true;
            } else if (next) {
                add[index] = Integer.parseInt(ins.split(" ")[2]);
                next = false;
            } else if (next2) {
                add2[index] = Integer.parseInt(ins.split(" ")[2]);
                next2 = false;
            }
        }
        part1(add, add2, dep);
        part2(add, add2, dep);
    }

    private static void part2(int[] add, int[] add2, int[] dep) {
        int[] max = new int[14];
        Arrays.fill(max, 1);
        for (int i = 0; i < dep.length; ++i) {
            int d = dep[i];
            if (d != -1) {
                int diff = add[d] + add2[i];
                max[diff > 0 ? i : d] += Math.abs(diff);
            }
        }
        Util.submitPart2(toString(max));
    }

    private static void part1(int[] add, int[] add2, int[] dep) {
        int[] max = new int[14];
        Arrays.fill(max, 9);
        for (int i = 0; i < dep.length; ++i) {
            int d = dep[i];
            if (d != -1) {
                int diff = add[d] + add2[i];
                max[diff > 0 ? d : i] -= Math.abs(diff);
            }
        }
        Util.submitPart1(toString(max));
    }

    private static String toString(int[] max) {
        var sb = new StringBuilder();
        for (int j : max) {
            sb.append(j);
        }
        return sb.toString();
    }
}

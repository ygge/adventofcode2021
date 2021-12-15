import util.Direction;
import util.Pos;
import util.Util;

import java.util.*;

public class Day15 {

    public static void main(String[] args) {
        var inputs = Util.readIntBoard();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(int[][] inputs) {
        int size = inputs.length;
        var board = new int[size*5][size*5];
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                for (int my = 0; my < 5; ++my) {
                    for (int mx = 0; mx < 5; ++mx) {
                        var v = inputs[y][x]+mx+my;
                        if (v >= 10) {
                            v -= 9;
                        }
                        board[y+size*my][x+size*mx] = v;
                    }
                }
            }
        }
        return part1(board);
    }

    private static int part1(int[][] inputs) {
        var start = new Pos(0, 0);
        Map<Pos, Integer> seen = new HashMap<>();
        Deque<Node> queue = new LinkedList<>();
        queue.add(new Node(start, 0));
        while (!queue.isEmpty()) {
            var n = queue.pop();
            var score = seen.get(n.pos);
            if (score != null && score <= n.score) {
                continue;
            }
            seen.put(n.pos, n.score);
            checkAndAdd(inputs, n, Direction.UP, queue, seen);
            checkAndAdd(inputs, n, Direction.LEFT, queue, seen);
            checkAndAdd(inputs, n, Direction.DOWN, queue, seen);
            checkAndAdd(inputs, n, Direction.RIGHT, queue, seen);
        }
        return seen.get(new Pos(inputs.length-1, inputs[0].length-1));
    }

    private static void checkAndAdd(int[][] inputs, Node node, Direction dir, Deque<Node> queue, Map<Pos, Integer> seen) {
        var pos = node.pos.move(dir);
        if (pos.x < 0 || pos.y < 0 || pos.y >= inputs.length || pos.x >= inputs[0].length) {
            return;
        }
        int score = node.score + inputs[pos.y][pos.x];
        var prevScore = seen.get(pos);
        if (prevScore != null && prevScore <= score) {
            return;
        }
        queue.add(new Node(pos, score));
    }

    public static class Node {
        private final Pos pos;
        private final int score;

        public Node(Pos pos, int score) {
            this.pos = pos;
            this.score = score;
        }
    }
}

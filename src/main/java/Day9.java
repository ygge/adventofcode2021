import util.Direction;
import util.Pos;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 {

    public static void main(String[] args) {
        var inputs = Util.readBoard();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(char[][] board) {
        Set<Pos> seen = new HashSet<>();
        List<Set<Pos>> basins = new ArrayList<>();
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                var pos = new Pos(x, y);
                if (seen.contains(pos)) {
                    continue;
                }
                if (board[y][x] == '9') {
                    continue;
                }
                Set<Pos> basin = new HashSet<>();
                basin.add(pos);
                calc(pos, basin, seen, board);
                basins.add(basin);
            }
        }
        basins.sort((b1, b2) -> b2.size() - b1.size());
        return basins.get(0).size() * basins.get(1).size() * basins.get(2).size();
    }

    private static void calc(Pos pos, Set<Pos> basin, Set<Pos> seen, char[][] board) {
        if (!seen.add(pos)) {
            return;
        }
        if (board[pos.y][pos.x] == '9') {
            return;
        }
        basin.add(pos);
        if (pos.y > 0) {
            calc(pos.move(Direction.UP), basin, seen, board);
        }
        if (pos.x > 0) {
            calc(pos.move(Direction.LEFT), basin, seen, board);
        }
        if (pos.y < board.length-1) {
            calc(pos.move(Direction.DOWN), basin, seen, board);
        }
        if (pos.x < board[pos.y].length-1) {
            calc(pos.move(Direction.RIGHT), basin, seen, board);
        }
    }

    private static int part1(char[][] inputs) {
        int sum = 0;
        for (int y = 0; y < inputs.length; ++y) {
            for (int x = 0; x < inputs[y].length; ++x) {
                int min = Integer.MAX_VALUE;
                if (y > 0 && inputs[y-1][x] < min) {
                    min = inputs[y-1][x];
                }
                if (x > 0 && inputs[y][x-1] < min) {
                    min = inputs[y][x-1];
                }
                if (y < inputs.length-1 && inputs[y+1][x] < min) {
                    min = inputs[y+1][x];
                }
                if (x < inputs[y].length-1 && inputs[y][x+1] < min) {
                    min = inputs[y][x+1];
                }
                if (min > inputs[y][x]) {
                    sum += (inputs[y][x]-'0')+1;
                }
            }
        }
        return sum;
    }
}

import util.Pos;
import util.Util;

import java.util.HashSet;
import java.util.Set;

public class Day11 {

    public static void main(String[] args) {
        var inputs = Util.readBoard();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(char[][] board) {
        for (int i = 0; ; ++i) {
            for (int y = 0; y < board.length; ++y) {
                for (int x = 0; x < board[y].length; ++x) {
                    ++board[y][x];
                }
            }
            Set<Pos> flashes = new HashSet<>();
            while (true) {
                int size = flashes.size();
                for (int y = 0; y < board.length; ++y) {
                    for (int x = 0; x < board[y].length; ++x) {
                        if (board[y][x] > '9') {
                            flashes.add(new Pos(x, y));
                            for (int dy = -1; dy <= 1; ++dy) {
                                for (int dx = -1; dx <= 1; ++dx) {
                                    var pos = new Pos(x+dx, y+dy);
                                    if (pos.y >= 0 && pos.y < board.length && pos.x >= 0 && pos.x < board[y].length && !flashes.contains(pos)) {
                                        board[pos.y][pos.x]++;
                                    }
                                }
                            }
                        }
                    }
                }
                for (int y = 0; y < board.length; ++y) {
                    for (int x = 0; x < board[y].length; ++x) {
                        if (flashes.contains(new Pos(x, y))) {
                            board[y][x] = '0';
                        }
                    }
                }
                if (flashes.size() == size) {
                    break;
                }
            }
            if (flashes.size() == 100) {
                return i+1;
            }
        }
    }

    private static int part1(char[][] board) {
        int ans = 0;
        for (int i = 0; i < 100; ++i) {
            for (int y = 0; y < board.length; ++y) {
                for (int x = 0; x < board[y].length; ++x) {
                    ++board[y][x];
                }
            }
            Set<Pos> flashes = new HashSet<>();
            while (true) {
                int size = flashes.size();
                for (int y = 0; y < board.length; ++y) {
                    for (int x = 0; x < board[y].length; ++x) {
                        if (board[y][x] > '9') {
                            flashes.add(new Pos(x, y));
                            for (int dy = -1; dy <= 1; ++dy) {
                                for (int dx = -1; dx <= 1; ++dx) {
                                    var pos = new Pos(x+dx, y+dy);
                                    if (pos.y >= 0 && pos.y < board.length && pos.x >= 0 && pos.x < board[y].length && !flashes.contains(pos)) {
                                        board[pos.y][pos.x]++;
                                    }
                                }
                            }
                        }
                    }
                }
                for (int y = 0; y < board.length; ++y) {
                    for (int x = 0; x < board[y].length; ++x) {
                        if (flashes.contains(new Pos(x, y))) {
                            board[y][x] = '0';
                        }
                    }
                }
                if (flashes.size() == size) {
                    break;
                }
            }
            ans += flashes.size();
        }
        return ans;
    }
}

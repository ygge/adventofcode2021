import util.Pos;
import util.Util;

import java.util.HashSet;
import java.util.Set;

public class Day25 {

    public static void main(String[] args) {
        var inputs = Util.readBoard();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(0);
    }

    private static int part1(char[][] board) {
        int steps = 0;
        while (true) {
            Set<Pos> moved = new HashSet<>();
            char[][] board2 = new char[board.length][board[0].length];
            for (int y = 0; y < board.length; ++y) {
                for (int x = 0; x < board[y].length; ++x) {
                    board2[y][x] = '.';
                }
            }
            for (int y = 0; y < board.length; ++y) {
                for (int x = 0; x < board[y].length; ++x) {
                    if (board[y][x] == '>') {
                        if (board[y][x == board[y].length-1 ? 0 : (x+1)] == '.') {
                            moved.add(new Pos(x, y));
                        } else {
                            board2[y][x] = '>';
                        }
                    }
                }
            }
            for (Pos pos : moved) {
                if (board[pos.y][pos.x] == '>') {
                    board2[pos.y][pos.x == board[pos.y].length-1 ? 0 : (pos.x+1)] = '>';
                }
            }
            for (int y = 0; y < board.length; ++y) {
                for (int x = 0; x < board[y].length; ++x) {
                    if (board[y][x] == 'v') {
                        if (board[y == board.length-1 ? 0 : (y+1)][x] != 'v'
                                && board2[y == board.length-1 ? 0 : (y+1)][x] != '>') {
                            moved.add(new Pos(x ,y));
                        } else {
                            board2[y][x] = 'v';
                        }
                    }
                }
            }
            for (Pos pos : moved) {
                if (board[pos.y][pos.x] == 'v') {
                    board2[pos.y == board.length-1 ? 0 : (pos.y+1)][pos.x] = 'v';
                }
            }
            ++steps;
            if (moved.isEmpty()) {
                return steps;
            }
            board = board2;
        }
    }
}

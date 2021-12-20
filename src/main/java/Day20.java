import util.Util;

import java.util.List;

public class Day20 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(solve(inputs, 2));
        Util.submitPart2(solve(inputs, 50));
    }

    private static int solve(List<String> inputs, int num) {
        char[] algo = inputs.get(0).toCharArray();

        char[][] board = new char[inputs.size()-2][];
        for (int i = 2; i < inputs.size(); ++i) {
            board[i-2] = inputs.get(i).toCharArray();
        }
        for (int i = 0; i < num; ++i) {
            board = perform(algo, board, (i%2) == 0);
        }

        int count = 0;
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == '#') {
                    ++count;
                }
            }
        }
        return count;
    }

    private static char[][] perform(char[] algo, char[][] board, boolean even) {
        char[][] newBoard = new char[board.length+2][board[0].length+2];
        for (int y = 0; y < newBoard.length; ++y) {
            for (int x = 0; x < newBoard[y].length; ++x) {
                var num = num(board, y-1, x-1, even);
                newBoard[y][x] = algo[num];
            }
        }
        return newBoard;
    }

    private static int num(char[][] board, int y, int x, boolean even) {
        var sb = new StringBuilder();
        for (int dy = -1; dy <= 1; ++dy) {
            for (int dx = -1; dx <= 1; ++dx) {
                int yy = y+dy;
                int xx = x+dx;
                if (yy < 0 || yy >= board.length || xx < 0 || xx >= board[yy].length) {
                    sb.append(even ? '0' : '1');
                } else {
                    sb.append(board[yy][xx] == '#' ? '1' : '0');
                }
            }
        }
        return Integer.parseInt(sb.toString(), 2);
    }
}

import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        List<Integer> nums = Stream.of(inputs.get(0).split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        List<int[][]> boards = parseBoard(inputs);
        boolean[] done = new boolean[boards.size()];
        int numDone = 0;
        for (Integer num : nums) {
            for (int a = 0; a < boards.size(); ++a) {
                if (done[a]) {
                    continue;
                }
                int res = checkBoard(boards.get(a), num);
                if (res != -1) {
                    done[a] = true;
                    if (++numDone == boards.size()) {
                        return res;
                    }
                }
            }
        }
        throw new IllegalStateException();
    }

    private static int part1(List<String> inputs) {
        List<Integer> nums = Stream.of(inputs.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<int[][]> boards = parseBoard(inputs);
        for (Integer num : nums) {
            for (int[][] board : boards) {
                int score = checkBoard(board, num);
                if (score != -1) {
                    return score;
                }
            }
        }
        throw new IllegalStateException();
    }

    private static List<int[][]> parseBoard(List<String> inputs) {
        List<int[][]> boards = new ArrayList<>();
        for (int i = 2; i < inputs.size(); i += 6) {
            int[][] board = new int[5][5];
            for (int j = 0; j < 5; ++j) {
                var s = inputs.get(i + j).split(" ");
                board[j] = new int[5];
                int k = 0;
                for (String s1 : s) {
                    if (s1.length() > 0) {
                        board[j][k++] = Integer.parseInt(s1);
                    }
                }
            }
            boards.add(board);
        }
        return boards;
    }

    private static int checkBoard(int[][] board, int num) {
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] == num) {
                    board[y][x] = -1;
                }
            }
        }
        for (int y = 0; y < board.length; ++y) {
            boolean solved = true;
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] != -1) {
                    solved = false;
                    break;
                }
            }
            if (solved) {
                return score(num, board);
            }
        }
        for (int x = 0; x < board[0].length; ++x) {
            boolean solved = true;
            for (int y = 0; y < board.length; ++y) {
                if (board[y][x] != -1) {
                    solved = false;
                    break;
                }
            }
            if (solved) {
                return score(num, board);
            }
        }
        return -1;
    }

    private static int score(Integer num, int[][] board) {
        int sum = 0;
        for (int[] row : board) {
            for (int i : row) {
                if (i != -1) {
                    sum += i;
                }
            }
        }
        return sum*num;
    }
}

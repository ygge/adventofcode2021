import util.Pos;
import util.Util;

public class Day17 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(String input) {
        var split = input.split(" ");
        var xs = split[2].split("\\.\\.");
        int minX = Integer.parseInt(xs[0].substring(2));
        int maxX = Integer.parseInt(xs[1].substring(0, xs[1].length()-1));
        var ys = split[3].split("\\.\\.");
        int minY = Integer.parseInt(ys[0].substring(2));
        int maxY = Integer.parseInt(ys[1]);

        int num = 0;
        for (int i = 1; i < maxX+1; ++i) {
            for (int j = minY; j < 1000000; ++j) {
                var pos = new Pos(0, 0);
                var vel = new Pos(i, j);
                var res = simulate(pos, vel, minX, maxX, minY, maxY);
                if (res != -1) {
                    ++num;
                }
            }
        }
        return num;
    }

    private static int part1(String input) {
        var split = input.split(" ");
        var xs = split[2].split("\\.\\.");
        int minX = Integer.parseInt(xs[0].substring(2));
        int maxX = Integer.parseInt(xs[1].substring(0, xs[1].length()-1));
        var ys = split[3].split("\\.\\.");
        int minY = Integer.parseInt(ys[0].substring(2));
        int maxY = Integer.parseInt(ys[1]);

        int best = 0;
        for (int i = 1; i < 1000; ++i) {
            for (int j = -10; j < 1000; ++j) {
                var pos = new Pos(0, 0);
                var vel = new Pos(i, j);
                var res = simulate(pos, vel, minX, maxX, minY, maxY);
                best = Math.max(best, res);
            }
        }
        return best;
    }

    private static int simulate(Pos pos, Pos vel, int minX, int maxX, int minY, int maxY) {
        int y = 0;
        while (true) {
            y = Math.max(y, pos.y);
            if (pos.x >= minX && pos.x <= maxX && pos.y >= minY && pos.y <= maxY) {
                return y;
            }
            pos = new Pos(pos.x + vel.x, pos.y + vel.y);
            vel = new Pos(vel.x-sign(vel.x), vel.y-1);
            if (pos.y < minY || pos.x > maxX || (vel.x == 0 && pos.x < minX)) {
                return -1;
            }
        }
    }

    private static int sign(int n) {
        return Integer.compare(n, 0);
    }
}

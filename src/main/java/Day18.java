import util.Util;

import java.util.List;

public class Day18 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static long part2(List<String> inputs) {
        long max = 0;
        for (int i = 0; i < inputs.size(); ++i) {
            for (int j = 0; j < inputs.size(); ++j) {
                if (i != j) {
                    var p1 = parse(inputs.get(i), new Index(0));
                    var p2 = parse(inputs.get(j), new Index(0));
                    var p = new Pair(p1, p2);
                    reduce(p);
                    max = Math.max(max, p.magnitude());
                }
            }
        }
        return max;
    }

    private static long part1(List<String> inputs) {
        Pair num = null;
        for (String input : inputs) {
            var p = parse(input, new Index(0));
            if (num == null) {
                num = p;
            } else {
                num = new Pair(num, p);
                reduce(num);
            }
        }
        return num.magnitude();
    }

    private static void reduce(Pair pair) {
        while (true) {
            boolean reduced = reduce(pair, 0, false);
            if (!reduced) {
                boolean exploded = split(pair);
                if (!exploded) {
                    return;
                }
            }
        }
    }

    private static boolean split(Pair pair) {
        if (pair.pLeft != null && split(pair.pLeft)) {
            return true;
        }
        if (pair.pLeft == null && pair.left >= 10) {
            pair.pLeft = new Pair(pair.left/2, (pair.left+1)/2, pair);
            pair.left = 0;
            return true;
        }
        if (pair.pRight != null && split(pair.pRight)) {
            return true;
        }
        if (pair.pRight == null && pair.right >= 10) {
            pair.pRight = new Pair(pair.right/2, (pair.right+1)/2, pair);
            pair.right = 0;
            return true;
        }
        return false;
    }

    private static boolean reduce(Pair pair, int depth, boolean left) {
        if (depth == 4) {
            addLeft(pair, pair.left);
            addRight(pair, pair.right);
            if (left) {
                pair.parent.left = 0;
                pair.parent.pLeft = null;
            } else {
                pair.parent.right = 0;
                pair.parent.pRight = null;
            }
            return true;
        }
        if (pair.pLeft != null && reduce(pair.pLeft, depth + 1, true)) {
            return true;
        }
        if (pair.pRight != null && reduce(pair.pRight, depth + 1, false)) {
            return true;
        }
        return false;
    }

    private static void addRight(Pair pair, int value) {
        if (pair.parent == null) {
            return;
        }
        if (pair.parent.pRight == pair) {
            addRight(pair.parent, value);
        } else if (pair.parent.pRight == null) {
            pair.parent.right += value;
        } else {
            var p = pair.parent.pRight;
            while (p.pLeft != null) {
                p = p.pLeft;
            }
            p.left += value;
        }
    }

    private static void addLeft(Pair pair, int value) {
        if (pair.parent == null) {
            return;
        }
        if (pair.parent.pLeft == pair) {
            addLeft(pair.parent, value);
        } else if (pair.parent.pLeft == null) {
            pair.parent.left += value;
        } else {
            var p = pair.parent.pLeft;
            while (p.pRight != null) {
                p = p.pRight;
            }
            p.right += value;
        }
    }

    private static Pair parse(String input, Index index) {
        var p = new Pair(null);
        ++index.p;
        if (input.charAt(index.p) == '[') {
            p.pLeft = parse(input, index);
            p.pLeft.parent = p;
        } else {
            p.left = input.charAt(index.p) - '0';
            ++index.p;
        }
        if (input.charAt(index.p) != ',') {
            throw new RuntimeException(String.format("Expected , but got %c at %d", input.charAt(index.p), index.p));
        }
        ++index.p;
        if (input.charAt(index.p) == '[') {
            p.pRight = parse(input, index);
            p.pRight.parent = p;
        } else {
            p.right = input.charAt(index.p) - '0';
            ++index.p;
        }
        if (input.charAt(index.p) != ']') {
            throw new RuntimeException(String.format("Expected ] but got %c at %d", input.charAt(index.p), index.p));
        }
        ++index.p;
        return p;
    }

    private static class Pair {
        private Pair parent;
        private int left, right;
        private Pair pLeft, pRight;

        public Pair(Pair parent) {
            this.parent = parent;
        }

        public Pair(Pair left, Pair right) {
            this.pLeft = left;
            this.pRight = right;
            left.parent = this;
            right.parent = this;
        }

        public Pair(int left, int right, Pair parent) {
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public String toString() {
            var sb = new StringBuilder();
            sb.append('[');
            if (pLeft != null) {
                sb.append(pLeft);
            } else {
                sb.append(left);
            }
            sb.append(',');
            if (pRight != null) {
                sb.append(pRight);
            } else {
                sb.append(right);
            }
            sb.append(']');
            return sb.toString();
        }

        public long magnitude() {
            long ans = 0;
            if (pLeft != null) {
                ans += 3 * pLeft.magnitude();
            } else {
                ans += 3L * left;
            }
            if (pRight != null) {
                ans += 2 * pRight.magnitude();
            } else {
                ans += 2L * right;
            }
            return ans;
        }
    }

    private static class Index {
        int p;

        public Index(int p) {
            this.p = p;
        }
    }
}

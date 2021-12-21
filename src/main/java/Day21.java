import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day21 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static long part2(List<String> inputs) {
        Map<State, Long> states = new HashMap<>();
        states.put(new State(8, 6, 0, 0, true), 1L);
        long p1Won = 0;
        long p2Won = 0;
        while (!states.isEmpty()) {
            var entry = states.entrySet().stream().findFirst().orElseThrow();
            var state = entry.getKey();
            states.remove(state);
            if (state.p1Turn) {
                for (int a = 1; a <= 3; ++a) {
                    for (int b = 1; b <= 3; ++b) {
                        for (int c = 1; c <= 3; ++c) {
                            int pos = state.p1+a+b+c;
                            while (pos > 10) {
                                pos -= 10;
                            }
                            int score = state.s1+pos;
                            if (score >= 21) {
                                p1Won += entry.getValue();
                            } else {
                                var newState = new State(pos, state.p2, score, state.s2, false);
                                var prev = states.get(newState);
                                states.put(newState, (prev == null ? 0 : prev) + entry.getValue());
                            }
                        }
                    }
                }
            } else {
                for (int a = 1; a <= 3; ++a) {
                    for (int b = 1; b <= 3; ++b) {
                        for (int c = 1; c <= 3; ++c) {
                            int pos = state.p2+a+b+c;
                            while (pos > 10) {
                                pos -= 10;
                            }
                            int score = state.s2+pos;
                            if (score >= 21) {
                                p2Won += entry.getValue();
                            } else {
                                var newState = new State(state.p1, pos, state.s1, score, true);
                                var prev = states.get(newState);
                                states.put(newState, (prev == null ? 0 : prev) + entry.getValue());
                            }
                        }
                    }
                }
            }
        }
        return Math.max(p1Won, p2Won);
    }

    private static int part1(List<String> inputs) {
        int[] pos = new int[]{8, 6};
        int[] score = new int[]{0, 0};
        int turns = 0;
        int dice = 0;
        int p = 0;
        while (true) {
            for (int i = 0; i < 3; ++i) {
                ++turns;
                if (++dice == 101) {
                    dice = 1;
                }
                pos[p] += dice;
                while (pos[p] > 10) {
                    pos[p] -= 10;
                }
            }
            score[p] += pos[p];
            if (score[p] >= 1000) {
                return score[1-p]*turns;
            }
            p = 1-p;
        }
    }

    private static class State {
        final int p1, p2, s1, s2;
        final boolean p1Turn;

        public State(int p1, int p2, int s1, int s2, boolean p1Turn) {
            this.p1 = p1;
            this.p2 = p2;
            this.s1 = s1;
            this.s2 = s2;
            this.p1Turn = p1Turn;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return p1 == state.p1 && p2 == state.p2 && s1 == state.s1 && s2 == state.s2 && p1Turn == state.p1Turn;
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2, s1, s2, p1Turn);
        }
    }
}

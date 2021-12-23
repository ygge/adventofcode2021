import util.Util;

import java.util.*;

public class Day23 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        var state = new State(inputs.get(2), "  #D#C#B#A#", "  #D#B#A#C#", inputs.get(3));
        return solve(state);
    }

    private static int part1(List<String> inputs) {
        var state = new State(inputs.get(2), inputs.get(3));
        return solve(state);
    }

    private static int solve(State state) {
        var queue = new PriorityQueue<State>(Comparator.comparingInt(s -> s.energy));
        Set<State> seen = new HashSet<>();
        int[] energy = new int[]{ 1, 10, 100, 1000 };
        queue.add(state);
        while (!queue.isEmpty()) {
            var s = queue.poll();
            if (s.done()) {
                return s.energy;
            }
            if (!seen.add(s)) {
                continue;
            }
            for (int i = 0; i < s.hall.length; ++i) {
                if (s.hall[i] != '.' && i != 2 && i != 4 && i != 6 && i != 8) {
                    char c = s.hall[i];
                    int room = c-'A';
                    int depth = s.room[room].length-1;
                    for (int j = 0; j < s.room[room].length; ++j) {
                        if (s.room[room][j] != c && s.room[room][j] != '.') {
                            depth = -1;
                            break;
                        }
                        if (s.room[room][j] == c && depth >= j) {
                            depth = j-1;
                        }
                    }
                    if (depth >= 0) {
                        boolean clear = true;
                        int goal = 2*room+2; // 2, 4, 6, 8
                        for (int j = Math.min(goal, i); j <= Math.max(goal, i); ++j) {
                            if (i != j && s.hall[j] != '.') {
                                clear = false;
                                break;
                            }
                        }
                        if (clear) {
                            var newEnergy = s.energy + (Math.abs(goal-i) + depth + 1) * energy[room];
                            var newState = new State(s, newEnergy);
                            newState.hall[i] = '.';
                            newState.room[room][depth] = c;
                            queue.add(newState);
                        }
                    }
                }
            }
            for (int room = 0; room < 4; ++room) {
                if (s.room[room][s.room[room].length-1] == '.') {
                    continue;
                }
                int depth = -1;
                for (int j = 0; j < s.room[room].length; ++j) {
                    if (s.room[room][j] != '.') {
                        depth = j;
                        break;
                    }
                }
                if (depth == -1) {
                    throw new IllegalStateException("Empty room which should not be empty");
                }
                var c = s.room[room][depth];
                int hallPos = 2*room+2;
                if (s.hall[hallPos] != '.') {
                    continue;
                }
                for (int j = 0; j < s.hall.length; ++j) {
                    if (s.hall[j] == '.' && j != hallPos) {
                        boolean clear = true;
                        for (int p = Math.min(j, hallPos); p <= Math.max(j, hallPos); ++p) {
                            if (s.hall[p] != '.') {
                                clear = false;
                                break;
                            }
                        }
                        if (clear) {
                            var newEnergy = s.energy + (Math.abs(hallPos-j) + depth + 1) * energy[c-'A'];
                            var newState = new State(s, newEnergy);
                            newState.hall[j] = c;
                            newState.room[room][depth] = '.';
                            queue.add(newState);
                        }
                    }
                }
            }
        }
        throw new RuntimeException("No goal state found");
    }

    private static class State {
        final char[] hall = new char[11];
        final char[][] room;
        final int energy;
        final State prev;

        State(String a, String b) {
            Arrays.fill(hall, '.');
            room = new char[4][2];
            room[0][0] = a.charAt(3);
            room[1][0] = a.charAt(5);
            room[2][0] = a.charAt(7);
            room[3][0] = a.charAt(9);
            room[0][1] = b.charAt(3);
            room[1][1] = b.charAt(5);
            room[2][1] = b.charAt(7);
            room[3][1] = b.charAt(9);
            energy = 0;
            prev = null;
        }

        State(String a, String b, String c, String d) {
            Arrays.fill(hall, '.');
            room = new char[4][4];
            room[0][0] = a.charAt(3);
            room[1][0] = a.charAt(5);
            room[2][0] = a.charAt(7);
            room[3][0] = a.charAt(9);
            room[0][1] = b.charAt(3);
            room[1][1] = b.charAt(5);
            room[2][1] = b.charAt(7);
            room[3][1] = b.charAt(9);
            room[0][2] = c.charAt(3);
            room[1][2] = c.charAt(5);
            room[2][2] = c.charAt(7);
            room[3][2] = c.charAt(9);
            room[0][3] = d.charAt(3);
            room[1][3] = d.charAt(5);
            room[2][3] = d.charAt(7);
            room[3][3] = d.charAt(9);
            energy = 0;
            prev = null;
        }

        public State(State s, int energy) {
            System.arraycopy(s.hall, 0, hall, 0, hall.length);
            room = new char[4][s.room[0].length];
            System.arraycopy(s.room[0], 0, room[0], 0, room[0].length);
            System.arraycopy(s.room[1], 0, room[1], 0, room[1].length);
            System.arraycopy(s.room[2], 0, room[2], 0, room[2].length);
            System.arraycopy(s.room[3], 0, room[3], 0, room[3].length);
            this.energy = energy;
            prev = s;
        }

        public boolean done() {
            return done(room[0], 'A') && done(room[1], 'B') && done(room[2], 'C') && done(room[3], 'D');
        }

        private boolean done(char[] r, char c) {
            for (char value : r) {
                if (value != c) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            var sb = new StringBuilder();
            sb.append("#############\n");
            sb.append("#");
            sb.append(new String(hall));
            sb.append("#\n");
            sb.append("###");
            sb.append(room[0][0]);
            sb.append("#");
            sb.append(room[1][0]);
            sb.append("#");
            sb.append(room[2][0]);
            sb.append("#");
            sb.append(room[3][0]);
            sb.append("###\n");
            for (int i = 1; i < room[0].length; ++i) {
                sb.append("  #");
                sb.append(room[0][i]);
                sb.append("#");
                sb.append(room[1][i]);
                sb.append("#");
                sb.append(room[2][i]);
                sb.append("#");
                sb.append(room[3][i]);
                sb.append("#\n");
            }
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Arrays.equals(hall, state.hall) && Arrays.deepEquals(room, state.room);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(hall);
            result = 31 * result + Arrays.deepHashCode(room);
            return result;
        }
    }
}

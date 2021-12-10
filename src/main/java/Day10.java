import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static long part2(List<String> inputs) {
        List<Long> scores = new ArrayList<>();
        for (String input : inputs) {
            List<Character> start = new ArrayList<>();
            boolean complete = true;
            for (int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);
                if (c == '(' || c == '{' || c == '<' || c == '[') {
                    start.add(c);
                } else {
                    char e = start.remove(start.size()-1);
                    if (c == ')' && e == '(') {
                        continue;
                    }
                    if (c == ']' && e == '[') {
                        continue;
                    }
                    if (c == '>' && e == '<') {
                        continue;
                    }
                    if (c == '}' && e == '{') {
                        continue;
                    }
                    if (c == ')') {
                        complete = false;
                        break;
                    } else if (c == ']') {
                        complete = false;
                        break;
                    } else if (c == '}') {
                        complete = false;
                        break;
                    } else if (c == '>') {
                        complete = false;
                        break;
                    }
                    break;
                }
            }
            if (complete) {
                long score = 0;
                for (int i = start.size()-1; i >= 0; --i) {
                    char c = start.get(i);
                    int s = 0;
                    if (c == '(') {
                        s = 1;
                    } else if (c == '[') {
                        s = 2;
                    } else if (c == '{') {
                        s = 3;
                    } else if (c == '<') {
                        s = 4;
                    }
                    score = score*5 + s;
                }
                scores.add(score);
            }
        }
        var sorted = scores.stream().sorted().collect(Collectors.toList());
        return sorted.get(sorted.size()/2);
    }

    private static int part1(List<String> inputs) {
        int score = 0;
        for (String input : inputs) {
            List<Character> start = new ArrayList<>();
            for (int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);
                if (c == '(' || c == '{' || c == '<' || c == '[') {
                    start.add(c);
                } else {
                    char e = start.remove(start.size()-1);
                    if (c == ')' && e == '(') {
                        continue;
                    }
                    if (c == ']' && e == '[') {
                        continue;
                    }
                    if (c == '>' && e == '<') {
                        continue;
                    }
                    if (c == '}' && e == '{') {
                        continue;
                    }
                    if (c == ')') {
                        score += 3;
                    } else if (c == ']') {
                        score += 57;
                    } else if (c == '}') {
                        score += 1197;
                    } else if (c == '>') {
                        score += 25137;
                    }
                    break;
                }
            }
        }
        return score;
    }
}

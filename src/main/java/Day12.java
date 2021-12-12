import util.Util;

import java.util.*;

public class Day12 {

    public static void main(String[] args) {
        var inputs = Util.readStrings();
        Util.submitPart1(part1(inputs));
        Util.submitPart2(part2(inputs));
    }

    private static int part2(List<String> inputs) {
        Map<String, Cave> caves = parseCaves(inputs);
        List<Node> goals = new ArrayList<>();
        calc(goals, new Node(caves.get("start"), false));
        return goals.size();
    }

    private static int part1(List<String> inputs) {
        Map<String, Cave> caves = parseCaves(inputs);
        List<Node> goals = new ArrayList<>();
        calc(goals, new Node(caves.get("start"), true));
        return goals.size();
    }

    private static Map<String, Cave> parseCaves(List<String> inputs) {
        Map<String, Cave> caves = new HashMap<>();
        for (String input : inputs) {
            var split = input.split("-");
            var a = split[0];
            var b = split[1];
            caves.putIfAbsent(a, new Cave(a));
            caves.putIfAbsent(b, new Cave(b));
            caves.get(a).neighbours.add(caves.get(b));
            caves.get(b).neighbours.add(caves.get(a));
        }
        return caves;
    }

    private static void calc(List<Node> goals, Node node) {
        if (Objects.equals(node.cave.name, "end")) {
            goals.add(node);
            return;
        }
        if (!node.seen.add(node.cave.name) && !node.cave.canBeVisitedTwice) {
            if (node.hasVisistedTwice || node.cave.name.equals("start")) {
                return;
            }
            node.hasVisistedTwice = true;
        }
        for (Cave neighbour : node.cave.neighbours) {
            Node newNode = new Node(neighbour, node);
            calc(goals, newNode);
        }
    }

    public static class Node {
        public final Cave cave;
        public final Set<String> seen;
        public List<String> path;
        public boolean hasVisistedTwice;

        public Node(Cave cave, boolean hasVisistedTwice) {
            this.cave = cave;
            this.seen = new HashSet<>();
            this.path = new ArrayList<>();
            this.hasVisistedTwice = hasVisistedTwice;
        }

        public Node(Cave cave, Node node) {
            this.cave = cave;
            this.seen = new HashSet<>(node.seen);
            this.path = new ArrayList<>(node.path);
            this.path.add(cave.name);
            this.hasVisistedTwice = node.hasVisistedTwice;
        }
    }

    public static class Cave {
        public final String name;
        public final List<Cave> neighbours = new ArrayList<>();
        public final boolean canBeVisitedTwice;

        public Cave(String name) {
            this.name = name;
            this.canBeVisitedTwice = name.equals(name.toUpperCase());
        }
    }
}

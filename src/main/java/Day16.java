import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day16 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(String input) {
        String str = convertToBooleanString(input);

        var packet = parse(str, 0, str.length(), -1);
        System.out.println(packet.values.get(0));
        return packet.values.get(0);
    }

    private static int part1(String input) {
        String str = convertToBooleanString(input);

        var packet = parse(str, 0, str.length(), -1);
        System.out.println(packet.versionSum);
        return packet.versionSum;
    }

    private static String convertToBooleanString(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            int v = input.charAt(i)-'0';
            if (input.charAt(i) >= 'A') {
                v = input.charAt(i)-'A'+10;
            }
            var s = new StringBuilder(Integer.toBinaryString(v));
            while (s.length() < 4) {
                s.insert(0, "0");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    private static Packet parse(String str, int index, int endIndex, int packets) {
        var packet = new Packet();
        for (int i = index; i+7 < endIndex && i+7 < str.length();) {
            var v = Integer.parseInt(str.substring(i, i+3), 2);
            packet.versionSum += v;
            i += 3;
            var t = Integer.parseInt(str.substring(i, i+3), 2);
            i += 3;
            if (t == 4) {
                var value = new StringBuilder();
                while (true) {
                    boolean keepReading = str.charAt(i) == '1';
                    ++i;
                    value.append(str, i, i + 4);
                    i += 4;
                    if (!keepReading) {
                        break;
                    }
                }
                packet.values.add(Long.parseLong(value.toString(), 2));
            } else {
                var type = str.charAt(i);
                ++i;
                if (type == '0') {
                    int num = Integer.parseInt(str.substring(i, i+15), 2);
                    i += 15;
                    var subPacket = parse(str, i, i+num, -1);
                    packet.versionSum += subPacket.versionSum;
                    packet.values.add(handleValue(t, subPacket.values));
                    i += num;
                } else {
                    int num = Integer.parseInt(str.substring(i, i+11), 2);
                    i += 11;
                    var subPacket = parse(str, i, str.length(), num);
                    packet.versionSum += subPacket.versionSum;
                    packet.values.add(handleValue(t, subPacket.values));
                    i = subPacket.pos;
                }
            }
            if (--packets == 0) {
                packet.pos = i;
                return packet;
            }
        }
        packet.pos = endIndex;
        return packet;
    }

    private static long handleValue(int t, List<Long> values) {
        if (t == 0) {
            return values.stream().mapToLong(i -> i).sum();
        } else if (t == 1) {
            return values.stream().mapToLong(i -> i).reduce(1, (a, b) -> a*b);
        } else if (t == 2) {
            return values.stream().mapToLong(i -> i).min().orElseThrow();
        } else if (t == 3) {
            return values.stream().mapToLong(i -> i).max().orElseThrow();
        } else if (t == 5) {
            if (values.size() != 2) {
                throw new RuntimeException(String.format("Got wrong number of packets (%d) for greater than packet", values.size()));
            }
            return values.get(0) > values.get(1) ? 1 : 0;
        } else if (t == 6) {
            if (values.size() != 2) {
                throw new RuntimeException(String.format("Got wrong number of packets (%d) for greater than packet", values.size()));
            }
            return values.get(0) < values.get(1) ? 1 : 0;
        } else if (t == 7) {
            if (values.size() != 2) {
                throw new RuntimeException(String.format("Got wrong number of packets (%d) for greater than packet", values.size()));
            }
            return Objects.equals(values.get(0), values.get(1)) ? 1 : 0;
        }
        throw new RuntimeException(String.format("Unknown type %d", t));
    }

    public static class Packet {
        private final List<Long> values = new ArrayList<>();
        private int versionSum, pos;
    }
}

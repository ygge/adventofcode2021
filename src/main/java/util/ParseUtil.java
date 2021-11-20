package util;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ParseUtil {

    public static <T> long sumLong(Collection<T> collection, Function<T, Long> func) {
        return collection.stream()
                .map(func)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public static <T> int sum(Collection<T> collection, Function<T, Integer> func) {
        return collection.stream()
                .map(func)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static <T> int sum(List<T> list, BiFunction<T, Integer, Integer> func) {
        int sum = 0;
        for (int i = 0; i < list.size(); ++i) {
            sum += func.apply(list.get(i), i);
        }
        return sum;
    }
}

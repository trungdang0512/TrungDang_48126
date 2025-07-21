package utils;

import java.util.List;
import java.util.Random;

public class ListUtils {
    private static final Random RANDOM = new Random();

    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List không được rỗng");
        }
        int randomIndex = RANDOM.nextInt(list.size());
        return list.get(randomIndex);
    }
}

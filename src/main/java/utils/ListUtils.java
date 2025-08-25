package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ListUtils {
    private static final Random RANDOM = new Random();

    private static <T> int getRandomNumberOfElementsFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List does not empty!");
        }
        return RANDOM.nextInt(list.size()) + 1; // random from 1 to list.size()
    }

    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List does not empty!");
        }
        int randomIndex = RANDOM.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static <T> List<T> getRandomElements(List<T> list) {
        int numberOfElements = getRandomNumberOfElementsFromList(list);
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List does not empty!");
        }
        if (numberOfElements <= 0) {
            throw new IllegalArgumentException("Number of Element must > 0");
        }

        List<T> copy = new ArrayList<>(list);
        Collections.shuffle(copy, RANDOM);
        return copy.subList(0, Math.min(numberOfElements, copy.size()));
    }
}

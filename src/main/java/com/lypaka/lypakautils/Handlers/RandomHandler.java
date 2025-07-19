package com.lypaka.lypakautils.Handlers;

import java.util.Collection;
import java.util.Random;

public class RandomHandler {

    public static Random random = new Random();

    public static int getRandomNumberBetween (int min, int max) {

        return random.nextInt(Math.max(1, max - min + 1)) + min;

    }

    public static double getRandomNumberBetween (double min, double max) {

        return random.nextDouble(min, max) + min;

    }

    public static <T> T getRandomElementFromList (Collection<T> collection) {

        if (collection.isEmpty()) {

            return null;

        }

        int index = getRandomNumberBetween(0, collection.size() - 1);
        int counter = 0;

        for (T t : collection) {

            if (counter == index) {

                return t;

            }

            ++counter;

        }

        return null;

    }

    public static boolean getRandomChance (double chance) {

        return random.nextDouble() < chance;

    }

}

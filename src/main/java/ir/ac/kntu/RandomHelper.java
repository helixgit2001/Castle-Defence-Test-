package ir.ac.kntu;

import java.io.Serializable;

public final class RandomHelper implements Serializable {

    private static final java.util.Random RANDOM_CREATOR = new java.util.Random(System.nanoTime());
    private RandomHelper() {

    }
    public static boolean nextBoolean() {
        return RANDOM_CREATOR.nextBoolean();
    }
    public static int nextInt(int bound) {
        return RANDOM_CREATOR.nextInt(bound);
    }
}
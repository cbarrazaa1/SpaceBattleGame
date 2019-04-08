package better.core;

import java.util.Random;

/**
 * Utility
 * 
 * Helper class that provides utility methods.
 * @author Cesar Barraza
 * Date 08/Feb/2019
 * @version 1.0
 */
public class Util {
    /**
     * Generates a random number in a range.
     * @param min lower bound
     * @param max upper bound
     * @return a randon number
     */
    public static int randNum(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}

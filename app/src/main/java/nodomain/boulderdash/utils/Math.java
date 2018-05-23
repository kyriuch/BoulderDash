package nodomain.boulderdash.utils;

public class Math {
    public static <T extends Comparable<T>> T Clamp(T min, T max, T current) {
        if(current.compareTo(min) < 0) return min;
        if(current.compareTo(max) > 0) return max;

        return current;
    }

    public static int Lerp(int min, int max, double value) {
        return (int)(value * (max - min));
    }
}

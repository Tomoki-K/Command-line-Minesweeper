import java.math.BigDecimal;

/*==================== Timer class ====================*/

public class Timer {
    static long lngTime;
    static double dblTime;
    static long startTime;
    static long endTime;

    // start timer
    static void start() {
        startTime = System.nanoTime();
    }

    // stop timer
    static void end() {
        endTime = System.nanoTime();
        lngTime = endTime - startTime;
    }

    //return as second
    static double getTime() {
        BigDecimal bd = new BigDecimal(String.valueOf(lngTime / 1000000000.0));// nanosec -> sec
        dblTime = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // half up 1/100
        return dblTime;

    }

    @Override
    public String toString() {
        return String.valueOf(dblTime) + "seconds";
    }
}

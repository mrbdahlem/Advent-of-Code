package days.days2x.day25;

public class Encryptor {
    private final long publicKey;
    private final long loopSize;

    public Encryptor(long publicKey) {
        this.publicKey = publicKey;
        this.loopSize = findLoopSize(publicKey);
        // System.out.println(loopSize);
    }

    private static long findLoopSize(long publicKey) {
        long ls = 0;
        long subjectNumber = 7;
        long value = 1;
        long divisor = 20201227;

        while (value != publicKey) {
            value = (value * subjectNumber) % divisor;
            ls++;
        }

        return ls;
    }

    public long getLoopSize() {
        return loopSize;
    }

    public long getKey(long loopSize) {
        long subjectNumber = this.publicKey;
        long value = 1;
        long divisor = 20201227;

        for (int loop = 0; loop < loopSize; loop++) {
            value = (value * subjectNumber) % divisor;
        }

        return value;
    }
}
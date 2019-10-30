package number_theoretic_methods_in_cryptography.task3.primeNumbers;

public abstract class PrimaryTest {
    public abstract boolean test();

    private long digit;

    public long degree(long dig, long deg) {
        if (deg == 2)
            return dig * dig;
        long n = ((deg * 2) + 1);
        long result = dig;

        for (int i = 1; i <= deg - 1; i++) {
            result = ((result * dig)) % digit;
        }
        return result;
    }
}

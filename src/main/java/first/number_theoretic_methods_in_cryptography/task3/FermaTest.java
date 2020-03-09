package first.number_theoretic_methods_in_cryptography.task3;

public class FermaTest {
    private static int q = 1;
    long a;
    long p;

    public FermaTest(long digit) {
        this.p = digit;
        this.a = chooseHelpDigit(digit);
    }

    private int chooseHelpDigit(long p) {

        while (true) {
            q++;
            if (p % q != 0)
                break;
        }
        return q;
    }

    private long degree(long p) {
        long result = a;
        for (int i = 1; i < p - 1; i++) {
            result = (result * a) % p;
        }
        return result;
    }

    public boolean test() {
        long c = degree(p) - 1;
        c = c % p;
        if (c == 0)
            return true;
        return false;
    }

}
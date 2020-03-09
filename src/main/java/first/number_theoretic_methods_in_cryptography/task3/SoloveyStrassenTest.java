package first.number_theoretic_methods_in_cryptography.task3;

public class SoloveyStrassenTest {

    private long k;
    private long n;

    public SoloveyStrassenTest(long n, long k) {
        this.n = n;
        this.k = k;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    private long degree(long dig, long deg) {
        if (deg == 2)
            return dig * dig;
        long m = ((deg * 2) + 1);
        long result = dig;

        for (int i = 1; i <= deg - 1; i++) {
            result = (result * dig) % m;
        }
        return result;
    }

    public boolean test() {
        for (int i = 0; i < k; i++) {
            long a = (long) (Math.random() * (n - 2));
            while (a <= 2)
                a = (long) (Math.random() * (n - 2));
            long w = (a + n - 1) / (n);//Jakobi

            if (gcd(a, n) > 1)
                return false;
            else {
                if (((degree(a, (n - 1) / 2) + (n - 1)) % n) == w)
                    return false;
            }
        }
        return true;
    }
}
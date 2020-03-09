package first.number_theoretic_methods_in_cryptography.task3;


public class MillerRabinTest {
    long digit, count;

    private long degree(long dig, long deg) {
        if (deg == 2)
            return dig * dig;
        long n = ((deg * 2) + 1);
        long result = dig;

        for (int i = 1; i <= deg - 1; i++) {
            result = ((result * dig)) % digit;
        }
        return result;
    }


    public MillerRabinTest(long c, long k) {
        this.digit = c;
        this.count = k;
    }

    public boolean test() {

        if (digit == 2 || digit == 3)
            return true;
        // если n < 2 или n четное - возвращаем false
        if (digit < 2 || digit % 2 == 0)
            return false;

        long n = (digit - 1);
        long deg = 0, k = n, d = 0;

        while (k % 2 == 0) {
            k /= 2;
            deg++;
            d = k;
        }

        for (int j = 0; j < count; j++) {
            long a = (int) (Math.random() * n);
            long x = degree(a, d);
            if (x == 1 || x == digit - 1)
                return true;
            for (int i = 2; i <= deg; i++) { //s-1 раз делаем возведение в квадрат по модулю. Если оостаток 1 то составное
                x = degree(x, i);
                if (x == 1)
                    return false;
                if (x == digit - 1)
                    return false;
            }
        }
        return false;
    }
}

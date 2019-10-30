package number_theoretic_methods_in_cryptography.task4;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import number_theoretic_methods_in_cryptography.task1.Euclid;
import number_theoretic_methods_in_cryptography.task1.WrongInputException;

import java.util.ArrayList;

import static number_theoretic_methods_in_cryptography.task1.Euclid.GCD;

@AllArgsConstructor
@NoArgsConstructor
public class PollardP {
    private long n;
    private long c;
    private Euclid euclid;
    long a;
    long b;

    private long function(long x) {
        return ((x * x) % this.n + 5) % this.n;
    }

    public PollardP(long n, long c) {
        this.n = n;
        this.c = c;
        this.euclid = new Euclid();
        a = c;
        b = c;
    }

    public long methodP() throws WrongInputException {
        a = function(a) % n;
        b = function(b) % n;
        b = function(b) % n;
        if (a - b > 0) {
            long d = GCD(a - b, n);
            if (1 < d && d < n)
                return d;
            else if (d == 1)
                return methodP();
            else
                return -1;
        } else return methodP();
    }

    public long methodP_Probaple(long n, double e) throws WrongInputException {
        Double t = Math.sqrt(2 * Math.sqrt(n) * Math.log(1 / e)) + 1;
        ArrayList<Long> l = new ArrayList<>();
        long x0 = (long) ((Math.random() * n) % n);
        for (int i = 0; i < t.longValue(); i++) {
            long xi = function(x0);
            l.add(xi);
            x0 = xi;
            for (int k = 0; k < l.size(); k++) {
                Long dk = Help.gcd(xi - l.get(k), n);
                if (dk == n)
                    x0 = (long) ((Math.random() * n) % n);
                if (dk < n && dk > 1)
                    return dk;
            }
        }
        double rand = Math.random() * 100;
        if (rand < e * 100)
            return 1;
        else return methodP();

    }
}

package number_theoretic_methods_in_cryptography.task2;

import com.google.common.collect.Lists;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class FFT {

    public static List<Complex> fft(List<Complex> a, boolean invert) {
        int n = a.size();
        if (n == 1)
            return new ArrayList<>();
        ArrayList<Complex> a0 = Lists.newArrayListWithExpectedSize(n / 2);
        ArrayList<Complex> a1 = Lists.newArrayListWithCapacity(n / 2);

        for (int i = 0; i < n / 2; i++) {
            a0.add(new Complex(0));
            a1.add(new Complex(0));
        }

        for (int i = 0, j = 0; i < n - 1; i += 2, ++j) {
            a0.set(j, a.get(i));
            a1.set(j, a.get(i + 1));
        }
        fft(a0, invert);
        fft(a1, invert);

        double ang = 2 * PI / n * (invert ? -1 : 1);
        Complex w = new Complex(1);
        Complex wn = new Complex(cos(ang), sin(ang));

        for (int i = 0; i < n / 2; ++i) {
            a.set(i, a0.get(i).add(w.multiply(a1.get(i))));
            a.set(i + n / 2, a0.get(i).subtract(w.multiply(a1.get(i))));
            if (invert) {
                a.set(i, a.get(i).divide(2));
                a.set(i + n / 2, a.get(i + n / 2).divide(2));
            }
            w = w.multiply(wn);
        }
        return a;
    }


    public static List<Long> multiply(List<Complex> a, List<Complex> b) {
        List<Complex> fa = Lists.newArrayList(a);
        List<Complex> fb = Lists.newArrayList(b);
        int count = fa.size() - 1 + fb.size() - 1;
        long n = 1;
        while (n < max(a.size(), b.size()))
            n *= 2;
        n *= 2;
        while (fa.size() != n)
            fa.add(new Complex(0));
        while (fb.size() != n)
            fb.add(new Complex(0));
        fa = fft(fa, false);
        fb = fft(fb, false);
        for (int i = 0; i < n; i++)
            fa.set(i, fa.get(i).multiply(fb.get(i)));
        fa = fft(fa, true);

        List<Long> result = Lists.newArrayList();
        for (int i = 0; i <= count; i++) {
            result.add((long) (fa.get(i).getReal() + 0.5));
        }
        return result;
    }

    public static List<Long> transport(List<Long> res) {
        List<Long> tmp = Lists.reverse(res);
        long carry = 0;

        for (int i = 0; i < tmp.size() - 1; i++) {
            if (carry != 0)
                tmp.set(i, tmp.get(i) + carry);
            if (tmp.get(i) > 9) {
                carry = tmp.get(i) / 10;
                tmp.set(i, tmp.get(i) % 10);
            }else carry=0;
        }
        tmp.set(tmp.size() - 1, tmp.get(tmp.size() - 1) + carry);
        return Lists.reverse(tmp);
    }


    public static List<Complex> digitAsList(Long d) {
        List<Complex> res = Lists.newArrayList();
        while (d > 0) {
            res.add(new Complex(d % 10));
            d /= 10;
        }
        return Lists.reverse(res);
    }

    public static void printDigit(List<Long> list) {
        for (Long aLong : list) {
            System.out.print(aLong);
        }
    }
}

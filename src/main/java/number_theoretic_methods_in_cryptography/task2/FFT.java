package number_theoretic_methods_in_cryptography.task2;

import com.google.common.collect.Lists;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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

//    typedef complex<double> base;
//
//    void fft (vector<base> & a, bool invert) {
//        int n = (int) a.size();
//        if (n == 1)  return;
//
//        vector<base> a0 (n/2),  a1 (n/2);
//        for (int i=0, j=0; i<n; i+=2, ++j) {
//            a0[j] = a[i];
//            a1[j] = a[i+1];
//        }
//        fft (a0, invert);
//        fft (a1, invert);
//
//        double ang = 2*PI/n * (invert ? -1 : 1);
//        base w (1),  wn (cos(ang), sin(ang));
//        for (int i=0; i<n/2; ++i) {
//            a[i] = a0[i] + w * a1[i];
//            a[i+n/2] = a0[i] - w * a1[i];
//            if (invert)
//                a[i] /= 2,  a[i+n/2] /= 2;
//            w *= wn;
//        }
//    }


}

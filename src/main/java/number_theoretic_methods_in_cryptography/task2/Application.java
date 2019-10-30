package number_theoretic_methods_in_cryptography.task2;

import com.google.common.collect.Lists;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

import static number_theoretic_methods_in_cryptography.task2.FFT.fft;

public class Application {

    public static void main(String[] args) {
        List<Complex> list = Lists.newArrayList();
        list.add(new Complex(10));
        list.add(new Complex(213));
        list.add(new Complex(2));
        list.add(new Complex(3));

        List<Complex> transformed = fft(list, false);
        System.out.println(transformed);
        List<Complex> finalList = fft(transformed, true);
        System.out.println(finalList);
    }
}

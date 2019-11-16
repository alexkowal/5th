package number_theoretic_methods_in_cryptography.task2;

import com.google.common.collect.Lists;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

import static number_theoretic_methods_in_cryptography.task2.FFT.*;

public class Application {

    public static void main(String[] args) {
        List<Complex> list,
                list2;

//        Long d = 157L;
//        Long d2 = 171L;
        Long d = 87654321L;
        Long d2 = 2112111121121111L;
        list = digitAsList(d);
        list2 = digitAsList(d2);

        //Быстрое преобразование Фурье
        List<Complex> transformed = fft(list, false);
        System.out.println(transformed);

        //Обратное быстрое преобразование Фурье
        List<Complex> finalList = fft(transformed, true);
        System.out.println(finalList);


        //Умножение 2 чисел
        List<Long> r = multiply(list, list2);
        r = transport(r);
        printDigit(r);
    }
}

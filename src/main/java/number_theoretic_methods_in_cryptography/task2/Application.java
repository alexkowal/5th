package number_theoretic_methods_in_cryptography.task2;

import com.google.common.collect.Lists;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

import static number_theoretic_methods_in_cryptography.task2.FFT.*;

public class Application {

    public static void main(String[] args) {
        System.out.println();
        List<Complex> list,
                list2;

        Long d = 12345678l;
        Long d2 = 87654321L;

        list = digitAsList(d);
        list2 = digitAsList(d2);

        //Быстрое преобразование Фурье
        long startTime = System.currentTimeMillis();
        List<Complex> transformed = fft(list, false);
        long f = System.currentTimeMillis() - startTime;
        System.out.println(transformed);
        System.out.println("Время выполнения прямого преобразования: " + f);
        System.out.println();


        //Обратное быстрое преобразование Фурье
        startTime = System.nanoTime();
        List<Complex> finalList = fft(transformed, true);
        System.out.println(finalList);
        f = System.nanoTime() - startTime;
        System.out.println("Время выполнения обратного преобразования: " + f);
        System.out.println();



        //Умножение 2 чисел
        System.out.println();
        startTime = System.currentTimeMillis();

        List<Long> r = multiply(list, list2);
        r = transport(r);
        printDigit(r);
        System.out.println();
        f = System.currentTimeMillis() - startTime;
        System.out.println("Время выполнения умножения 2 чисел: " + f);
        System.out.println();

    }
}

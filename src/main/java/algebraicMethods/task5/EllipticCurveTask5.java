package algebraicMethods.task5;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

import static algebraicMethods.task1.EllipticCurve.summ;
import static algebraicMethods.task1.Help.generateEllipticCurve;

public class EllipticCurveTask5 {

    public static void main(String[] args) throws IOException {
        // Страница 324
        System.out.println("Введите число k для вычисления значения функции h(k)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Long k = Long.valueOf(br.readLine());
        int n = Long.toBinaryString(k).length();
        System.out.println("Генерация эллиптической кривой " + n + " бит");
        ArrayList<Object> result = generateEllipticCurve(n);
        Pair<BigInteger, BigInteger> q = (Pair<BigInteger, BigInteger>) result.get(0);
        BigInteger p = (BigInteger) result.get(1);

        long module = getModule(n);
        long e = q.getKey().mod(BigInteger.valueOf(module)).longValue();
        long factor = module * k + e;

        Pair<BigInteger, BigInteger> r = q;
        for (int i = 0; i < factor; i++)
            r = summ(r, r, p);
        System.out.println(r.getKey());
    }

    private static long getModule(int n) {
        long k = 2;
        for (int i = 0; i < n - 1; i++)
            k *= 2;
        return k;
    }
}

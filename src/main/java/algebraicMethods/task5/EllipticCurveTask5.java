package algebraicMethods.task5;

import algebraicMethods.task1.EllipticCurve;
import algebraicMethods.task1.Help;
import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

import static algebraicMethods.task1.EllipticCurve.summ;
import static java.math.BigInteger.ONE;

public class EllipticCurveTask5 {
    private static ArrayList<Object> generateEllipticCurve(int L) {
        EllipticCurve ellipticCurve = new EllipticCurve();
        while (true) {
            while (true) {
                ellipticCurve.findP(L);
                if (!ellipticCurve.faction(ONE)) continue; // проблема тут?
                if (ellipticCurve.verify() && ellipticCurve.check(5)) break;
            }

            int k = 0;
            boolean f = false;

            while (true) {
                if (k++ == Help.k) {
                    f = true;
                    break;
                }
                if (!ellipticCurve.generate()) continue;
                if (ellipticCurve.checkXY()) {
                    ellipticCurve.generateQ();
                    break;
                }
            }

            if (!f) {
                Help.printPoints(ellipticCurve.getQ(), ellipticCurve.getP(), ellipticCurve.getR());
                Help.printParams(ellipticCurve.getP(), ellipticCurve.getQ(), ellipticCurve.getR(), ellipticCurve.getBs());
                return Lists.newArrayList(ellipticCurve.getQ(), ellipticCurve.getP());
            }
        }
    }

    public static void main(String[] args) throws IOException {
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

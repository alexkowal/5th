package first.algebraicMethods.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.math.BigInteger.ONE;

public class Starter {
    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите L - количество бит");
        String s1 = reader.readLine();
        if (s1.equals("") || !isNumber(s1)) {
            System.out.println("Введите число!!");
            return;
        }
        int L = Integer.parseInt(s1);
        if (L <= 5) {
            System.out.println("Ошибка! Число бит должно быть > 4 и != 5");
            return;
        }
        EllipticCurve ellipticCurve = new EllipticCurve();
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
            System.exit(0);
        }

    }

    static boolean isNumber(String s) {
        char[] a = s.toCharArray();

        if (a[0] == '0' && a.length > 1)
            return false;

        for (int i = 0; i < a.length; i++) {

            if (a[i] < '0' || a[i] > '9')
                return false;
        }
        return true;
    }


}

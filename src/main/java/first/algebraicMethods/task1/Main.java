package first.algebraicMethods.task1;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Starter starter = new Starter();
        starter.start();



       /* BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите L - количество бит");
        String s1 = reader.readLine();
        if (s1.equals("") || !isNumber(s1)) {
            System.out.println("Введите число!!");
            return;
        }
        int L = Integer.parseInt(s1);
        if (L <= 4) {
            System.out.println("Ошибка! Число бит должно быть > 4 и != 5");
            return;
        }

        while (true) {
            EllipticCurve ellipticCurve = new EllipticCurve();

            while (true) {
                ellipticCurve.findP(L);
                System.out.println("P = " + ellipticCurve.getP());
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
                break;
            }
        }
*/
    }

}

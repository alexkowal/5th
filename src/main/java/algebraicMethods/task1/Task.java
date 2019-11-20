package algebraicMethods.task1;

import static java.math.BigInteger.ONE;

class Task implements Runnable {
    Integer L;

    public Task(Integer L) {
        this.L = L;
    }

    @Override
    public void run() {
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
                System.exit(0);
            }
        }
    }
}
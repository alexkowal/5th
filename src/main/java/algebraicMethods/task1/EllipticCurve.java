package algebraicMethods.task1;

import javafx.util.Pair;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EllipticCurve {
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");
    private static final BigInteger FOUR = new BigInteger("4");
    private BigInteger bs;
    private BigInteger p;
    private BigInteger N;
    private BigInteger r;
    private static BigInteger a = BigInteger.ONE;
    private BigInteger b;
    private Pair<BigInteger, BigInteger> q;
    private BigInteger x0;
    private BigInteger y0;
    private int L;
    Help help = new Help();


    public void findP(int L) {
        this.L = L;
        BigInteger p;
        do {
            p = help.generatePrime(L);
        }
        while (!Help.isPrime(p, L, 20) || p.toString(2).toCharArray()[0] != '1' || !p.mod(FOUR).equals(BigInteger.ONE));
        this.p = p;
    }

    public boolean faction(BigInteger d) {
        BigInteger jacobi = Help.jacobi(d.negate(), p);
        if (jacobi.equals(BigInteger.valueOf(-1))) {
            return false;
        }
        // 2.1
        Pair<BigInteger, BigInteger> u = Help.squareRoot(d.negate(), p);
        // 2.2 Тоннели Шенкс

        BigInteger ui = u.getKey();
        BigInteger mi = p;
        int i = 0;
        // 2.3
        List<BigInteger> uis = new ArrayList<>();
        uis.add(ui);
        int cc = 10000;
        while (cc > 0) {
            cc--;
            if (i >= Help.k) {
                return false;
            }
            if (mi.equals(BigInteger.ZERO)) {
                return false;
            }

            mi = (uis.get(i).pow(2).add(d)).divide(mi);
            ui = (ui.mod(mi)).min(mi.subtract(ui).mod(mi));
            // 2.4
            uis.add(ui);
            if (mi.equals(BigInteger.ONE)) {
                break;
            } else {
                i++;
            }
            // 2.5
        }

        BigInteger ai = uis.get(uis.size() - 2);
        BigInteger bi = BigInteger.ONE;
        // 2.6
        BigInteger aiCopy = new BigInteger(ai.toString());

        while (true) {
            if (i == 0) {
                if (!ai.pow(2).add(bi.pow(2)).equals(p))
                    return false;
                //2.7.1
                this.a = ai;
                this.b = bi;
                return true;
            }

            if ((uis.get(i - 1).multiply(ai).add(d.multiply(bi))).mod((ai.pow(2)).add(d.multiply(bi.pow(2)))).equals(BigInteger.ZERO)) {
                ai = (uis.get(i - 1).multiply(ai).add(d.multiply(bi))).divide((ai.pow(2)).add(d.multiply(bi.pow(2))));
            } else {
                ai = (uis.get(i - 1).negate().multiply(ai).add(d.multiply(bi))).divide((ai.pow(2)).add(d.multiply(bi.pow(2))));
            }
            if ((uis.get(i - 1).multiply(bi).subtract(aiCopy)).mod((aiCopy.pow(2)).add(d.multiply(bi.pow(2)))).equals(BigInteger.ZERO)) {
                bi = (uis.get(i - 1).multiply(bi).subtract(aiCopy)).divide((aiCopy.pow(2)).add(d.multiply(bi.pow(2))));
            } else {
                bi = (uis.get(i - 1).negate().multiply(bi).subtract(aiCopy)).divide((aiCopy.pow(2)).add(d.multiply(bi.pow(2))));
            }
            //2.7.2
            aiCopy = new BigInteger(ai.toString());
            i--;
            //2.8
        }
    }

    public boolean verify() {
        List<BigInteger> T = new ArrayList<>();
        T.add(a.multiply(new BigInteger("2")));
        T.add(b.multiply(new BigInteger("2")));
        T.add(a.multiply(new BigInteger("2")).negate());
        T.add(b.multiply(new BigInteger("2")).negate());


        BigInteger N;
        for (BigInteger t : T) {
            N = p.add(BigInteger.ONE).add(t);

            if (N.mod(FOUR).equals(BigInteger.ZERO)
                    && N.divide(FOUR).isProbablePrime(20)) {
                BigInteger r = N.divide(FOUR);
                this.N = N;
                this.r = r;
                return true;
            }

            if (N.mod(TWO).equals(BigInteger.ZERO)
                    && N.divide(TWO).isProbablePrime(20)) {
                BigInteger r = N.divide(TWO);
                this.N = N;
                this.r = r;
                return true;
            }
        }
        return false;
    }

    public boolean check(int m) {
        if (L == 4) {
            return true;
        }
        if (p.equals(r)) {
            return false;
        }
        for (int i = 1; i <= m; i++) {
            if (p.modPow(BigInteger.valueOf(i), r).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }


    static boolean isQuad(BigInteger a, BigInteger p, boolean not) {
        BigInteger check;
        BigInteger ONE = BigInteger.valueOf(1);
        BigInteger TWO = BigInteger.valueOf(2);
        check = a.modPow((p.subtract(ONE)).divide(TWO), p);
        if (!not && check.toString().equals("1"))
            return true;
        if (not && check.compareTo(p.subtract(BigInteger.ONE)) == 0)
            return true;
        return false;
    }

    public boolean generate() {
        int l = p.bitLength();
        BigInteger x0;
        BigInteger y0;

        Pair<BigInteger, BigInteger> xy = generateXY(l);
        x0 = xy.getKey();
        y0 = xy.getValue();
        if (x0.equals(BigInteger.ZERO) || y0.equals(BigInteger.ZERO)) {
            return false;
        }

        BigInteger xInverse = x0.modInverse(p);
        BigInteger a = y0.pow(2).subtract(x0.pow(3)).multiply(xInverse).mod(p);

        if (r.multiply(TWO).equals(N) && isQuad(a.negate(),p,false)){
            this.bs = a;
            this.x0 = x0;
            this.y0 = y0;
            return true;
        }
;
        if (r.multiply(FOUR).equals(N) && isQuad(a.negate(),p,true)){
            this.bs = a;
            this.x0 = x0;
            this.y0 = y0;
            return true;
        }
        return false;
    }

    public boolean checkXY() {
        Pair<BigInteger, BigInteger> point = new Pair<>(x0, y0);

        for (BigInteger i = BigInteger.ONE; i.compareTo(N.subtract(BigInteger.ONE)) < 0; i = i.add(BigInteger.ONE)) {
            point = summ(point, new Pair<>(x0, y0), p);
            if (point == null) {
                return false;
            }
        }
        return summ(point, new Pair<>(x0, y0), p) == null;
    }

    public static Pair<BigInteger, BigInteger> summ(Pair<BigInteger, BigInteger> first, Pair<BigInteger, BigInteger> second,
                                                    BigInteger p) {
        try {
            if (first == null || second == null) {
                return null;
            }

            BigInteger l;
            BigInteger x1 = first.getKey();
            BigInteger y1 = first.getValue();
            BigInteger x2 = second.getKey();
            BigInteger y2 = second.getValue();

            if (x1.equals(x2) && y1.equals(y2)) {
                if (y1.equals(BigInteger.ZERO)) {
                    return null;
                } else {
                    l = x1.pow(2).multiply(THREE).add(a).multiply((TWO.multiply(y1)).modInverse(p)).mod(p);
                }
            } else {
                l = (y2.subtract(y1)).multiply((x2.subtract(x1)).modInverse(p)).mod(p);
            }

            BigInteger x3 = l.pow(2).subtract(x1).subtract(x2).mod(p);
            BigInteger y3 = (x1.subtract(x3)).multiply(l).subtract(y1).mod(p);
            return new Pair<>(x3, y3);
        } catch (ArithmeticException e) {
            return null;
        }
    }

    public void generateQ() {
        Pair<BigInteger, BigInteger> point = new Pair<>(x0, y0);

        for (int i = 0; i < N.divide(r).intValue() - 1; i++) {
            point = summ(point, new Pair<>(x0, y0), p);
        }
        this.q = point;
    }

    public BigInteger getBs() {
        return bs;
    }

    public Pair<BigInteger, BigInteger> getQ() {
        return q;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getR() {
        return r;
    }

    public Pair<BigInteger, BigInteger> generateXY(int l) {
        BigInteger x0 = new BigInteger(new Random().nextInt(l + 1) + l / 2 + 1, 20, new SecureRandom()).mod(p);
        BigInteger y0 = new BigInteger(new Random().nextInt(l + 1) + l / 2 + 1, 20, new SecureRandom()).mod(p);
        return new Pair<>(x0, y0);
    }

}

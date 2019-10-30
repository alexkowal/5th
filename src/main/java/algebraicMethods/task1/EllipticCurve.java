package algebraicMethods.task1;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Random;


@NoArgsConstructor
public class EllipticCurve {
    public static Random random = new Random();
    private BigInteger ONE = BigInteger.ONE;
    private BigInteger ZERO = BigInteger.ZERO;
    SqrtExtractor extractor = new SqrtExtractor();

    public boolean isPrime(BigInteger number) {
        if (!number.isProbablePrime(5))
            return false;

        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
            if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
                return false;
        }
        return true;
    }

    //l - bits in digit
    public BigInteger findPrimeDigit(Integer l) {
        BigInteger digit;
        while (true) {
            digit = BigInteger.probablePrime(l, random);
            if (digit.mod(BigInteger.valueOf(4)).equals(ONE) && isFirstBitNotNull(digit))
                break;
        }
        return digit;
    }

    public boolean s() {
        BigInteger p = findPrimeDigit(10);
        numberExpansion(p);
        return true;
    }

    private BigInteger[] numberExpansion(BigInteger p) {
        BigInteger lezhandr = ONE.divide(p);
        if (lezhandr.equals(ONE.multiply(new BigInteger("-1"))))
            return null;
        //extractor.chooseModeAndExpand(lezhandr, p);
        return null;
    }

    public boolean isFirstBitNotNull(BigInteger digit) {
        String s = digit.toString(2);
        System.out.println(s);
        return s.charAt(0) == '1';
    }
}

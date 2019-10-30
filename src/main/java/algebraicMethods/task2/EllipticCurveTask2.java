package algebraicMethods.task2;
import java.math.BigInteger;

import java.math.BigInteger;
import java.util.Random;

public class EllipticCurveTask2 {

    private static Random random;

    public EllipticCurveTask2() {
        this.random = new Random();
    }

    private static boolean isPrime(BigInteger number) {
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
    private static BigInteger findPrimeDigit(Integer l) {
        while (true) {
            BigInteger digit = BigInteger.probablePrime(l, random);
        }
    }

}

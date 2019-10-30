package algebraicMethods.task1;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

public class Appication {
    public static void main(String[] args) {
        EllipticCurve curve = new EllipticCurve();
        System.out.println(curve.findPrimeDigit(16));
        SqrtExtractor e = new SqrtExtractor();
        System.out.println(e.exp(new BigInteger("46")).get(0) + "\n" + e.exp(new BigInteger("46")).get(1));
    }
}

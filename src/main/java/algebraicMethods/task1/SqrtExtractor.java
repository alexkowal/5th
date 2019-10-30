package algebraicMethods.task1;

import com.google.common.collect.Lists;

import java.math.BigInteger;
import java.util.ArrayList;

public class SqrtExtractor {

    public BigInteger mod3(BigInteger p, BigInteger a) {
        return new BigInteger("0");
    }

    public void expand(BigInteger p, BigInteger n) {
        BigInteger temp = p.subtract(BigInteger.ONE);
        Long s = (Long) exp(temp).get(0);
        BigInteger q = (BigInteger) exp(temp).get(1);
    }


    public ArrayList<? extends Number> exp(BigInteger p) {
        Long s = Long.valueOf(0);
        while (p.divide(new BigInteger("2")) != BigInteger.valueOf(0) && p.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
            s++;
            p = p.divide(new BigInteger("2"));
        }
        ArrayList<? extends Number> list = Lists.newArrayList(s, p);
        return list;
    }

}

package number_theoretic_methods_in_cryptography.task1;

import java.math.BigInteger;

public class Chinese {

    public static String chineseTheorem(int k, int[] a, int[] mi) {
        Long M = 1L;
        Long[] Mi = new Long[mi.length];
        Long[] miRevert = new Long[mi.length];
        for (int i = 0; i < k; i++) {
            M *= mi[i];
        }
        for (int i = 0; i < k; i++) {
            Mi[i] = M / mi[i];
            miRevert[i] = BigInteger.valueOf(Mi[i]).modInverse(BigInteger.valueOf(mi[i])).longValue();
        }
        Long x = 0L;
        for (int i = 0; i < k; i++) {
            x += BigInteger.valueOf(a[i] * Mi[i] * miRevert[i]).mod(BigInteger.valueOf(M)).longValue();
        }
        x  = BigInteger.valueOf(x).mod(BigInteger.valueOf(M)).longValue();
        return "chineseTheorem:" + x;
    }


}


package number_theoretic_methods_in_cryptography.task1;

import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;

import static com.google.common.primitives.Longs.min;


@Data
public class Garner {
    static String garnerMethod(int k, int[] a, int[] m) {
        int[] miRevert = new int[m.length];
        int[] mu = new int[m.length];
        int M = 0;
        for (int i = 1; i < k; i++) {
            M = m[0];
            for (int j = 1; j < i; j++) {
                M *= m[j];
            }
            mu[i] = M;
            miRevert[i] = BigInteger.valueOf(mu[i]).modInverse(BigInteger.valueOf(m[i])).intValue();
        }
        M = mu[k - 1] * m[k - 1];
        int temp;
        int v = a[0];
        for (int i = 1; i < k; i++) {
            temp = BigInteger.valueOf((a[i] - v) * miRevert[i]).mod(BigInteger.valueOf(m[i])).intValue();
            v += temp * mu[i];
        }
        v = BigInteger.valueOf(v).mod(BigInteger.valueOf(M)).intValue();
        return "garnerMethod:" + v;
    }
}

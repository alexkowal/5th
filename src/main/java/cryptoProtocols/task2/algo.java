package cryptoProtocols.task2;

import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;
import java.util.Scanner;

public class algo {

    public static long GetPRoot(long p) {
        for (long i = 0; i < p; i++)
            if (IsPRoot(p, i))
                return i;
        return 0;
    }

    public static boolean IsPRoot(long p, long a) {
        if (a == 0 || a == 1)
            return false;
        long last = 1;

        Set<Long> set = new HashSet<>();
        for (long i = 0; i < p - 1; i++) {
            last = (last * a) % p;
            if (set.contains(last)) // Если повтор
                return false;
            set.add(last);
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        BigInteger p, b, c, secretKey;
        Random sc = new SecureRandom();
        secretKey = new BigInteger("1234");
        //
        // public key calculation
        //
        System.out.println("secretKey = " + secretKey);
        p = BigInteger.probablePrime(20, sc);
        System.out.println("ROOT = " + GetPRoot(p.longValue()));
        BigInteger g = BigInteger.valueOf(GetPRoot(p.longValue()));
        BigInteger x = p.multiply(BigInteger.valueOf((long) (Math.random() * p.longValue()))).mod(p.subtract(BigInteger.ONE));
        BigInteger y = g.modPow(x, p);
        System.out.println("y = " + y.longValue());

        b = new BigInteger("3");
        c = b.modPow(secretKey, p);
        System.out.println("p = " + p);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        //
        // Encryption
        //
        System.out.print("Введите сообщение (большое число) -->");
        String s = scan.next();
        BigInteger X = new BigInteger(s);
        BigInteger r = new BigInteger(64, sc);
        BigInteger EC = X.multiply(c.modPow(r, p)).mod(p);
        BigInteger brmodp = b.modPow(r, p);
        System.out.println("Сообщение = " + X);
        System.out.println("целое число r такое, что 1 < r < p ---> r = " + r);
        //System.out.println("EC = " + EC);
        System.out.println("Первая часть зашифрованного сообщения b^r mod p = " + brmodp);
        //
        // Decryption
        //
        BigInteger crmodp = brmodp.modPow(secretKey, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(EC).mod(p);
        System.out.println("Вторая часть зашифрованного сообщения c^r mod p = " + crmodp);
        //System.out.println("d = " + d);
        System.out.println("Дешифрованное сообщение: " + ad);

    }
}
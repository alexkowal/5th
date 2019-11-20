package cryptoProtocols.task3;

import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class DiffieHellman {

    private static final String COMMON_PARAMETERS_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 3/common-parameters.txt";
    private static final String ALICE_X = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 3/alice-x.txt";
    private static final String BOB_Y = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 3/bob-y.txt";
    private static final String K_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 3/k.txt";
    private static final String X_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 3/x.txt";
    private static final String Y_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 3/y.txt";


    private static final String K_BOB_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 3/k_bob.txt";

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private BigInteger x;
    private BigInteger y;
    private BigInteger n;
    private Random sc;

    public DiffieHellman() throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(COMMON_PARAMETERS_FILE_PATH));
        sc = new SecureRandom();

    }

    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        DiffieHellman diffieHellman = new DiffieHellman();
        while (true) {
            System.out.println("Выберите шаг алгоритма: 1 - генерация общих параметров, 2 - генерация x Alice" +
                    ", 3 -  генерация y Bob, 4 - вычисление общего ключа для Алисы, 5 - вычисление общего ключа для Алисы");
            Integer mode = Integer.valueOf(consoleReader.readLine());
            if (mode == 1)
                diffieHellman.generateCommonParameters();
            if (mode == 2) {
                diffieHellman.generateXForAlice();
            }
            if (mode == 3)
                diffieHellman.generateYForBob();

            if (mode == 4) {
                diffieHellman.generateKeyForAlice();

            }
            if (mode == 5) {
                diffieHellman.generateKeyForBob();
            }
        }
    }

    public void generateCommonParameters() throws IOException {
        this.bufferedWriter = new BufferedWriter(new FileWriter(COMMON_PARAMETERS_FILE_PATH));

        Pair<BigInteger, BigInteger> pair = generateNAndG();
        BigInteger n = pair.getKey();
        BigInteger g = pair.getValue();

        while (!n.isProbablePrime(200) || !g.isProbablePrime(200) || !n.gcd(g).equals(BigInteger.ONE)) {
            pair = generateNAndG();
            n = pair.getKey();
            g = pair.getValue();

            while (!g.mod(n).isProbablePrime(200)) {
                g = new BigInteger(50, sc).mod(n);
            }

        }
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(COMMON_PARAMETERS_FILE_PATH));
            bufferedWriter.write(n.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(g.toString());
            bufferedWriter.newLine();
        } finally {
            bufferedWriter.close();
        }
    }

    public void protocol() throws IOException {
        BigInteger n = new BigInteger(bufferedReader.readLine());
        BigInteger g = new BigInteger(bufferedReader.readLine());
        this.n = n;
        BigInteger x = g.multiply(new BigInteger(String.valueOf(Math.abs((long) ((Math.random() * g.longValue()))))));
        this.x = x;
        BigInteger X = g.modPow(x, n);
        bufferedWriter = new BufferedWriter(new FileWriter(ALICE_X));
        bufferedWriter.write(X.toString());
        bufferedWriter.close();

        BigInteger y = g.multiply(new BigInteger(String.valueOf(Math.abs((long) ((Math.random() * g.longValue()))))));
        this.y = y;
        BigInteger Y = g.modPow(y, n);
        bufferedWriter = new BufferedWriter(new FileWriter(BOB_Y));
        bufferedWriter.write(Y.toString());
        bufferedWriter.close();


        this.bufferedReader = new BufferedReader(new FileReader(ALICE_X));
        BigInteger readed_x = new BigInteger(bufferedReader.readLine());


        this.bufferedReader = new BufferedReader(new FileReader(BOB_Y));
        BigInteger readed_y = new BigInteger(bufferedReader.readLine());


        BigInteger K = readed_y.modPow(x, n);
        BigInteger Ks = readed_x.modPow(y, n);

        bufferedWriter = new BufferedWriter(new FileWriter(K_FILE_PATH));
        bufferedWriter.write(K.toString());
        bufferedWriter.newLine();
        bufferedWriter.write(Ks.toString());
        bufferedWriter.close();
    }

    private Pair<BigInteger, BigInteger> generateNAndG() {
        BigInteger q;
        BigInteger p = ONE;

        while (!p.isProbablePrime(200)) {

            q = new BigInteger(3, sc);
            for (int k = 0; k < q.longValue(); k++) {
                p = new BigInteger("2").pow(k).multiply(q).add(ONE);
                if (p.isProbablePrime(200))
                    break;
            }
        }
        BigInteger tmp = phi(p.subtract(ONE));
        BigInteger g = new BigInteger(p.bitCount(), sc).mod(p);

        while (!g.modPow(tmp, p).equals(ONE)) {
            g = new BigInteger(p.bitCount(), sc).mod(p);
        }
        return new Pair(p, g);
    }

    BigInteger phi(BigInteger n) {
        BigInteger result = n;
        for (BigInteger i = BigInteger.valueOf(2); i.multiply(i).compareTo(n) < 0; i = i.add(BigInteger.ONE))
            if (n.mod(i).equals(ZERO)) {
                while (n.mod(i).equals(ZERO))
                    n = n.divide(i);
                result = result.subtract(result.divide(i));
            }
        if (n.compareTo(ONE) > 0)
            result = result.subtract(result.divide(n));
        return result;
    }


    private void generateXForAlice() throws IOException {
        bufferedReader = new BufferedReader(new FileReader(COMMON_PARAMETERS_FILE_PATH));
        BigInteger n = new BigInteger(bufferedReader.readLine());
        BigInteger g = new BigInteger(bufferedReader.readLine());
        this.n = n;
        BigInteger x = g.multiply(new BigInteger(String.valueOf(Math.abs((long) ((Math.random() * g.longValue())))))).mod(n);
        bufferedWriter = new BufferedWriter(new FileWriter(X_FILE_PATH));
        bufferedWriter.write(String.valueOf(x));
        bufferedWriter.close();

        this.x = x;
        BigInteger X = g.modPow(x, n);
        bufferedWriter = new BufferedWriter(new FileWriter(ALICE_X));
        bufferedWriter.write(X.toString());
        bufferedWriter.close();
    }

    private void generateYForBob() throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(COMMON_PARAMETERS_FILE_PATH));
        BigInteger n = new BigInteger(bufferedReader.readLine());
        BigInteger g = new BigInteger(bufferedReader.readLine());
        this.n = n;
        BigInteger y = g.multiply(new BigInteger(String.valueOf(Math.abs((long) ((Math.random() * g.longValue())))))).mod(n);
        bufferedWriter = new BufferedWriter(new FileWriter(Y_FILE_PATH));
        bufferedWriter.write(String.valueOf(y));
        bufferedWriter.close();
        this.y = y;
        BigInteger Y = g.modPow(y, n);
        bufferedWriter = new BufferedWriter(new FileWriter(BOB_Y));
        bufferedWriter.write(Y.toString());
        bufferedWriter.close();
        bufferedReader.close();
    }


    private void generateKeyForAlice() throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(BOB_Y));
        BigInteger readed_y = new BigInteger(bufferedReader.readLine());
        this.bufferedReader = new BufferedReader(new FileReader(X_FILE_PATH));
        BigInteger x = new BigInteger(bufferedReader.readLine());

        this.bufferedReader = new BufferedReader(new FileReader(COMMON_PARAMETERS_FILE_PATH));
        BigInteger n = new BigInteger(bufferedReader.readLine());


        BigInteger K = readed_y.modPow(x, n);
        bufferedWriter = new BufferedWriter(new FileWriter(K_FILE_PATH));
        bufferedWriter.write(K.toString());
        bufferedWriter.newLine();
        bufferedWriter.close();
        bufferedReader.close();

    }

    private void generateKeyForBob() throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(ALICE_X));
        BigInteger readed_x = new BigInteger(bufferedReader.readLine());

        this.bufferedReader = new BufferedReader(new FileReader(COMMON_PARAMETERS_FILE_PATH));
        BigInteger n = new BigInteger(bufferedReader.readLine());

        this.bufferedReader = new BufferedReader(new FileReader(Y_FILE_PATH));
        BigInteger y = new BigInteger(bufferedReader.readLine());

        BigInteger K = readed_x.modPow(y, n);
        bufferedWriter = new BufferedWriter(new FileWriter(K_BOB_FILE_PATH));
        bufferedWriter.write(K.toString());
        bufferedWriter.newLine();
        bufferedWriter.close();
        bufferedReader.close();

    }
}

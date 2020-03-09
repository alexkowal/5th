package first.cryptoProtocols.task2;

import com.google.common.collect.Lists;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import static java.math.BigInteger.ONE;

public class ElGamalSign {
    private static final String TEXT_TO_SIGN = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 2/text-to-sign.txt";
    private static final String SECRET_TEXT_TO_ENCRYPT = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 2/secret-text.txt";
    private static final String SIGN_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 2/sign.txt";
    private static final String COMMON_PARAMETERS_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 2/common-parameters.txt";
    private static final String PRIVATE_KEY = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 2/private-key.txt";
    private static final String DECRYPTED_TEXT_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 2/decrypted-text.txt";
    private BufferedReader bufferedReader;
    private static Map<Character, Long> alph = new TreeMap<>();
    private static Map<Long, Character> alphRevert = new TreeMap<>();

    private String text;
    private String secretText;

    private BigInteger p;
    private BigInteger g;
    private BigInteger r;
    private BigInteger K;


    static {
        alph.put('0', 17l);
        alph.put('1', 27l);
        alph.put('2', 37l);
        alph.put('3', 47l);
        alph.put('4', 57l);
        alph.put('5', 67l);
        alph.put('6', 117l);
        alph.put('7', 127l);
        alph.put('8', 137l);
        alph.put('9', 147l);
        alph.put('а', 157l);
        alph.put('б', 167l);
        alph.put('в', 217l);
        alph.put('г', 227l);
        alph.put('д', 237l);
        alph.put('е', 247l);
        alph.put('ё', 257l);
        alph.put('ж', 267l);
        alph.put('з', 317l);
        alph.put('и', 327l);
        alph.put('й', 337l);
        alph.put('к', 347l);
        alph.put('л', 357l);
        alph.put('м', 367l);
        alph.put('н', 417l);
        alph.put('о', 427l);
        alph.put('п', 437l);
        alph.put('р', 447l);
        alph.put('с', 457l);
        alph.put('т', 467l);
        alph.put('у', 517l);
        alph.put('ф', 527l);
        alph.put('х', 537l);
        alph.put('ц', 547l);
        alph.put('ч', 557l);
        alph.put('ш', 567l);
        alph.put('щ', 617l);
        alph.put('ь', 627l);
        alph.put('ы', 637l);
        alph.put('ъ', 647l);
        alph.put('э', 657l);
        alph.put('ю', 667l);
        alph.put('я', 1117l);
        alph.put(' ', 1127l);
        alph.put(',', 1137l);
        alph.put('.', 1147l);
        alph.put('-', 1157l);
        alph.put('?', 1167l);
        alph.put('!', 1217l);
        alph.put('А', 1227l);
        alph.put('Б', 1237l);
        alph.put('В', 1247l);
        alph.put('Г', 1257l);
        alph.put('Д', 1267l);
        alph.put('Е', 1317l);
        alph.put('Ё', 1327l);
        alph.put('Ж', 1337l);
        alph.put('З', 1347l);
        alph.put('И', 1357l);
        alph.put('Й', 1367l);
        alph.put('К', 1417l);
        alph.put('Л', 1427l);
        alph.put('М', 1437l);
        alph.put('Н', 1447l);
        alph.put('О', 1457l);
        alph.put('П', 1467l);
        alph.put('Р', 1517l);
        alph.put('С', 1527l);
        alph.put('Т', 1537l);
        alph.put('У', 1547l);
        alph.put('Ф', 1557l);
        alph.put('Х', 1657l);
        alph.put('Ц', 1717l);
        alph.put('Ч', 1727l);
        alph.put('Ш', 1737l);
        alph.put('Щ', 1747l);
        alph.put('Ь', 1757l);
        alph.put('Ы', 1767l);
        alph.put('Ъ', 2017l);
        alph.put('Э', 2027l);
        alph.put('Ю', 2037l);
        alph.put('Я', 2047l);
        alph.put('a', 2057l);
        alph.put('b', 2067l);
        alph.put('c', 2117l);
        alph.put('d', 2127l);
        alph.put('e', 2137l);
        alph.put('f', 2147l);
        alph.put('g', 2157l);
        alph.put('h', 2167l);
        alph.put('i', 2217l);
        alph.put('j', 2227l);
        alph.put('k', 2237l);
        alph.put('l', 2247l);
        alph.put('m', 2257l);
        alph.put('n', 2267l);
        alph.put('o', 2317l);
        alph.put('p', 2327l);
        alph.put('q', 2337l);
        alph.put('r', 2347l);
        alph.put('s', 2357l);
        alph.put('t', 2367l);
        alph.put('u', 2417l);
        alph.put('v', 2427l);
        alph.put('w', 2437l);
        alph.put('x', 2447l);
        alph.put('y', 2457l);
        alph.put('z', 2467l);
        alph.put('A', 2517l);
        alph.put('B', 2527l);
        alph.put('C', 2537l);
        alph.put('D', 2547l);
        alph.put('E', 2557l);
        alph.put('F', 2567l);
        alph.put('G', 2617l);
        alph.put('H', 2627l);
        alph.put('I', 2637l);
        alph.put('J', 2647l);
        alph.put('K', 2657l);
        alph.put('L', 2667l);
        alph.put('M', 2717l);
        alph.put('N', 2727l);
        alph.put('O', 2737l);
        alph.put('P', 2747l);
        alph.put('Q', 2757l);
        alph.put('R', 2767l);
        alph.put('S', 2817l);
        alph.put('T', 2827l);
        alph.put('U', 2837l);
        alph.put('V', 2847l);
        alph.put('W', 2857l);
        alph.put('X', 2867l);
        alph.put('Y', 2917l);
        alph.put('Z', 2927l);
    }

    public ElGamalSign() throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(TEXT_TO_SIGN));
        generateAlphRevert();
    }

    public static void main(String[] args) throws Exception {

        ElGamalSign elGamalSign = new ElGamalSign();
        elGamalSign.readText();
        elGamalSign.readSecretText();
        while (true)
            try {
                elGamalSign.sign();
                break;
            } catch (Exception e) {
                System.out.print(".");
                Thread.sleep(200);
            }
        elGamalSign.checkSign();
        elGamalSign.decryptSecretText();
    }

    private String readText() throws IOException {
        bufferedReader = new BufferedReader(new FileReader(TEXT_TO_SIGN));

        String text = "";
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            text = text.concat(s);
        }
        this.text = text;
        return text;
    }


    private String readSecretText() throws IOException {
        bufferedReader = new BufferedReader(new FileReader(SECRET_TEXT_TO_ENCRYPT));
        String text = "";
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            text = text.concat(s);
        }
        this.secretText = text;
        while (this.text.length() > this.secretText.length())
            this.secretText = this.secretText.concat(" ");
        return text;
    }


    public void sign() throws IOException {
        readText();
        readSecretText();
        Random sc = new SecureRandom();

        p = BigInteger.probablePrime(512, sc);
        g = p.multiply(new BigInteger(String.valueOf((long) (Math.random() * p.longValue())))).mod(p.subtract(ONE));
        r = p.multiply(new BigInteger(String.valueOf((long) (Math.random() * p.longValue())))).mod(p.subtract(ONE));
        K = g.modPow(r, p);

        writeSecretKey();
        writeCommonParameters();

        List<String> textAsDigits = new ArrayList<>();
        for (int i = 0; i < this.text.length(); i++) {
            long tmp = alph.get(text.charAt(i));
            textAsDigits.add(String.valueOf(tmp));
        }

        List<String> secretTextAsDigits = new ArrayList<>();
        for (int i = 0; i < this.secretText.length(); i++) {
            long tmp = alph.get(secretText.charAt(i));
            secretTextAsDigits.add(String.valueOf(tmp));
        }

        long M;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(SIGN_FILE_PATH))) {
            int j = 0;
            for (String secretTextAsDigit : secretTextAsDigits) {
                M = Long.parseLong(secretTextAsDigit);
                long Ms = Long.parseLong(textAsDigits.get(j));

                BigInteger MModInverse = new BigInteger(String.valueOf(M)).modInverse(p.subtract(ONE));
                j++;
                BigInteger X = g.modPow(new BigInteger(String.valueOf(M)), p);
                BigInteger Y = BigInteger.valueOf(Ms)
                        .subtract(r.multiply(X))
                        .multiply(MModInverse)
                        .mod(p.subtract(ONE));

                bufferedWriter.write(X.toString());
                bufferedWriter.newLine();
                bufferedWriter.write(Y.toString());
                bufferedWriter.newLine();

                //SIGN CREATED
                //PUBLISH KEYS : K, G, P

                BigInteger YModInverse = Y.modInverse(p.subtract(ONE));
                BigInteger tmp = new BigInteger(String.valueOf(Ms)).subtract(r.multiply(X)).multiply(YModInverse).mod(p.subtract(ONE));
                // GET SECRET TEXT

            }
        }
    }

    public void checkSign() throws Exception {
        readKeys();
        readText();
        List<String> sign = readSign();
        int j = 0;
        for (int i = 0; i < sign.size(); i += 2) {
            BigInteger X = new BigInteger(sign.get(i));
            BigInteger Y = new BigInteger(sign.get(i + 1));
            long Ms = alph.get(text.charAt(j));
            j++;
            BigInteger kPowX = K.modPow(X, p);
            BigInteger xPowY = X.modPow(Y, p);
            BigInteger left = kPowX.multiply(xPowY).mod(p);
            BigInteger right = g.modPow(new BigInteger(String.valueOf(Ms)), p);
            if (!left.equals(right))
                throw new Exception("Подпись отвергнута");
        }
        System.out.println("Подпись принята");
    }

    public void decryptSecretText() throws IOException {
        String result = "";
        readKeys();
        readText();
        List<String> sign = readSign();
        int j = 0;
        for (int i = 0; i < sign.size(); i += 2) {
            BigInteger X = new BigInteger(sign.get(i));
            BigInteger Y = new BigInteger(sign.get(i + 1));
            long Ms = alph.get(text.charAt(j));
            j++;
            BigInteger YModInverse = Y.modInverse(p.subtract(ONE));
            BigInteger tmp = new BigInteger(String.valueOf(Ms)).subtract(r.multiply(X)).multiply(YModInverse).mod(p.subtract(ONE));
            result = result.concat(String.valueOf(alphRevert.get(tmp.longValue())));
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DECRYPTED_TEXT_FILE_PATH))) {
            bufferedWriter.write(result);
        }
    }

    private List<String> readSign() throws IOException {
        List<String> sign = Lists.newArrayList();
        bufferedReader = new BufferedReader(new FileReader(SIGN_FILE_PATH));
        String s = "";
        while ((s = bufferedReader.readLine()) != null) {
            sign.add(s);
        }
        return sign;
    }

    private void readKeys() throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(COMMON_PARAMETERS_FILE_PATH));
        K = new BigInteger(bufferedReader.readLine());
        g = new BigInteger(bufferedReader.readLine());
        p = new BigInteger(bufferedReader.readLine());
        this.bufferedReader = new BufferedReader(new FileReader(PRIVATE_KEY));
        r = new BigInteger(bufferedReader.readLine());
    }

    private void writeCommonParameters() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(COMMON_PARAMETERS_FILE_PATH))) {
            bufferedWriter.write(K.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(g.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(p.toString());

        }
    }

    private void writeSecretKey() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PRIVATE_KEY))) {
            bufferedWriter.write(r.toString());
        }
    }

    private void generateAlphRevert() {
        alph.forEach((key, value) -> alphRevert.put(value, key));
    }

}

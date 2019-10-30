package cryptoProtocols.task2;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class ElGamal {
    private static final String filesPath = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task 2/text.txt";
    private BufferedReader bufferedReader;
    private static Map<Character, Integer> alph = new TreeMap<>();
    private String text;

    public ElGamal() throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(filesPath));
    }

    static {
        alph.put('0', 18);
        alph.put('1', 28);
        alph.put('2', 38);
        alph.put('3', 48);
        alph.put('4', 58);
        alph.put('5', 68);
        alph.put('6', 78);
        alph.put('7', 118);
        alph.put('8', 128);
        alph.put('9', 138);
        alph.put('а', 148);
        alph.put('б', 158);
        alph.put('в', 168);
        alph.put('г', 178);
        alph.put('д', 218);
        alph.put('е', 228);
        alph.put('ё', 238);
        alph.put('ж', 248);
        alph.put('з', 258);
        alph.put('и', 268);
        alph.put('й', 278);
        alph.put('к', 318);
        alph.put('л', 328);
        alph.put('м', 338);
        alph.put('н', 348);
        alph.put('о', 358);
        alph.put('п', 368);
        alph.put('р', 378);
        alph.put('с', 418);
        alph.put('т', 428);
        alph.put('у', 438);
        alph.put('ф', 448);
        alph.put('х', 458);
        alph.put('ц', 468);
        alph.put('ч', 478);
        alph.put('ш', 518);
        alph.put('щ', 528);
        alph.put('ь', 538);
        alph.put('ы', 548);
        alph.put('ъ', 558);
        alph.put('э', 568);
        alph.put('ю', 578);
        alph.put('я', 618);
        alph.put(' ', 628);
        alph.put(',', 638);
        alph.put('.', 648);
        alph.put('-', 658);
        alph.put('?', 668);
        alph.put('!', 678);
        alph.put('А', 718);
        alph.put('Б', 728);
        alph.put('В', 738);
        alph.put('Г', 748);
        alph.put('Д', 758);
        alph.put('Е', 768);
        alph.put('Ё', 778);
        alph.put('Ж', 1118);
        alph.put('З', 1128);
        alph.put('И', 1138);
        alph.put('Й', 1148);
        alph.put('К', 1158);
        alph.put('Л', 1168);
        alph.put('М', 1178);
        alph.put('Н', 1218);
        alph.put('О', 1228);
        alph.put('П', 1238);
        alph.put('Р', 1248);
        alph.put('С', 1258);
        alph.put('Т', 1268);
        alph.put('У', 1278);
        alph.put('Ф', 1318);
        alph.put('Х', 1328);
        alph.put('Ц', 1338);
        alph.put('Ч', 1348);
        alph.put('Ш', 1358);
        alph.put('Щ', 1368);
        alph.put('Ь', 1378);
        alph.put('Ы', 1418);
        alph.put('Ъ', 1428);
        alph.put('Э', 1438);
        alph.put('Ю', 1448);
        alph.put('Я', 1458);
        alph.put('a', 1468);
        alph.put('b', 1478);
        alph.put('c', 1518);
        alph.put('d', 1528);
        alph.put('e', 1538);
        alph.put('f', 1548);
        alph.put('g', 1558);
        alph.put('h', 1568);
        alph.put('i', 1578);
        alph.put('j', 1618);
        alph.put('k', 1628);
        alph.put('l', 1638);
        alph.put('m', 1648);
        alph.put('n', 1658);
        alph.put('o', 1668);
        alph.put('p', 1678);
        alph.put('q', 1718);
        alph.put('r', 1728);
        alph.put('s', 1738);
        alph.put('t', 1748);
        alph.put('u', 1758);
        alph.put('v', 1768);
        alph.put('w', 1778);
        alph.put('x', 1918);
        alph.put('y', 1928);
        alph.put('z', 1938);
        alph.put('A', 1948);
        alph.put('B', 1958);
        alph.put('C', 1968);
        alph.put('D', 1978);
        alph.put('E', 2118);
        alph.put('F', 2128);
        alph.put('G', 2138);
        alph.put('H', 2148);
        alph.put('I', 2158);
        alph.put('J', 2168);
        alph.put('K', 2178);
        alph.put('L', 2218);
        alph.put('M', 2228);
        alph.put('N', 2238);
        alph.put('O', 2248);
        alph.put('P', 2258);
        alph.put('Q', 2268);
        alph.put('R', 2278);
        alph.put('S', 2318);
        alph.put('T', 2328);
        alph.put('U', 2338);
        alph.put('V', 2348);
        alph.put('W', 2358);
        alph.put('X', 2368);
        alph.put('Y', 2378);
        alph.put('Z', 2418);

    }


    public static void main(String[] args) throws IOException {
        ElGamal elGamal = new ElGamal();
        String text = elGamal.readText();
        System.out.println(text);
        elGamal.generateParameters();
    }


    private String readText() throws IOException {
        String text = "";
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            text = text.concat(s);
        }
        this.text = text;
        return text;
    }

    private void generateParameters() {
        BigInteger p, g, x;
        Random sc = new SecureRandom();
        p = BigInteger.probablePrime(20, sc);
        g = p.multiply(BigInteger.valueOf((long) (Math.random() * p.longValue())))
                .mod(p.subtract(BigInteger.ONE));
        x = p.multiply(BigInteger.valueOf((long) (Math.random() * p.longValue())))
                .mod(p.subtract(BigInteger.ONE));
        BigInteger y = g.modPow(x, p);
        System.out.println("y = " + y.longValue());
        System.out.println("g = " + g.longValue());
        System.out.println("p = " + p.longValue());
        encrypt(g, p, y, x);
    }

    private void encrypt(BigInteger g, BigInteger p, BigInteger y, BigInteger x) {
        long k = (long) (Math.random() * 10000);

        String textToDigits = "";
        for (int i = 0; i < this.text.length(); i++) {
            int tmp = alph.get(text.charAt(i));
            textToDigits = textToDigits.concat(String.valueOf(tmp));
        }
        System.out.println(textToDigits);
        long M = 123;
        BigInteger a = g.modPow(BigInteger.valueOf(k), p);
        BigInteger b = y.modPow(BigInteger.valueOf(k), p)
                .multiply(BigInteger.valueOf(M))
                .mod(p);
        System.out.println("a =" + a.longValue());
        System.out.println("b =" + b.longValue());
        decrypt(a, b, p, x);
    }

    private void decrypt(BigInteger a, BigInteger b, BigInteger p, BigInteger x) {
        long M = b.multiply(a.modPow(p.subtract(BigInteger.ONE).subtract(x), p)).mod(p).longValue();
        System.out.println(M);
    }
}

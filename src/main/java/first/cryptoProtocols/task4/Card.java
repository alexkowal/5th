package first.cryptoProtocols.task4;


import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
public class Card {
    private static Map<Character, Long> alph = new TreeMap<>();
    private static Map<Long, Character> alphRevert = new TreeMap<>();

    static {
        alph.put('0', 18l);
        alph.put('1', 28l);
        alph.put('2', 38l);
        alph.put('3', 48l);
        alph.put('4', 58l);
        alph.put('5', 68l);
        alph.put('6', 78l);
        alph.put('7', 118l);
        alph.put('8', 128l);
        alph.put('9', 138l);
        alph.put('а', 148l);
        alph.put('б', 158l);
        alph.put('в', 168l);
        alph.put('г', 178l);
        alph.put('д', 218l);
        alph.put('е', 228l);
        alph.put('ё', 238l);
        alph.put('ж', 248l);
        alph.put('з', 258l);
        alph.put('и', 268l);
        alph.put('й', 278l);
        alph.put('к', 318l);
        alph.put('л', 328l);
        alph.put('м', 338l);
        alph.put('н', 348l);
        alph.put('о', 358l);
        alph.put('п', 368l);
        alph.put('р', 378l);
        alph.put('с', 418l);
        alph.put('т', 428l);
        alph.put('у', 438l);
        alph.put('ф', 448l);
        alph.put('х', 458l);
        alph.put('ц', 468l);
        alph.put('ч', 478l);
        alph.put('ш', 518l);
        alph.put('щ', 528l);
        alph.put('ь', 538l);
        alph.put('ы', 548l);
        alph.put('ъ', 558l);
        alph.put('э', 568l);
        alph.put('ю', 578l);
        alph.put('я', 618l);
        alph.put(' ', 628l);
        alph.put(',', 638l);
        alph.put('.', 648l);
        alph.put('-', 658l);
        alph.put('?', 668l);
        alph.put('!', 678l);
        alph.put('А', 718l);
        alph.put('Б', 728l);
        alph.put('В', 738l);
        alph.put('Г', 748l);
        alph.put('Д', 758l);
        alph.put('Е', 768l);
        alph.put('Ё', 778l);
        alph.put('Ж', 1118l);
        alph.put('З', 1128l);
        alph.put('И', 1138l);
        alph.put('Й', 1148l);
        alph.put('К', 1158l);
        alph.put('Л', 1168l);
        alph.put('М', 1178l);
        alph.put('Н', 1218l);
        alph.put('О', 1228l);
        alph.put('П', 1238l);
        alph.put('Р', 1248l);
        alph.put('С', 1258l);
        alph.put('Т', 1268l);
        alph.put('У', 1278l);
        alph.put('Ф', 1318l);
        alph.put('Х', 1328l);
        alph.put('Ц', 1338l);
        alph.put('Ч', 1348l);
        alph.put('Ш', 1358l);
        alph.put('Щ', 1368l);
        alph.put('Ь', 1378l);
        alph.put('Ы', 1418l);
        alph.put('Ъ', 1428l);
        alph.put('Э', 1438l);
        alph.put('Ю', 1448l);
        alph.put('Я', 1458l);
        alph.put('a', 1468l);
        alph.put('b', 1478l);
        alph.put('c', 1518l);
        alph.put('d', 1528l);
        alph.put('e', 1538l);
        alph.put('f', 1548l);
        alph.put('g', 1558l);
        alph.put('h', 1568l);
        alph.put('i', 1578l);
        alph.put('j', 1618l);
        alph.put('k', 1628l);
        alph.put('l', 1638l);
        alph.put('m', 1648l);
        alph.put('n', 1658l);
        alph.put('o', 1668l);
        alph.put('p', 1678l);
        alph.put('q', 1718l);
        alph.put('r', 1728l);
        alph.put('s', 1738l);
        alph.put('t', 1748l);
        alph.put('u', 1758l);
        alph.put('v', 1768l);
        alph.put('w', 1778l);
        alph.put('x', 1918l);
        alph.put('y', 1928l);
        alph.put('z', 1938l);
        alph.put('A', 1948l);
        alph.put('B', 1958l);
        alph.put('C', 1968l);
        alph.put('D', 1978l);
        alph.put('E', 2118l);
        alph.put('F', 2128l);
        alph.put('G', 2138l);
        alph.put('H', 2148l);
        alph.put('I', 2158l);
        alph.put('J', 2168l);
        alph.put('K', 2178l);
        alph.put('L', 2218l);
        alph.put('M', 2228l);
        alph.put('N', 2238l);
        alph.put('O', 2248l);
        alph.put('P', 2258l);
        alph.put('Q', 2268l);
        alph.put('R', 2278l);
        alph.put('S', 2318l);
        alph.put('T', 2328l);
        alph.put('U', 2338l);
        alph.put('V', 2348l);
        alph.put('W', 2358l);
        alph.put('X', 2368l);
        alph.put('Y', 2378l);
        alph.put('Z', 2418l);

    }

    private String suit;
    private String rank;
    private String secret;

    public static void generateAlphRevert() {
        alph.forEach((key, value) -> alphRevert.put(value, key));
    }

    public Card(String suit, String rank, String secret) {
        this.suit = suit;
        this.rank = rank;
        this.secret = secret;

    }

    public static String encryptString(String string, BigInteger module, BigInteger key) {
        List<String> textAsDigits = new ArrayList<>();
        List<String> secret = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            long tmp = alph.get(string.charAt(i));
            textAsDigits.add(String.valueOf(tmp));
        }
        for (String textAsDigit : textAsDigits) {
            BigInteger M = new BigInteger(textAsDigit);
            M = M.modPow(key, module);
            secret.add(M.toString());
        }
        String result = "";
        for (String s : secret) {
            result = result.concat(s).concat("999");
        }
        return result.substring(0);
    }

    public static String decryptString(String string, BigInteger module, BigInteger key) {
        String result = "";
        List<String> split = Lists.newArrayList(string.split("999"));
        for (String s : split) {
            BigInteger k = new BigInteger(s).modPow(key, module);
            result = result.concat(String.valueOf(alphRevert.get(k.longValue())));
        }
        return result;
    }
}

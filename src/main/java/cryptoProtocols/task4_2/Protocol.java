package cryptoProtocols.task4_2;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.*;

public class Protocol {
    private static final String SHARED_FILE = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/Share.txt";
    private static final String ALICES_PAIR_FILE = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/Alice.txt";
    private static final String BOBS_PAIR_FILE = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/Bob.txt";
    private static final String CAROLS_PAIR_FILE = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/Carol.txt";
    public static final String BASE_PATH = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/";
    public static final String DECK_PATH = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/deck/";
    private static final String BOB_PATH = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/b/";
    private static final String ALICE_PATH = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/a/";
    private static final String CAROL_PATH = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/c/";


    public static void main(String[] args) throws IOException {

        BigInteger s = readFileAsBytes(new File("/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/b/1.txt"));
        System.out.println(s);
        writeBytesAsString(s);
        System.out.println("Выберите команду:" +
                "\n1 - Сгенерировать общее p - БПЧ" +
                "\n2 - Сгенерировать пары ключей для Алисы, Боба и Кэрол" +
                "\n3 - Зашифровать всю колоду" +
                "\n4 - Перемешать колоду" +
                "\n5 - Взять n карт из колоды" +
                "\n6 - Зашифровать n карт" +
                "\n7 - Расшифровать n карт" +
                "\n8 - Сгенерировать колоду" +
                "\n9 - Передать колоду" +
                "\n10 - Прочитать карты");

        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();

        if (a == 1) {
            generateP();
        }
        if (a == 2) {
            generateKeys();
        }
        if (a == 3) {
            encryptAllDeck();
        }
        if (a == 4) {
            shuffleCards();
        }
        if (a == 5) {
            transferNCards();
        }
        if (a == 6) {
            encryptNCards();
        }
        if (a == 7) {
            decryptNCards();
        }
        if (a == 8) {
            generateDeck();
        }
        if (a == 9) {
            transferDeck();
        }
        if (a == 10) {
            readCards();
        }


//        generateDeck();
//        shuffleCards();
//        encryptNCards();
//        transferDeck();
//        transferNCards();
//        decryptNCards();
//        encryptAllDeck();
    }

    public static void generateP() {
        System.out.println("Генерация p - БПЧ");
        BigInteger p = Help.generatePrime();
        String sharedParams = "p:" + p;
        Help.writeInFile(SHARED_FILE, Arrays.asList(sharedParams));
        System.out.println("p - сгенерированно!");
    }

    public static void generateKeys() {
        System.out.println("Генерация пар ключей для Алисы, Боба и Кэрол");

        List<String> sharedParams = Help.readFromFile(SHARED_FILE);

        BigInteger p = new BigInteger(sharedParams.get(0).split(":")[1]);

        BigInteger eA;
        do {
            eA = BigInteger.probablePrime(p.bitLength() - 1, new SecureRandom()).mod(p);
        } while (!eA.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

        BigInteger dA = eA.modInverse(p.subtract(BigInteger.ONE));

        BigInteger eB;
        do {
            eB = BigInteger.probablePrime(p.bitLength() - 1, new SecureRandom()).mod(p);
        } while (!eB.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

        BigInteger dB = eB.modInverse(p.subtract(BigInteger.ONE));

        BigInteger eC;
        do {
            eC = BigInteger.probablePrime(p.bitLength() - 1, new SecureRandom()).mod(p);
        } while (!eC.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

        BigInteger dC = eC.modInverse(p.subtract(BigInteger.ONE));

        List<String> alicesPair = new ArrayList<>();
        List<String> bobsPair = new ArrayList<>();
        List<String> carolsPair = new ArrayList<>();
        alicesPair.add("eA:" + eA);
        alicesPair.add("dA:" + dA);
        bobsPair.add("eB:" + eB);
        bobsPair.add("dB:" + dB);
        carolsPair.add("eC:" + eC);
        carolsPair.add("dC:" + dC);
        Help.writeInFile(ALICES_PAIR_FILE, alicesPair);
        Help.writeInFile(BOBS_PAIR_FILE, bobsPair);
        Help.writeInFile(CAROLS_PAIR_FILE, carolsPair);
        System.out.println("Пары сгенерированны!");
    }

    public static void generateDeck() throws IOException {
        Help.generateDeck();
    }

    public static void encryptAllDeck() throws IOException {
        List<String> sharedParams = Help.readFromFile(SHARED_FILE);
        List<String> alicesPair = Help.readFromFile(ALICES_PAIR_FILE);
        BigInteger eA = new BigInteger(alicesPair.get(0).split(":")[1]);
        BigInteger p = new BigInteger(sharedParams.get(0).split(":")[1]);
        List<List<String>> message = new ArrayList<>();

        for (int i = 1; i <= 52; i++) {
            Help.convert(p, DECK_PATH + i + ".txt");
        }


        for (int i = 1; i <= 52; i++) {
            message.add(Help.readCard(DECK_PATH + i + ".txt"));
        }
        for (int i = 0; i < message.size(); i++) {
            for (int j = 0; j < message.get(i).size(); j++) {
                message.get(i).set(j, new BigInteger(message.get(i).get(j)).modPow(eA, p).toString());
            }
        }

        for (int i = 1; i <= 52; i++) {
            Help.writeCard(message.get(i - 1), DECK_PATH + i + ".txt");
        }

    }

    public static void encryptNCards() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        System.out.println("Введите имя игрока");
        name = br.readLine();
        Integer count = 0;
        System.out.println("Введите количество карт");
        count = Integer.valueOf(br.readLine());
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            System.out.println("Введите номер карты " + (i + 1));
            numbers.add(Integer.valueOf(br.readLine()));
        }
        if (name.equals("a"))
            name = "Alice";
        if (name.equals("b"))
            name = "Bob";
        if (name.equals("c"))
            name = "Carol";
        List<String> sharedParams = Help.readFromFile(SHARED_FILE);
        List<String> key = Help.readFromFile(BASE_PATH + name + ".txt");
        BigInteger eA = new BigInteger(key.get(0).split(":")[1]);
        BigInteger p = new BigInteger(sharedParams.get(0).split(":")[1]);

        System.out.println("Введите путь к колоде");
        String path = br.readLine().trim();

        for (int i = 0; i < numbers.size(); i++) {
            List<List<String>> message = new ArrayList<>();
            message.add(Help.readCard(BASE_PATH + path + "/" + numbers.get(i) + ".txt"));

            for (int l = 0; l < message.size(); l++) {
                for (int j = 0; j < message.get(l).size(); j++) {
                    message.get(l).set(j, new BigInteger(message.get(l).get(j)).modPow(eA, p).toString());
                }
            }
            for (int l = 0; l < message.size(); l++) {
                Help.writeCard(message.get(l), BASE_PATH + path + "/" + (numbers.get(i)) + ".txt");
            }
        }
    }


    public static void decryptNCards() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        System.out.println("Введите имя игрока");
        name = br.readLine();
        Integer count = 0;
        System.out.println("Введите количество карт");
        count = Integer.valueOf(br.readLine());
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            System.out.println("Введите номер карты " + (i + 1));
            numbers.add(Integer.valueOf(br.readLine()));
        }
        if (name.equals("a"))
            name = "Alice";
        if (name.equals("b"))
            name = "Bob";
        if (name.equals("c"))
            name = "Carol";
        List<String> sharedParams = Help.readFromFile(SHARED_FILE);
        List<String> key = Help.readFromFile(BASE_PATH + name + ".txt");
        BigInteger dA = new BigInteger(key.get(1).split(":")[1]);
        BigInteger p = new BigInteger(sharedParams.get(0).split(":")[1]);

        System.out.println("Введите путь к колоде");
        String path = br.readLine().trim();

        for (int i = 0; i < numbers.size(); i++) {
//            List<String> crd = Help.readCard(BASE_PATH + path + "/" + numbers.get(i) + ".txt");
            BigInteger s = readFileAsBytes(new File(BASE_PATH + path + "/" + numbers.get(i) + ".txt"));
            s = s.modPow(dA, p);
            Files.delete(new File(BASE_PATH + path + "/" + numbers.get(i) + ".txt").toPath());
            writeBytesAsString(s);
//            Help.writeCard(crd, BASE_PATH + path + "/" + (numbers.get(i)) + ".txt");
//            Help.dec(crd, BASE_PATH + path + "/" + (numbers.get(i)) + "_decrypted.txt");
//            Files.delete(new File(BASE_PATH + path + "/" + (numbers.get(i)) + ".txt").toPath());
        }
    }

    private static void shuffleCards() throws IOException {
        System.out.println("Введите расположение колоды:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String path = br.readLine().trim();

        List<List<String>> cards = new ArrayList<>();

        for (int i = 1; i <= 52; i++) {
            cards.add(Help.readCard(BASE_PATH + path + "/" + i + ".txt"));
        }
        Collections.shuffle(cards);

        for (int i = 1; i <= 52; i++) {
            Help.writeCard(cards.get(i - 1), BASE_PATH + path + "/" + i + ".txt");
        }
    }

    private static void transferDeck() throws IOException {
        System.out.println("Введите от кого и кому:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String from = br.readLine().trim();
        String to = br.readLine().trim();
        for (int i = 1; i <= 52; i++) {
            File file = new File(BASE_PATH + from + "/" + i + ".txt");
            if (file.exists()) {
                List<String> crd = Help.readCard(BASE_PATH + from + "/" + i + ".txt");
                Help.writeCard(crd, BASE_PATH + to + "/" + i + ".txt");
                file.delete();
            }
        }
    }

    private static void transferNCards() throws IOException {
        System.out.println("Введите от кого и кому:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String from = br.readLine().trim();
        String to = br.readLine().trim();

        System.out.println("Введите количество карт");
        Integer count = Integer.valueOf(br.readLine());
        List<Integer> numbers = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            System.out.println("Введите номер карты " + (i + 1));
            numbers.add(Integer.valueOf(br.readLine()));
        }

        for (int i = 0; i < numbers.size(); i++) {
            File file = new File(BASE_PATH + from + "/" + numbers.get(i) + ".txt");
            if (file.exists()) {
                List<String> crd = Help.readCard(BASE_PATH + from + "/" + numbers.get(i) + ".txt");
                Help.writeCard(crd, BASE_PATH + to + "/" + numbers.get(i) + ".txt");
                file.delete();
            }
        }
    }

    private static void readCards() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer count;
        System.out.println("Введите количество карт");
        count = Integer.valueOf(br.readLine());
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            System.out.println("Введите номер карты " + (i + 1));
            numbers.add(Integer.valueOf(br.readLine()));
        }
        System.out.println("Введите путь к колоде");
        String path = br.readLine().trim();

        for (int i = 0; i < numbers.size(); i++) {
            File file = new File(BASE_PATH + path + "/" + numbers.get(i) + ".txt");
            if (file.exists()) {
                List<String> crd = Help.readCard(BASE_PATH + path + "/" + numbers.get(i) + ".txt");
                Help.dec(crd, BASE_PATH + path + "/" + numbers.get(i) + ".txt");
            }
        }
    }


    private static BigInteger readFileAsBytes(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        String result = "";

        return new BigInteger(bytes);
    }

    private static void writeBytesAsString(BigInteger s) throws IOException {
        byte[] a = s.toByteArray();
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/cryptoProtocols/task4_2/files/b/1.txt");
        fileOutputStream.write(a);
        fileOutputStream.close();
    }
}

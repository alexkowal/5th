package first.cryptoProtocols.task4;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

public class Application {
    private static final String INITIAL_DECK_FILE_PATH = "/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/first.cryptoProtocols/task4/deck.json";
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Application(BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(INITIAL_DECK_FILE_PATH));
        this.bufferedWriter = new BufferedWriter(new FileWriter(INITIAL_DECK_FILE_PATH));
    }

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        ObjectMapper mapper = new ObjectMapper();
        Card.generateAlphRevert();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INITIAL_DECK_FILE_PATH));
        Deck deck = new Deck();
        deck = deck.initDeck();
        deck = deck.shuffleDeck(deck);

        Cipher cipher = Cipher.getInstance("RSA");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair kp = keyGen.genKeyPair();


        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        System.out.println(deck.deck.get(0).getSuit());
        deck.deck.get(0).setSuit(new String(cipher.doFinal(deck.deck.get(0).getSuit().getBytes())));
        System.out.println(deck.deck.get(0).getSuit());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        deck.deck.get(0).setSuit(new String(cipher.doFinal(deck.deck.get(0).getSuit().getBytes())));
        System.out.println(deck.deck.get(0).getSuit());
        mapper.writeValue(new File(INITIAL_DECK_FILE_PATH), deck.deck.entrySet());


    }


}

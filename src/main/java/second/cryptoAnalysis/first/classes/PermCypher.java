package second.cryptoAnalysis.first.classes;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static second.cryptoAnalysis.first.constants.KasiskiConstants.*;


@Data
@Slf4j
class PermCypher {

    public void generateKey() throws IOException {
        Long keyLength = readKeyLength();
        log.info("Generate key");
        StringBuilder initialKey = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            initialKey = initialKey.append(i).append(" ");
        }
        List<String> perm = Lists.newArrayList(initialKey.toString().split(" "));
        Collections.shuffle(perm);
        writeKey(perm);
    }

    public void permText() throws IOException {
        String textToEncrypt = readText();
        List<String> perm = readKeyFromFile();
        compareKeyAndText(textToEncrypt, perm);
        List<StringBuilder> splittedText = splitTextByKeySize(textToEncrypt, perm);
        log.info("Start text permutation");
        List<String> result = Lists.newArrayList();
        for (StringBuilder s : splittedText) {
            result.add(permString(s, perm));
        }
        log.info("Permutation finished");
        writeEncryptedText(result);
        log.info("DONE!");
    }

    public void decrypt() throws IOException {
        List<String> perm = readKeyFromFile();
        String textToDecrypt = readEncryptedText();
        List<StringBuilder> splittedText = splitTextByKeySize(textToDecrypt,perm);
        List<String> result = Lists.newArrayList();
        for (StringBuilder s : splittedText) {
            result.add(permStringToDecrypt(s, perm));
        }

        log.info("Permutation finished");
        writeDecryptedText(result);
        log.info("DONE!");
    }


    private List<String> readKeyFromFile() throws IOException {
        String key;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + KEY_FILE_NAME))) {
            key = br.readLine();
        }
        return Lists.newArrayList(key.split(" "));
    }

    private Long readKeyLength() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + TEXT_FILE_NAME))) {
            return Long.parseLong(br.readLine());
        }
    }

    private void writeKey(List<String> perm) throws IOException {
        log.info("Write key to file '{}'", KEY_FILE_NAME);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + KEY_FILE_NAME))) {
            for (String s : perm) {
                bw.write(s + " ");
            }
        }
    }

    private String readText() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + TEXT_FILE_NAME))) {
            br.readLine();
            return br.readLine();
        }
    }

    private String readEncryptedText() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + ENCRYPTED_FILE_NAME))) {
            return br.readLine();
        }
    }

    private void writeEncryptedText(List<String> text) throws IOException {
        log.info("Write encrypted text to file");
        String result = "";
        for (String s : text) {
            result = result.concat(s);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + ENCRYPTED_FILE_NAME))) {
                bw.write(result);
        }
    }

    private void writeDecryptedText(List<String> text) throws IOException {
        log.info("Write encrypted text to file");
        String result = "";
        for (String s : text) {
            result = result.concat(s);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + DECRYPTED_FILE_NAME))) {
            bw.write(normalizeString(result));
        }
    }

    private void compareKeyAndText(String text, List<String> key) {
        if (text.length() < key.size()) {
            while (text.length() < key.size()) {
                text = text.concat("_");
            }
        }
    }

    private List<StringBuilder> splitTextByKeySize(String text, List<String> key) {
        List<StringBuilder> slittedText = Lists.newArrayList();
        int count = text.length() / key.size();
        for (int i = 0; i < count; i++) {
            slittedText.add(new StringBuilder(text.substring(0, key.size())));
            text = text.substring(key.size());
        }
        if (text.length() > 0) {
            if (text.length() < key.size()) {
                while (text.length() < key.size()) {
                    text = text.concat("_");
                }
            }
            slittedText.add(new StringBuilder(text));
        }
        return slittedText;
    }

    private String permString(StringBuilder string, List<String> perm) {
        int pos = 0;
        List<Character> list = Lists.newArrayList();
        for (int i = 0; i < string.length(); i++)
            list.add(' ');
        for (String s : perm) {
            list.set(Integer.parseInt(s), string.charAt(pos));
            pos++;
        }
        String finalString = "";
        for (Character character : list) {
            finalString = finalString.concat(String.valueOf(character));
        }
        return finalString;
    }

    private String permStringToDecrypt(StringBuilder string, List<String> perm) {
        int pos = 0;
        List<Character> list = Lists.newArrayList();
        for (int i = 0; i < string.length(); i++)
            list.add(' ');
        for (String s : perm) {
            list.set(pos, string.charAt(Integer.parseInt(s)));
            pos++;
        }
        String finalString = "";
        for (Character character : list) {
            finalString = finalString.concat(String.valueOf(character));
        }
        return finalString;
    }

    private String normalizeString(String s){
        return s.replaceAll("_","");
    }


}

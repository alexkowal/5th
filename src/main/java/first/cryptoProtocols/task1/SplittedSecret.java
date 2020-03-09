package first.cryptoProtocols.task1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SplittedSecret {
    public static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    public static final String NUMBER = "0123456789";

    public static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;

    private Integer users;
    private byte[] message;
    SecureRandom random = new SecureRandom();
    private static final String filesPath = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task1/";
    private static final String messagePath = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task1/message.txt";
    public static final String SPECIAL = "special";
    public static final String RESULT = "result";
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private ArrayList<String> files;

    public static final String dotTXT = ".txt";

    public void sendMessage() throws IOException {
        initToSend();
        ArrayList<ArrayList<Byte>> bytes = generateBytes();
        processMessageForSpecialUser(bytes);
        processMessageForOtherUsers(bytes);
    }

    public void consumeMessage() throws IOException {
        ArrayList<ArrayList<Byte>> bytes = new ArrayList<>();
        byte[] randomByteArray = new byte[message.length];
        for (String file : files) {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(randomByteArray);
            bytes.add((ArrayList<Byte>) fromByteArray(randomByteArray));
        }
        fileOutputStream = new FileOutputStream(filesPath + RESULT + dotTXT);
        fileOutputStream.write(XOR(bytes));

    }


    private void initToSend() {
        files = new ArrayList<>();
        try {
            for (int i = 0; i < users - 1; i++) {
                File file = new File(filesPath + i + dotTXT);
                file.createNewFile();
                files.add(filesPath + i + dotTXT);
            }
            File file = new File(filesPath + SPECIAL + dotTXT);
            file.createNewFile();
            files.add(filesPath + SPECIAL + dotTXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMessageForOtherUsers(ArrayList<ArrayList<Byte>> bytes) throws IOException {
        int i = 0;
        for (ArrayList<Byte> aByte : bytes) {
            fileOutputStream = new FileOutputStream(files.get(i));
            fileOutputStream.write(toByteArray(aByte));
            i++;
        }

    }

    private void processMessageForSpecialUser(ArrayList<ArrayList<Byte>> bytes) throws IOException {
        fileOutputStream = new FileOutputStream(filesPath + SPECIAL + dotTXT);
        byte[] xorMessage = XORWithMessage(bytes, message);
        fileOutputStream.write(xorMessage);
    }

    private byte[] XORWithMessage(ArrayList<ArrayList<Byte>> bytes, byte[] message) {
        byte[] result = message;
        for (int i = 0; i < bytes.size(); i++) {
            byte[] temp = toByteArray(bytes.get(i));
            for (int c = 0; c < result.length; c++) {
                result[c] = (byte) (result[c] ^ temp[c]);
            }
        }
        return result;
    }

    private byte[] XOR(ArrayList<ArrayList<Byte>> bytes) {
        byte[] result = toByteArray(bytes.get(0));
        for (int i = 1; i < bytes.size(); i++) {
            byte[] temp = toByteArray(bytes.get(i));
            for (int c = 0; c < result.length; c++) {
                result[c] = (byte) (result[c] ^ temp[c]);
            }
        }
        return result;
    }


    private static byte[] toByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }

    private static List<Byte> fromByteArray(byte[] in) {
        final int n = in.length;
        List<Byte> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ret.add(in[i]);
        }
        return ret;
    }

    private ArrayList<ArrayList<Byte>> generateBytes() {
        ArrayList<ArrayList<Byte>> result = new ArrayList<>();
        byte[] randomByteArray = new byte[message.length];
        for (int i = 0; i < users - 1; i++) {
            ArrayList<Byte> singleArray = new ArrayList<>();
            random.nextBytes(randomByteArray);
            for (byte b : randomByteArray) {
                singleArray.add(b);
            }
            result.add(singleArray);
        }
        return result;

    }


}

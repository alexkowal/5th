package cryptoProtocols.task1;

import java.io.*;
import java.nio.file.Files;
import java.security.SecureRandom;

import static cryptoProtocols.task1.SplittedSecret.DATA_FOR_RANDOM_STRING;


public class StarterImpl implements Starter {
    private SplittedSecret splittedSecret;
    private String fileName = "/Users/aleksandr/5 курс 1 семестр/Криптографические протоколы/task1/message.txt";

    StarterImpl() throws FileNotFoundException {
        splittedSecret = new SplittedSecret();
    }

    @Override
    public void start() throws IOException {
        System.out.println("Выберите режим: (1) - зашифровать, (2) - расшифровать, (3) - записать сообщение в файл, (4) - выход из программы");
        Integer mode = Integer.valueOf(consoleReader.readLine());
        switch (mode) {
            case 1:
                encode();
                return;
            case 2:
                decode();
                return;
            case 3:
                writeMessage();
                return;
            case 4:
                System.exit(0);
        }
    }

    private void writeMessage() throws IOException {
        System.out.println("Введите сообщение: ");
        String message = consoleReader.readLine();
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(message.getBytes());
    }

    private void decode() throws IOException {
        splittedSecret.consumeMessage();
    }

    private void encode() throws IOException {
        System.out.println("Введите количество пользователей");
        Integer users = Integer.valueOf(consoleReader.readLine());

        splittedSecret.setUsers(users);
        System.out.println("Считывается сообщение");
        String message = readMessage();
        byte[] msg = readMessageToByte();

        splittedSecret.setMessage(msg);
        splittedSecret.sendMessage();
    }

    private byte[] readMessageToByte() throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private String readMessage() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String message = "";
            String tmp;
            while ((tmp = br.readLine()) != null)
                message += tmp;
            return message;
        } finally {
            br.close();
        }
    }

}

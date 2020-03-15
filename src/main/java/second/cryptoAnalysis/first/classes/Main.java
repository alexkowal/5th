package second.cryptoAnalysis.first.classes;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("1 - сгенерировать ключ \n" +
                    "2 - перестановка текста \n" +
                    "3 - расшифровать перестановку");
            Integer mode = Integer.valueOf(br.readLine());
            PermCypher permCypher = new PermCypher();

            switch (mode) {
                case 1:
                    permCypher.generateKey();
                    break;
                case 2:
                    permCypher.permText();
                    break;
                case 3:
                    permCypher.decrypt();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Выберите режим 1 - 3!");
            }
        }
    }
}

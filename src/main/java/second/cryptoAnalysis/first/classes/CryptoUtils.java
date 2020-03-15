package second.cryptoAnalysis.first.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static second.cryptoAnalysis.first.constants.KasiskiConstants.FILE_PATH;
import static second.cryptoAnalysis.first.constants.KasiskiConstants.TEXT_FILE_NAME;

public class CryptoUtils {

    public static String readText() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + TEXT_FILE_NAME))) {
            br.readLine();
            return br.readLine();
        }
    }
}

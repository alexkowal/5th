package second.cryptoAnalysis.first.classes;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PermCypher permCypher = new PermCypher();
        permCypher.generateKey();
        permCypher.permText();
        permCypher.decrypt();
    }
}

package first.cryptoProtocols.task1;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        StarterImpl starterImpl = new StarterImpl();
        while (true) {
            starterImpl.start();
        }
    }
}

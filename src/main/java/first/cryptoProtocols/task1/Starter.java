package first.cryptoProtocols.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface Starter {
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    public void start() throws IOException;

}

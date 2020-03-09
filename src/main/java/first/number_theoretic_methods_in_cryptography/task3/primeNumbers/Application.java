package first.number_theoretic_methods_in_cryptography.task3.primeNumbers;

import first.number_theoretic_methods_in_cryptography.task3.FermaTest;
import first.number_theoretic_methods_in_cryptography.task3.MillerRabinTest;
import first.number_theoretic_methods_in_cryptography.task3.SoloveyStrassenTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

    public static void main(String[] args) throws IOException {
        System.out.println("Enter digit to test: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long c = Long.parseLong(br.readLine());
        SoloveyStrassenTest soloveyStrassenTest = new SoloveyStrassenTest(c, 100);
        FermaTest fermaTest = new FermaTest(c);
        MillerRabinTest millerRabinTest = new MillerRabinTest(c,100);
        System.out.print("Solovey-Strassen Test: ");
        long startTime = System.currentTimeMillis();
        if (soloveyStrassenTest.test())
            System.out.println("Prime");
        else
            System.out.println("Not Prime");
        System.out.println("Solovey-Strassen Test Time spent:" + (System.currentTimeMillis() - startTime));


        System.out.print("Raben-Miller Test: ");
        startTime = System.currentTimeMillis();
        if (millerRabinTest.test())
            System.out.println("Prime");
        else
            System.out.println("Not Prime");
        System.out.println("Raben-Miller Test Time spent:" + (System.currentTimeMillis() - startTime));

        System.out.print("Ferma Test: ");
        startTime = System.currentTimeMillis();
        if (fermaTest.test())
            System.out.println("Prime");
        else
            System.out.println("Not Prime");
        System.out.println("Ferma Test Time spent:" + (System.currentTimeMillis() - startTime));
    }

}

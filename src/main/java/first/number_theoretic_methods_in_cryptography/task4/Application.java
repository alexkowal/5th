package first.number_theoretic_methods_in_cryptography.task4;

import first.number_theoretic_methods_in_cryptography.task1.WrongInputException;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws WrongInputException, IOException {
        long start;
        long time;

        long n = 1233425l;
        long res;
        PollardP p = new PollardP(n, 123);
        PollardP_1 p_1 = new PollardP_1(n);
        InfiniteFreactionMethod ifm = new InfiniteFreactionMethod();
//        p_1.generatePrimes();

        System.out.println();
        start = System.currentTimeMillis();
        res = p.methodP_Probaple(n, 0.05);
        time = System.currentTimeMillis() - start;
        System.out.println("P метод Полларда: " + res + " * " + (n / res) + " выполнился за " + time + " мс");
        start = System.currentTimeMillis();
        res = p_1.method(n, 10);
        time = System.currentTimeMillis() - start;
        if (res == 0)
            res = 1;
        System.out.println("P-1 метод Полларда: " + res + " * " + (n / res) + " выполнился за " + time + " мс");
        start = System.currentTimeMillis();
        ifm.expand(n, 3);
        time = System.currentTimeMillis() - start;
        System.out.println(" выполнился за " + time + " мс");
    }
}

package first.number_theoretic_methods_in_cryptography.task1;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws WrongInputException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Алгоритм Евклида нахождения НОД чисел");
        System.out.println("2 - Китайская теорема об остатках и алгоритма Гарнера");
        System.out.println("0 - Выход");
        while (true) {
            int init = scanner.nextInt();
            if (init == 1) {
                System.out.print("Введите 2 числа:");
                long a = scanner.nextInt();
                long b = scanner.nextInt();
                Euclid euclid = new Euclid();
                BinaryEuclid binaryEuclid = new BinaryEuclid(a, b);
                ExtEuclid extEuclid = new ExtEuclid(a, b);
                long startTime = System.nanoTime();
                System.out.println("Euclid = " + Euclid.GCD(a, b));
                System.out.println("Euclid time spent:" + (System.nanoTime() - startTime));

                startTime = System.nanoTime();
                System.out.println("Binary Euclid = " + binaryEuclid.GCD(binaryEuclid.a, binaryEuclid.b));
                System.out.println("Binary Euclid time spent:" + (System.nanoTime() - startTime));
                startTime = System.nanoTime();
                extEuclid.GCD(extEuclid.a, extEuclid.b);
                System.out.println("EXT Euclid time spent:" + (System.nanoTime() - startTime));
            } else if (init == 2) {
                System.out.println("Введите k > 0 - количество модулей , затем вычеты a1 ... ak и модули m1 ... mk: ");
                System.out.print("k= ");
                int k = scanner.nextInt();
                int[] a = new int[k];
                int[] m = new int[k];
                System.out.print("a1..ak: ");
                for (int i = 0; i < k; i++) {
                    a[i] = scanner.nextInt();
                }
                System.out.println("m1...mk: ");
                for (int i = 0; i < k; i++) {
                    m[i] = scanner.nextInt();
                }
                System.out.println("Первый результат - это китайская теорема об остатках, второй - алгоритм Гарнера:");
                long startTime = System.nanoTime();

                System.out.println(Chinese.chineseTheorem(k, a, m));
                System.out.println("Chinese theorem time spent:" + (System.nanoTime() - startTime));

                startTime = System.nanoTime();
                System.out.println(Garner.garnerMethod(k, a, m));
                System.out.println("Garner methodP time spent:" + (System.nanoTime() - startTime));

            } else if (init == 0)
                return;
        }
    }

}

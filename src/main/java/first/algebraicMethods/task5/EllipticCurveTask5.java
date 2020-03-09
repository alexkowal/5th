package first.algebraicMethods.task5;

import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.util.List;

import static first.algebraicMethods.task1.EllipticCurve.summ;
import static first.algebraicMethods.task1.Help.generateEllipticCurve;

public class EllipticCurveTask5 {

    public static void main(String[] args) throws IOException {
        // Страница 324

        while (true) {
            System.out.println("Выберите шаг: 1 - генерация кривой, 2 - Вычисление значения функции h(k)");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int step = Integer.parseInt(br.readLine());
            if (step == 1) {
                System.out.println("Введите l для генерации кривой:");
                int l = Integer.parseInt(br.readLine());
                generateCommonParameters(l);
                System.out.println("Далее (2 * n + 2) должно быть меньше или равно " + defineCriteria() + "" +
                        ",где n - битность числа k");
            }
            if (step == 2) {
                int max = defineCriteria();
                System.out.println("(2 * n + 2) должно быть меньше или равно " + max + "" +
                        ", где n -  битность числа k ");


                System.out.println("Введите k:");
                int k = Integer.parseInt(br.readLine());
                calculateFunctionValue(k);
            }

        }

    }

    private static long getModule(int n) {
        long k = 2;
        for (int i = 0; i < n - 1; i++)
            k *= 2;
        return k;
    }

    private static void generateCommonParameters(int l) throws IOException {
        System.out.println("Генерация эллиптической кривой");
        List<Object> parameters = generateEllipticCurve(l);
        System.out.println("Генерация окончена");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ellipticCurveParameters.txt"));
        try {
            bufferedWriter.write(String.valueOf(parameters.get(0)));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(parameters.get(1)));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(parameters.get(2)));
            bufferedWriter.close();
        } finally {
            bufferedWriter.close();
        }
    }

    private static void calculateFunctionValue(long k) throws IOException {
        int max = defineCriteria();
        int n = Long.toBinaryString(k).length();
        if (2 * n + 2 > max) {
            System.out.println("(2*n+2) должно быть меньше или равно " + max + "" +
                    ",где n -  битность числа k ");
            return;
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader("ellipticCurveParameters.txt"));
        List<String> q = Lists.newArrayList(bufferedReader.readLine().split("="));
        Pair<BigInteger, BigInteger> qPoint =
                new Pair<>(new BigInteger(q.get(0)), new BigInteger(q.get(1)));
        BigInteger p = new BigInteger(bufferedReader.readLine());
        //kQ
//        System.out.println("Вычисляется kQ");
        for (int i = 0; i < k; i++) {
            qPoint = summ(qPoint, qPoint, p);
        }

        long module = getModule(n);
        //f(kQ(mod 2^n)
//        System.out.println("Вычисляется f(kQ(mod 2^n)");
        long e = qPoint.getKey().mod(BigInteger.valueOf(module)).longValue();
        long factor = module * k + e;

        //R = ((2^n*k + e)*Q)
//        System.out.println("Вычисляется R = ((2^n*k + e)*Q)");

        Pair<BigInteger, BigInteger> r = qPoint;
        for (int i = 0; i < factor; i++)
            r = summ(r, r, p);
        System.out.println("h(k) = " + r.getKey());
    }

    private static int defineCriteria() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("ellipticCurveParameters.txt"));
        List<String> q = Lists.newArrayList(bufferedReader.readLine().split("="));
        Pair<BigInteger, BigInteger> qPoint =
                new Pair<>(new BigInteger(q.get(0)), new BigInteger(q.get(1)));
        BigInteger p = new BigInteger(bufferedReader.readLine());
        BigInteger r = new BigInteger(bufferedReader.readLine());
        return (int) Math.floor(Math.log(r.longValue()) / Math.log(2));
    }
}

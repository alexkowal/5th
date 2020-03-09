package first.algebraicMethods.task2;

import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import static first.algebraicMethods.task1.EllipticCurve.summ;
import static first.algebraicMethods.task1.Help.generateEllipticCurve;

public class EllipticCurveTask2 {

    public static void main(String[] args) throws IOException {
        EllipticCurveTask2 ellipticCurveTask2 = new EllipticCurveTask2();
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Выберите шаг алгоритма: " +
                    "\n1 - Сгенерировать общие параметры" +
                    "\n2 - Алиса: Сгенерировать случайное число a и отправить aQ " +
                    "\n3 - Боб: Сгенерировать случайное число b и отправить bQ " +
                    "\n4 - Алиса: Умножить точку bQ на a" +
                    "\n5 - Боб: Умножить точку aQ на b");

            Integer step = Integer.valueOf(br.readLine());
            if (step == 1) {
                System.out.println("Введите l");
                int l = Integer.parseInt(br.readLine());
                ellipticCurveTask2.generateCommonParameters(l);
                System.out.println("Параметры сгенерированы");
            }
            if (step == 2) {
                ellipticCurveTask2.generateRandomDigitAndSend("Alice");
            }
            if (step == 3) {
                ellipticCurveTask2.generateRandomDigitAndSend("Bob");
            }
            if (step == 4) {
                ellipticCurveTask2.readPointAndMultiplyWithMyRandomNumber("Bob", "Alice");
            }
            if (step == 5) {
                ellipticCurveTask2.readPointAndMultiplyWithMyRandomNumber("Alice", "Bob");
            }
        }
    }


    private void generateCommonParameters(int l) throws IOException {
        List<Object> parameters = generateEllipticCurve(l);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("commonParameters.txt"));
        try {
            bufferedWriter.write(String.valueOf(parameters.get(0)));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(parameters.get(1)));
            bufferedWriter.close();
        } finally {
            bufferedWriter.close();
        }

    }

    private void generateRandomDigitAndSend(String abonentName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("commonParameters.txt"));
        List<String> q = Lists.newArrayList(bufferedReader.readLine().split("="));
        Pair<BigInteger, BigInteger> qPoint =
                new Pair<>(new BigInteger(q.get(0)), new BigInteger(q.get(1)));
        BigInteger p = new BigInteger(bufferedReader.readLine());

        Random r = new SecureRandom();
        Long random = Long.valueOf(Math.abs(r.nextInt((int) (p.longValue()))));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(abonentName + "_randomNumber.txt"));
        try {
            bufferedWriter.write(String.valueOf(random));
        } finally {
            bufferedWriter.close();
        }
        System.out.println("Случайно сгенерированное число пользователем " + abonentName + " : " + random);
        for (int i = 0; i < random; i++)
            qPoint = summ(qPoint, qPoint, p);
        bufferedWriter = new BufferedWriter(new FileWriter(abonentName + ".txt"));
        try {
            bufferedWriter.write(String.valueOf(qPoint));
        } finally {
            bufferedWriter.close();
        }

    }

    private void readPointAndMultiplyWithMyRandomNumber(String oppenentName, String myName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(myName + "_randomNumber.txt"));
        Integer myNumber = Integer.valueOf(bufferedReader.readLine());
        bufferedReader = new BufferedReader(new FileReader(oppenentName + ".txt"));
        List<String> pointAsString = Lists.newArrayList(bufferedReader.readLine().split("="));
        Pair<BigInteger, BigInteger> point =
                new Pair<>(new BigInteger(pointAsString.get(0)), new BigInteger(pointAsString.get(1)));
        bufferedReader = new BufferedReader(new FileReader("commonParameters.txt"));
        bufferedReader.readLine();
        BigInteger p = new BigInteger(bufferedReader.readLine());
        for (int i = 0; i < myNumber; i++) {
            point = summ(point, point, p);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(myName + "_key.txt"));
        try {
            bufferedWriter.write(String.valueOf(point));
        } finally {
            bufferedWriter.close();
        }
    }
}

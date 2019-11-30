package algebraicMethods.task2;

import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import static algebraicMethods.task1.EllipticCurve.summ;
import static algebraicMethods.task1.Help.generateEllipticCurve;

public class EllipticCurveTask2 {

    public static void main(String[] args) throws IOException {
        EllipticCurveTask2 ellipticCurveTask2 = new EllipticCurveTask2();
        ellipticCurveTask2.generateCommonParameters(6);

        ellipticCurveTask2.generateRandomDigitAndSend("Alice");
        ellipticCurveTask2.generateRandomDigitAndSend("Bob");
        ellipticCurveTask2.readPointAndMultiplyWithMyRandomNumber("Bob", "Alice");
        ellipticCurveTask2.readPointAndMultiplyWithMyRandomNumber("Alice", "Bob");

    }


    private void generateCommonParameters(int l) throws IOException {
        List<Object> parameters = generateEllipticCurve(l);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("commonParameters.txt"));
        bufferedWriter.write(String.valueOf(parameters.get(0)));
        bufferedWriter.newLine();
        bufferedWriter.write(String.valueOf(parameters.get(1)));
        bufferedWriter.close();
    }

    private void generateRandomDigitAndSend(String abonentName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("commonParameters.txt"));
        List<String> q = Lists.newArrayList(bufferedReader.readLine().split("="));
        Pair<BigInteger, BigInteger> qPoint =
                new Pair<>(new BigInteger(q.get(0)), new BigInteger(q.get(1)));
        BigInteger p = new BigInteger(bufferedReader.readLine());

        Random r = new SecureRandom();
        Integer random = Math.abs(r.nextInt(100));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(abonentName + "_randomNumber.txt"));
        bufferedWriter.write(String.valueOf(random));
        bufferedWriter.close();
        System.out.println(random);
        for (int i = 0; i < random; i++)
            qPoint = summ(qPoint, qPoint, p);
        bufferedWriter = new BufferedWriter(new FileWriter(abonentName + ".txt"));
        bufferedWriter.write(String.valueOf(qPoint));
        bufferedWriter.close();

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
        bufferedWriter.write(String.valueOf(point));
        bufferedWriter.close();

    }
}

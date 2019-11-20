package algebraicMethods.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import static java.math.BigInteger.ONE;

public class Starter {
    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите L - количество бит");
        String s1 = reader.readLine();
        if (s1.equals("") || !isNumber(s1)) {
            System.out.println("Введите число!!");
            return;
        }
        int L = Integer.parseInt(s1);
        if (L <= 4) {
            System.out.println("Ошибка! Число бит должно быть > 4 и != 5");
            return;
        }
        List<Task> t = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            t.add(new Task(L));

        }
        ThreadPoolExecutor tt = new ScheduledThreadPoolExecutor(100);
        for (Task task : t) {
            tt.submit(task);
        }
/*


        EllipticCurve ellipticCurve = new EllipticCurve();
        while (true) {
            ellipticCurve.findP(L);
            System.out.println("P = " + ellipticCurve.getP());
            if (!ellipticCurve.faction(ONE)) continue; // проблема тут?
            if (ellipticCurve.verify() && ellipticCurve.check(5)) break;
        }
        int k = 0;

        List<Task> t = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            t.add(new Task(L));

        }
        ThreadPoolExecutor tt = new ScheduledThreadPoolExecutor(100);
        for (Task task : t) {
            tt.submit(task);
        }

*/
    }

    static boolean isNumber(String s) {
        char[] a = s.toCharArray();

        if (a[0] == '0' && a.length > 1)
            return false;

        for (int i = 0; i < a.length; i++) {

            if (a[i] < '0' || a[i] > '9')
                return false;
        }
        return true;
    }


}

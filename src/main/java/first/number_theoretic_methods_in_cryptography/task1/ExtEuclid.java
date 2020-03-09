package first.number_theoretic_methods_in_cryptography.task1;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class ExtEuclid extends GCD {
    Long ax;
    Long by;

    public ExtEuclid(Long a, Long b) {
        super(a, b);
    }

    @Override
    public Long GCD(Long a, Long b) throws WrongInputException {
        ArrayList<Long> r = Lists.newArrayList(a, b);
        ArrayList<Long> x = Lists.newArrayList(1l, 0l);
        ArrayList<Long> y = Lists.newArrayList(0l, 1l);
        int i = 1;
        while (true) {
            r.add(r.get(i - 1) % r.get(i));
            if (r.get(i + 1) == 0) {
                ax = x.get(i);
                by = y.get(i);
                System.out.println("ExtEuclid: ax + by = d:  " + a + " * " + ax + " + " + b + " * " + by + " = " + r.get(i));
                return r.get(i);
            } else {
                Long qi = (r.get(i - 1) - r.get(i + 1)) / r.get(i);
                x.add(x.get(i - 1) - qi * x.get(i));
                y.add(y.get(i - 1) - qi * y.get(i));
                i++;
            }
        }
    }
}

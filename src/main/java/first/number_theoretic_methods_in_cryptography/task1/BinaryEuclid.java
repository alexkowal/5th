package first.number_theoretic_methods_in_cryptography.task1;

public class BinaryEuclid extends GCD {

    public BinaryEuclid(Long a, Long b) {
        super(a, b);
    }

    @Override
    public Long GCD(Long a, Long b) throws WrongInputException {
        int g = 1;
        while (isEven(a) && isEven(b)) {
            a /= 2;
            b /= 2;
            g *= 2;
        }
        Long u = a;
        Long v = b;
        while (u != 0) {
            while (isEven(u))
                u /= 2;
            while (isEven(v))
                v /= 2;
            if (u >= v)
                u = u - v;
            else v = v - u;
        }
        return g * v;
    }

    private boolean isEven(Long a) {
        return a % 2 == 0;
    }

}

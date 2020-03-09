package first.number_theoretic_methods_in_cryptography.task1;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Euclid {

    public static Long GCD(Long a, Long b) throws WrongInputException {
        if (b == 0)
            return a;
        else return GCD(b, a % b);
    }
}

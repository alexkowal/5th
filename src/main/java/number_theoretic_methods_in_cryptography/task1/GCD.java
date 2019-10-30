package number_theoretic_methods_in_cryptography.task1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class GCD {
    Long a;
    Long b;

    public abstract Long GCD(Long a, Long b) throws WrongInputException;
}

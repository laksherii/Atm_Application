package atm.application.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Card {
    @EqualsAndHashCode.Include
    private long id;
    @EqualsAndHashCode.Include
    private String cardNumber;

    @Setter
    private BigDecimal balance;

    private long pin;

    @Setter
    private boolean isCardNonBlocked;

    @Setter
    private int countOfInputIncorrectPin;
}

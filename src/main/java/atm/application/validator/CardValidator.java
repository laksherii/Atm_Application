package atm.application.validator;

import atm.application.exception.data.IncorrectDataException;
import atm.application.model.Card;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class CardValidator {
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");

    public boolean validateCard(Card card) {
        String cardNumber = card.getCardNumber();
        if (!isCardNumberValid(cardNumber)) return false;

        // В дальнейшем может расширяться

        return true;
    }

    public boolean isCardNumberValid(String cardNumber) {
        return CARD_NUMBER_PATTERN.matcher(cardNumber).matches();
    }

    public boolean isCardBalanceValidForWithdrawMoney(BigDecimal cardBalance, BigDecimal desiredAmount) {
        return cardBalance.compareTo(desiredAmount) >= 0;
    }

    public void validCardPin(Card card, long cardPin) {
        if (card.getPin() != cardPin) {

            int currentCountOfInputIncorrectPin = card.getCountOfInputIncorrectPin() + 1;
            card.setCountOfInputIncorrectPin(currentCountOfInputIncorrectPin);

            int countOfRemainingAttempts = 3 - currentCountOfInputIncorrectPin;
            String errorMessage;

            if (countOfRemainingAttempts == 0) {
                errorMessage = "Неправильный PIN-код. Карта заблокирована из-за частых попыток ввода!";
                card.setCardNonBlocked(false);
            } else {
                errorMessage = "Неправильный PIN-код. Попробуйте снова. Осталось попыток: " + countOfRemainingAttempts;
            }

            throw new IncorrectDataException(400, errorMessage);
        } else {
            card.setCountOfInputIncorrectPin(0);
        }
    }

}

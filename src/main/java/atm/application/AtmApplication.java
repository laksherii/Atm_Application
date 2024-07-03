package atm.application;

import atm.application.exception.ApplicationException;
import atm.application.exception.data.IncorrectDataException;
import atm.application.exception.validate.BlockedCardException;
import atm.application.model.Card;
import atm.application.model.operation.OperationType;
import atm.application.validator.CardValidator;

import java.math.BigDecimal;
import java.util.Set;

import static atm.application.dao.CardDownloader.downloadCards;
import static atm.application.dao.CardUploader.uploadCards;
import static atm.application.util.InputUtil.getInputCardNumber;
import static atm.application.util.InputUtil.getInputCardPin;
import static atm.application.util.InputUtil.getInputDepositedAmount;
import static atm.application.util.InputUtil.getInputDesiredAmount;
import static atm.application.util.InputUtil.getInputUserOperation;
import static atm.application.util.InputUtil.showUserOperations;
import static java.lang.System.out;

public class AtmApplication {

    private static final CardValidator CARD_VALIDATOR = new CardValidator();

    private static final Set<Card> CARDS;
    static {
        CARDS = downloadCards();
    }

    public static void main(String[] args) {
        out.println("********   БАНКОМАТ RAIFFEISEN   ********");
        out.println("\n\n");

        while (true) {
            showUserOperations();

            OperationType operationType;
            try {
                operationType = getInputUserOperation();
            } catch (ApplicationException applicationException) {
                System.err.println(applicationException.getMessage());
                continue;
            }

            switch (operationType) {
                case CHECK_CARD_BALANCE:
                    printCardBalance(
                            getCardFromSetByCardNumberAndPin());
                    break;

                case GET_MONEY_FROM_CARD:
                    debitingMoneyFromCard(
                            getCardFromSetByCardNumberAndPin()
                    );

                    break;

                case ADD_MONEY_TO_BALANCE:
                    depositingMoneyToCard(
                            getCardFromSetByCardNumberAndPin()
                    );

                    break;

                case EXIT:
                    out.println("\n\n");
                    out.println("********   БАНКОМАТ RAIFFEISEN   ********");
                    out.println("********     УДАЧНОГО ДНЯ!    ********");

                    uploadCards(CARDS);

                    return;
            }
        }
    }

    private static void depositingMoneyToCard(Card card) {
        try {
            if (card.getId() != 0) {
                BigDecimal depositedAmount = getInputDepositedAmount();

                BigDecimal currentBalance = card.getBalance().add(depositedAmount);
                card.setBalance(currentBalance);

                out.println("Средства успешно внесены! Ожидайте зачисление!");
            }
        } catch (ApplicationException applicationException) {
            System.err.println(applicationException.getMessage());
        }
    }

    private static void debitingMoneyFromCard(Card card) {
        try {
            if (card.getId() != 0) {
                BigDecimal desiredAmount = getInputDesiredAmount(card);

                BigDecimal currentBalance = card.getBalance().subtract(desiredAmount);
                card.setBalance(currentBalance);

                out.println("Средства успешно списаны! Ожидайте выдачу!");
            }
        } catch (ApplicationException applicationException) {
            System.err.println(applicationException.getMessage());
        }
    }

    private static void printCardBalance(Card card) {
        if (card.getId() != 0) {
            out.println("Ваш баланс составляет " + card.getBalance() + "$");
        }
    }

    private static Card getCardFromSetByCardNumberAndPin() {
        try {
            String cardNumber = getInputCardNumber();

            Card card = CARDS.stream()
                    .filter(cardFromSet -> cardFromSet.getCardNumber().equals(cardNumber))
                    .findAny()
                    .orElseThrow(() -> new IncorrectDataException(400, "Неправильный номер карты. Попробуйте снова"));

            if (!card.isCardNonBlocked()) {
                throw new BlockedCardException(403, "Карта заблокирована! Обратитесь к оператору для снятия блокировки!");
            }

            long cardPin = getInputCardPin();
            CARD_VALIDATOR.validCardPin(card, cardPin);

            return card;
        } catch (ApplicationException applicationException) {
            System.err.println(applicationException.getMessage());
        }
        
        return new Card();
    }
}
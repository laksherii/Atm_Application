package atm.application.util;

import atm.application.exception.input.InputException;
import atm.application.model.Card;
import atm.application.model.operation.OperationType;

import atm.application.validator.CardValidator;
import lombok.experimental.UtilityClass;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Scanner;

import static atm.application.util.OperationUtil.getOperationTypeFromId;

import static java.lang.System.in;

@UtilityClass
public class InputUtil {
    private static final CardValidator CARD_VALIDATOR = new CardValidator();
    private static final Scanner SCANNER = new Scanner(new InputStreamReader(in));

    public static String getInputCardNumber() {
        System.out.println("Введите номер карты");

        String cardNumber = SCANNER.next();
        boolean isCardNumberValid = CARD_VALIDATOR.isCardNumberValid(cardNumber);
        if (!isCardNumberValid) {
            throw new InputException(400, "Введен невалидный номер карты. Попробуйте снова");
        }

        return cardNumber;
    }

    public static long getInputCardPin() {
        try {
            System.out.println("Введите PIN-код карты");
            return Long.parseLong(SCANNER.next());
        } catch (NumberFormatException numberFormatException) {
            throw new InputException(400, "Неверный тип PIN-кода. Попробуйте снова.");
        }
    }

    public static OperationType getInputUserOperation() {
        int operationTypeId;
        try {
            operationTypeId = Integer.parseInt(SCANNER.next());
        } catch (NumberFormatException numberFormatException) {
            throw new InputException(400, "Неверный тип или значение операции. Попробуйте снова.");
        }

        return getOperationTypeFromId(operationTypeId);
    }

    public static BigDecimal getInputDesiredAmount(Card card) {
        System.out.println("Введите сумму, которую хотите снять");

        BigDecimal desiredAmount;
        try {
            desiredAmount = BigDecimal.valueOf(Double.parseDouble(SCANNER.next()));
        } catch (NumberFormatException numberFormatException) {
            throw new InputException(400, "Невалидная сумма. Попробуйте снова.");
        }
        
        boolean isDesiredAmountValid = CARD_VALIDATOR.isCardBalanceValidForWithdrawMoney(card.getBalance(), desiredAmount);
        if (isDesiredAmountValid) {
            return desiredAmount;
        } else {
            throw new InputException(400, "Недостаточно средств! Веедите другую сумму!");
        }
    }

    public static BigDecimal getInputDepositedAmount() {
        System.out.println("Введите сумму, которую хотите внести");

        try {
            return BigDecimal.valueOf(Double.parseDouble(SCANNER.next()));
        } catch (NumberFormatException numberFormatException) {
            throw new InputException(400, "Невалидная сумма. Попробуйте снова.");
        }
    }

    public static void showUserOperations() {
        System.out.println("1. Проверить баланс карты");
        System.out.println("2. Снять средства со счета");
        System.out.println("3. Пополнить баланс");
        System.out.println("4. Выход из банкомата");
    }
}

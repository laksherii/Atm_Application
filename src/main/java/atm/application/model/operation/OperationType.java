package atm.application.model.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {
    CHECK_CARD_BALANCE(1),
    GET_MONEY_FROM_CARD(2),
    ADD_MONEY_TO_BALANCE(3),
    EXIT(4);

    private final int idOperationType;
}

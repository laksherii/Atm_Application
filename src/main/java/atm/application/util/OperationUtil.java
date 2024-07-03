package atm.application.util;

import atm.application.exception.input.InputException;
import atm.application.model.operation.OperationType;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class OperationUtil {
    public static OperationType getOperationTypeFromId(int idOperationType) {
        return Arrays
                .stream(OperationType.values())
                .filter(operationType -> operationType.getIdOperationType() == idOperationType)
                .findFirst()
                .orElseThrow(() -> new InputException(400, "Неверный тип операции. Попробуйте снова."));
    }
}

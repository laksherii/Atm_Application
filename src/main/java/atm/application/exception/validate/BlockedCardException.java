package atm.application.exception.validate;

import atm.application.exception.ApplicationException;

public class BlockedCardException extends ApplicationException {
    public BlockedCardException(int code, String message) {
        super(code, message);
    }
}

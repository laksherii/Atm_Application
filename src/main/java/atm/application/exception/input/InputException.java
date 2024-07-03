package atm.application.exception.input;

import atm.application.exception.ApplicationException;

public class InputException extends ApplicationException {
    public InputException(int code, String message) {
        super(code, message);
    }
}

package atm.application.exception.data;

import atm.application.exception.ApplicationException;

public class IncorrectDataException extends ApplicationException {
    public IncorrectDataException(int code, String message) {
        super(code, message);
    }
}

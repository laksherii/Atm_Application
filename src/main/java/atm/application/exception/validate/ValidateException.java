package atm.application.exception.validate;

import atm.application.exception.ApplicationException;

public class ValidateException extends ApplicationException {
    public ValidateException(int code, String message) {
        super(code, message);
    }
}

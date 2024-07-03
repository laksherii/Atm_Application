package atm.application.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
    private final int exceptionCode;

    protected ApplicationException(int code, String message) {
        super(message);
        this.exceptionCode = code;
    }
}

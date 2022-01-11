package finalgroup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String arg0) {
        super(arg0);
    }
}

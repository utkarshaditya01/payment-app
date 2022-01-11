package finalgroup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionDeclinedException extends RuntimeException{

    public TransactionDeclinedException(String arg0) {
        super(arg0);
    }

}


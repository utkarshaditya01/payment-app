package finalgroup.DTO;

import finalgroup.enums.State;

public class RequestServerTransactionDTO {
    private Integer transactionId;
    private State state;

    public RequestServerTransactionDTO(Integer transactionId, State state) {
        this.transactionId = transactionId;
        this.state = state;
    }
    public Integer getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
}


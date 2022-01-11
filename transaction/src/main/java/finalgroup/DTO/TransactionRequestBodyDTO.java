package finalgroup.DTO;

import finalgroup.enums.Type;

public class TransactionRequestBodyDTO {
    private Type type;
    private Integer amount;


    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

package finalgroup.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import finalgroup.enums.State;
import finalgroup.enums.Type;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Transaction {

    @Id
    @SequenceGenerator(name = "transaction_id_seq", sequenceName = "transaction_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    private Integer transactionId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wallet wallet;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Transaction() {
    }

    public Transaction(Integer transactionId, Wallet wallet, Integer amount, State state, Type type) {
        this.transactionId = transactionId;
        this.wallet = wallet;
        this.amount = amount;
        this.state = state;
        this.type = type;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction [amount=" + amount + ", state=" + state + ", transactionId=" + transactionId + ", type="
                + type + "]";
    }

}

package finalgroup.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Wallet implements Serializable{

    private static final long serialVersionUID = -4439114469417994311L;

    @Id
    @SequenceGenerator(
            name="wallet_id_seq",
            sequenceName="wallet_id_seq",
            allocationSize=1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="wallet_id_seq"
    )
    private Integer id;

    private String username;

    private Integer balance;

    public Wallet(Integer id, String username, Integer balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public Wallet() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Wallet [id=" + id + "]";
    }

}


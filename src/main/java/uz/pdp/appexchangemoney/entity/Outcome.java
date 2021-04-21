package uz.pdp.appexchangemoney.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "the fromCard must not be null")
    @ManyToOne
    private Card fromCard;

    @NotNull(message = "the toCard must not be null")
    @ManyToOne
    private Card toCard;

    @NotNull(message = "the amount must not be null")
    private Double amount;

    private Date date;

    private Double commissionAmount;


    public Outcome(Card fromCard,Card toCard, Double amount, Date date, Double commissionAmount) {
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.amount = amount;
        this.date = date;
        this.commissionAmount = commissionAmount;
    }
}

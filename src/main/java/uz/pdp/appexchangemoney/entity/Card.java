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
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "the user must not be null")
    private String username;

    @NotNull(message = "the number must not be null")
    @Column(unique = true,nullable = false)
    private Long number;

    @NotNull(message = "the balance must not be null")
    private Double balance;

    @NotNull(message = "the expireDate must not be null")
    private Date expireDate;

    private boolean active=true;
}

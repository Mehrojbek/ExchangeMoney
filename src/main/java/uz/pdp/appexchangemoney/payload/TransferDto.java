package uz.pdp.appexchangemoney.payload;

import lombok.Data;

@Data
public class TransferDto {
    private Long fromCardNumber;
    private Double amount;
    private Long toCardNumber;
}

package uz.pdp.appexchangemoney.payload;

import lombok.Data;

import java.sql.Date;

@Data
public class CardDto {
    private Long number;
    private Date expireDate;
}

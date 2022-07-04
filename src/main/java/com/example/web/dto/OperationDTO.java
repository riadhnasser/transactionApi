package com.example.web.dto;

import com.example.web.constants.OperationType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO {

    OperationType type;
    Date date;
    BigDecimal amount;

}

package com.example.web.model;

import com.example.web.dto.OperationDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Operation extends OperationDTO {

    String clientId;
}

package com.example.web.service;

import com.example.web.dto.OperationDTO;
import com.example.web.dto.OperationResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {

    OperationResponseDTO deposit(BigDecimal amount, String clientId);

    OperationResponseDTO withdraw(BigDecimal amount, String clientId);

    List<OperationDTO> getClientOperationsList(String clientId);
}

package com.example.web.service.impl;

import com.example.web.constants.OperationType;
import com.example.web.dto.OperationDTO;
import com.example.web.dto.OperationResponseDTO;
import com.example.web.model.Operation;
import com.example.web.service.ITransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private List<Operation> operationList;

    @Override
    public OperationResponseDTO deposit(BigDecimal amount, String clientId) {
        this.addOperation(amount, clientId, OperationType.DEPOSIT);
        OperationResponseDTO operationResponse = OperationResponseDTO.builder()
                .amount(amount)
                .date(new Date())
                .type(OperationType.DEPOSIT)
                .balance(this.getClientBalance(clientId))
                .build();

        return operationResponse;
    }

    @Override
    public OperationResponseDTO withdraw(BigDecimal amount, String clientId) {
        this.addOperation(amount, clientId, OperationType.WITHDRAWAL);
        OperationResponseDTO operationResponse = OperationResponseDTO.builder()
                .amount(amount)
                .date(new Date())
                .type(OperationType.WITHDRAWAL)
                .balance(this.getClientBalance(clientId))
                .build();

        return operationResponse;
    }

    @Override
    public List<OperationDTO> getClientOperationsList(String clientId) {
        List<OperationDTO> clientOperationsList = this.operationList
                .stream()
                .filter(el -> el.getClientId().equals(clientId))
                .map(el ->
                        OperationDTO.builder()
                                .amount(el.getAmount())
                                .date(el.getDate())
                                .type(el.getType())
                                .build())
                .collect(Collectors.toList());

        return clientOperationsList;
    }

    private void addOperation(BigDecimal amount, String clientId, OperationType operationType) {
        Operation newOperation = Operation.builder()
                .clientId(clientId)
                .type(operationType)
                .date(new Date())
                .amount(amount)
                .build();
        this.operationList.add(newOperation);
    }

    private BigDecimal getClientBalance(String clientId) {
        List<Operation> clientOperationsList = this.operationList
                .stream()
                .filter(el -> el.getClientId().equals(clientId))
                .collect(Collectors.toList());

        BigDecimal withdrawalSum = clientOperationsList
                .stream()
                .filter(el -> el.getType().equals(OperationType.WITHDRAWAL))
                .map(el -> el.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal depositSum = clientOperationsList
                .stream()
                .filter(el -> el.getType().equals(OperationType.DEPOSIT))
                .map(el -> el.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return depositSum.min(withdrawalSum);

    }
}

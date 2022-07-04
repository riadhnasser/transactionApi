package com.example.web.controllers;

import com.example.web.constants.Constants;
import com.example.web.constants.OperationType;
import com.example.web.dto.OperationDTO;
import com.example.web.dto.OperationRequestDTO;
import com.example.web.dto.OperationResponseDTO;
import com.example.web.model.Operation;
import com.example.web.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping(value = "/operation")
    public ResponseEntity<OperationResponseDTO> clientOperation(@RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
                                             @RequestBody(required = true) OperationRequestDTO operationRequest) {
        OperationResponseDTO result;
        if (operationRequest.getType().equals(OperationType.DEPOSIT)) {
            result = transactionService.deposit(operationRequest.getAmount(), clientId);
        } else if (operationRequest.getType().equals(OperationType.WITHDRAWAL)) {
            result = transactionService.withdraw(operationRequest.getAmount(), clientId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/history")
    public ResponseEntity<List<OperationDTO>> getOperationHistory(@RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId) {
        List<OperationDTO> operationDTOList = transactionService.getClientOperationsList(clientId);

        return ResponseEntity.status(HttpStatus.OK).body(operationDTOList);
    }
}

package com.bank.fake_financial_institution.controller;

import com.bank.fake_financial_institution.dto.TransferDTO;
import com.bank.fake_financial_institution.service.TransferService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<?> createTransfer(@RequestBody TransferDTO transferDTO) {
        try {
            TransferDTO result = transferService.create(transferDTO);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}



package com.example.csi_5230_final.DTO.transaction;

import java.util.List;

public class TransactionDTO {
    private List<TransactionItemDTO> transactions;

    public List<TransactionItemDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionItemDTO> transactions) {
        this.transactions = transactions;
    }
}

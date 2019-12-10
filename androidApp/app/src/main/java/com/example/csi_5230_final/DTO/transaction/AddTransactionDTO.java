package com.example.csi_5230_final.DTO.transaction;

import com.example.csi_5230_final.DTO.TokenDTO;

public class AddTransactionDTO extends TokenDTO {
    private TransactionItemDTO transaction;

    public TransactionItemDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionItemDTO transaction) {
        this.transaction = transaction;
    }
}

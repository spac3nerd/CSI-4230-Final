package com.example.csi_5230_final.DTO.cashflow;

import java.util.List;

public class CashflowResponseDTO {
    private List<CashflowItemDTO> income;
    private List<CashflowItemDTO> expense;

    public List<CashflowItemDTO> getIncome() {
        return income;
    }

    public void setIncome(List<CashflowItemDTO> income) {
        this.income = income;
    }

    public List<CashflowItemDTO> getExpense() {
        return expense;
    }

    public void setExpense(List<CashflowItemDTO> expense) {
        this.expense = expense;
    }
}

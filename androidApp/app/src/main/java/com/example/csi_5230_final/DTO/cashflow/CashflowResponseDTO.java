package com.example.csi_5230_final.DTO.cashflow;

import java.util.ArrayList;
import java.util.List;

public class CashflowResponseDTO {
    private ArrayList<CashflowItemDTO> income;
    private ArrayList<CashflowItemDTO> expense;

    public List<CashflowItemDTO> getIncome() {
        return income;
    }

    public void setIncome(ArrayList<CashflowItemDTO> income) {
        this.income = income;
    }

    public List<CashflowItemDTO> getExpense() {
        return expense;
    }

    public void setExpense(ArrayList<CashflowItemDTO> expense) {
        this.expense = expense;
    }

    public int getNumberOfEntries() {
        return Math.max(income.size(), expense.size());
    }
}

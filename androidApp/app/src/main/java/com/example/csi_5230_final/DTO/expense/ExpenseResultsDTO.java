package com.example.csi_5230_final.DTO.expense;

import com.example.csi_5230_final.DTO.cashflow.CashflowItemDTO;

import java.util.ArrayList;

public class ExpenseResultsDTO {

    private ArrayList<SpendingItemDTO> spending;

    public ArrayList<SpendingItemDTO> getSpending() {
        if (spending == null) {
            spending = new ArrayList<SpendingItemDTO>();
        }
        return spending;
    }

    public void setSpending(ArrayList<SpendingItemDTO> spending) {
        this.spending = spending;
    }

}

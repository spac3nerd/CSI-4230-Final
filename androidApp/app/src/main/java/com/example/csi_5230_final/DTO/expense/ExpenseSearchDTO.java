package com.example.csi_5230_final.DTO.expense;

import com.example.csi_5230_final.DTO.TokenDTO;

public class ExpenseSearchDTO extends TokenDTO {

    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}

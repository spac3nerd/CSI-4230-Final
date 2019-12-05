package com.example.csi_5230_final.DTO.cashflow;

public class CashflowItemDTO {
    private String month;
    private double value;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

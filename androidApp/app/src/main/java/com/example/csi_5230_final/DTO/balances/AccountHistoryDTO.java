package com.example.csi_5230_final.DTO.balances;

public class AccountHistoryDTO {
    private String date;
    private float value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

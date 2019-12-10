package com.example.csi_5230_final.DTO.transaction;

public class TransactionItemDTO {
    private String account_id;
    private String account_name;
    private float amount;
    private String category_primary;
    private String category_secondary;
    private String date;
    private String name;
    private String transaction_id;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory_primary() {
        return category_primary;
    }

    public void setCategory_primary(String category_primary) {
        this.category_primary = category_primary;
    }

    public String getCategory_secondary() {
        return category_secondary;
    }

    public void setCategory_secondary(String category_secondary) {
        this.category_secondary = category_secondary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}

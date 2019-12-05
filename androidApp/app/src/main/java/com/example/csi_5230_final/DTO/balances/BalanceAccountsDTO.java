package com.example.csi_5230_final.DTO.balances;

import java.util.ArrayList;

public class BalanceAccountsDTO {
    private String account_id;
    private String account_name;
    private ArrayList<AccountHistoryDTO> history;

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

    public ArrayList<AccountHistoryDTO> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<AccountHistoryDTO> history) {
        this.history = history;
    }
}

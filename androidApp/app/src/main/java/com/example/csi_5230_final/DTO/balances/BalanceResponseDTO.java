package com.example.csi_5230_final.DTO.balances;

import java.util.ArrayList;

public class BalanceResponseDTO {

    private ArrayList<BalanceAccountsDTO> accounts;

    public ArrayList<BalanceAccountsDTO> getAccounts() {
        if (accounts == null) {
            accounts = new ArrayList<BalanceAccountsDTO>();
        }
        return accounts;
    }

    public void setAccounts(ArrayList<BalanceAccountsDTO> accounts) {
        this.accounts = accounts;
    }

}

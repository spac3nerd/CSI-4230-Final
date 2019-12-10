package com.example.csi_5230_final.constants;

public class Constants {
    public static final String baseURL = "http://10.0.2.2:8080/";

    public static final int INVALID_USERNAME = 10;
    public static final int INVALID_PASSWORD = 11;
    public static final int LOGIN_FAIL = 30;
    public static final int GENERIC_FAIL = 31;

    //Add Transactions
    public static final int CATEGORIES_DATA = 40;
    public static final int TRANSACTION_ITEM_ADDED = 45;

    //Transactions
    public static final int TRANSACTION_DATA = 50;

    //Cashflow
    public static final int CASHFLOW_DATA = 60;

    //Expenses
    public static final int EXPENSE_DATA = 70;
    public static final int SPENDING_MONTHS = 71;

    //Balances
    public static final int BALANCE_DATA = 80;

    //Data packet constants
    public static final String AUTH_TOKEN_HEADER = "authToken";


    //Transaction table headers
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_ACCOUNT = "Account";
    public static final String HEADER_NAME = "Name";
    public static final String HEADER_CAT = "Category";
    public static final String HEADER_SUB_CAT = "Sub Category";
    public static final String HEADER_AMOUNT = "Amount";
}

package com.example.csi_5230_final.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.csi_5230_final.DTO.TokenDTO;
import com.example.csi_5230_final.DTO.expense.SpendingMonthsDTO;
import com.example.csi_5230_final.DTO.transaction.TransactionDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.example.csi_5230_final.customAdapters.TransactionTableAdapter;
import com.example.csi_5230_final.views.transaction_table.TransactionTableView;
import com.google.gson.Gson;

import java.io.IOException;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Transactions extends AppCompatActivity {

    private TokenDTO tokenDTO;
    String baseURL = Constants.baseURL;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient http;

    private TransactionDTO transactionDTO;
    private Handler APIHandler;

    TransactionTableView tableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();
        setTitle("Transactions");

        tableView = (TransactionTableView) findViewById(R.id.tableView);

        setupAPIHandler();
        getTransactionData();
    }


    private void setupAPIHandler() {
        APIHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.TRANSACTION_DATA:
                        updateTableData();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void updateTableData() {
        TransactionTableAdapter tableAdapter = new TransactionTableAdapter(this, transactionDTO.getTransactions(), tableView);
        tableView.setDataAdapter(tableAdapter);
    }

    private void getTransactionData() {
        RequestBody body = RequestBody.create(JSON, gson.toJson(tokenDTO));
        Request transactionRequest = new Request.Builder().url(baseURL + "transactions/getall")
                .post(body).build();


        http.newCall(transactionRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("Something went Wrong - Spending Months call");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    transactionDTO = gson.fromJson(response.body().string(), TransactionDTO.class);
                    APIHandler.sendEmptyMessage(Constants.TRANSACTION_DATA);
                }
            }
        });
    }


}

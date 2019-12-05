package com.example.csi_5230_final.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.csi_5230_final.DTO.TokenDTO;
import com.example.csi_5230_final.DTO.cashflow.CashflowResponseDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Cashflow extends AppCompatActivity {

    private TokenDTO tokenDTO;
    String baseURL = Constants.baseURL;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();

    OkHttpClient http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow);

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();

        //fetch the cashflow data
        getAllCashflow();
    }

    //Make API call to get all of the user's cashflow
    private void getAllCashflow() {
        RequestBody body = RequestBody.create(JSON, gson.toJson(tokenDTO));
        Request cashflowRequest = new Request.Builder().url(baseURL + "cashflow/all")
                .post(body).build();


        http.newCall(cashflowRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("Something went Wrong - Cashflow API call");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response);

                CashflowResponseDTO responseDTO = gson.fromJson(response.body().string(), CashflowResponseDTO.class);
                System.out.println(responseDTO);
            }
        });



    }
}

package com.example.csi_5230_final.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.csi_5230_final.DTO.TokenDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class AddTransactions extends AppCompatActivity {
    private TokenDTO tokenDTO;
    String baseURL = Constants.baseURL;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient http;

    private DatePicker datePicker;
    private EditText description;
    private EditText amount;
    private Spinner category;
    private Spinner subCategory;
    private ArrayAdapter catAdapter;
    private ArrayAdapter subCatAdapter;

    private String[] categoryList = {"All Months"};
    private String[] subCategoryList = {"All Months"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transactions);

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();

        //set up category/sub-cat spinners
        catAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                categoryList);
        category.setAdapter(catAdapter);

        subCatAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                subCategoryList);
        subCategory.setAdapter(subCatAdapter);

        setTitle("New Transaction");
    }
}

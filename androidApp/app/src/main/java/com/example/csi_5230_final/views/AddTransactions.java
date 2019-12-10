package com.example.csi_5230_final.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.csi_5230_final.DTO.LoginDTO;
import com.example.csi_5230_final.DTO.TokenDTO;
import com.example.csi_5230_final.DTO.categories.CategoriesDTO;
import com.example.csi_5230_final.DTO.expense.SpendingMonthsDTO;
import com.example.csi_5230_final.DTO.transaction.AddTransactionDTO;
import com.example.csi_5230_final.DTO.transaction.TransactionItemDTO;
import com.example.csi_5230_final.MainActivity;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddTransactions extends AppCompatActivity {

    private static Context context;

    private TokenDTO tokenDTO;
    String baseURL = Constants.baseURL;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient http;
    private Handler APIHandler;

    private DatePicker datePicker;
    private EditText description;
    private EditText amount;
    private Spinner category;
    private Spinner subCategory;
    private ArrayAdapter catAdapter;
    private ArrayAdapter subCatAdapter;
    Button submitBtn;

    private String[] categoryList = {"All Months"};
    private String[] subCategoryList = {"All Months"};

    private String selectedPrimaryCat;
    private String selectedSecondaryCat;

    private CategoriesDTO allCategories;
    private ArrayList<String> activeSecondary;


    public static Context getAppContext() {
        return AddTransactions.context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transactions);

        AddTransactions.context = getBaseContext();

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();

        datePicker = findViewById(R.id.datePicker);
        description = findViewById(R.id.description);
        amount = findViewById(R.id.amount);
        category = findViewById(R.id.category);
        subCategory = findViewById(R.id.subCategory);
        submitBtn = findViewById(R.id.addButton);

        setTitle("New Transaction");
        setupAPIHandler();
        getCategories();
        setAddAction();
    }

    private void setupAPIHandler() {
        APIHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.CATEGORIES_DATA:
                        setCategoriesDropDowns();
                        break;
                    case Constants.GENERIC_FAIL:
                        somethingWentWrongMessage();
                        break;
                    case Constants.TRANSACTION_ITEM_ADDED:
                        itemAddedMessage();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void setCategoriesDropDowns() {
        //set up category/sub-cat spinners
        catAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                allCategories.getPrimaryCategories());
        category.setAdapter(catAdapter);


        activeSecondary = allCategories.getSecondaryCategories().get(0);
        subCatAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                allCategories.getSecondaryCategories().get(0));
        subCategory.setAdapter(subCatAdapter);


        //primary category select handler
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPrimaryCat = allCategories.getPrimaryCategories().get(position);

                //set the secondary category based on the primary selection
                activeSecondary = allCategories.getSecondaryCategories().get(position);

                subCatAdapter = new ArrayAdapter<String>(AddTransactions.getAppContext(),
                        android.R.layout.simple_spinner_item,
                        activeSecondary);
                subCategory.setAdapter(subCatAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //secondary category handler
        subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSecondaryCat = activeSecondary.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getCategories() {
        RequestBody body = RequestBody.create(JSON, gson.toJson(tokenDTO));
        Request categoriesRequest = new Request.Builder().url(baseURL + "categories/getall")
                .post(body).build();


        http.newCall(categoriesRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("Something went Wrong - Categories call");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    allCategories = gson.fromJson(response.body().string(), CategoriesDTO.class);
                    APIHandler.sendEmptyMessage(Constants.CATEGORIES_DATA);
                }
            }
        });
    }

    private void setAddAction() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String itemDescription = description.getText().toString();
                String itemAmount = amount.getText().toString();
                String itemPrimCat = selectedPrimaryCat;
                String itemSecCat = selectedSecondaryCat;

                int itemDateMonth = datePicker.getMonth() + 1;
                int itemDateDay = datePicker.getDayOfMonth();
                int itemDateYear = datePicker.getYear();


                if (itemDescription.length() == 0 ||
                        itemAmount.length() == 0 ||
                        itemPrimCat.length() == 0 ||
                        itemSecCat.length() == 0) {
                    requiredItems();
                    return;
                }

                //Build transaction item
                TransactionItemDTO newTransaction = new TransactionItemDTO();
                newTransaction.setName(itemDescription);
                newTransaction.setAmount(Float.parseFloat(itemAmount));
                newTransaction.setCategory_primary(selectedPrimaryCat);
                newTransaction.setCategory_secondary(selectedSecondaryCat);
                String date = String.valueOf(itemDateYear) + "-" + String.valueOf(itemDateMonth) + '-' + String.valueOf(itemDateDay);
                newTransaction.setDate(date);

                //build DTO which will be sent.
                AddTransactionDTO outgoingDTO = new AddTransactionDTO();
                outgoingDTO.setTransaction(newTransaction);
                outgoingDTO.setToken(tokenDTO.getToken());
                addTransaction(outgoingDTO);
            }
        });
    }

    private void requiredItems() {
        Toast.makeText(AddTransactions.this,"Missing Items!",Toast.LENGTH_SHORT).show();
    }

    private void somethingWentWrongMessage() {
        Toast.makeText(AddTransactions.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
    }

    private void itemAddedMessage() {
        Toast.makeText(AddTransactions.this,"Item Added",Toast.LENGTH_SHORT).show();
    }

    private void addTransaction(AddTransactionDTO outgoingDTO) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(outgoingDTO));
        Request loginRequest = new Request.Builder().url(baseURL + "transactions/add").post(body).build();


        http.newCall(loginRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                APIHandler.sendEmptyMessage(Constants.GENERIC_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response);
                APIHandler.sendEmptyMessage(Constants.TRANSACTION_ITEM_ADDED);
            }
        });
    }
}

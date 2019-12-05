package com.example.csi_5230_final.views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.csi_5230_final.DTO.TokenDTO;
import com.example.csi_5230_final.DTO.cashflow.CashflowResponseDTO;
import com.example.csi_5230_final.DTO.expense.SpendingMonthsDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
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

public class Expenses extends AppCompatActivity {
    private TokenDTO tokenDTO;
    String baseURL = Constants.baseURL;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient http;


    private Handler APIHandler;
    private PieChart chart;
    private SpendingMonthsDTO spendingMonths;
    private Spinner spendingMonthSpinner;
    private String[] spendingMonthsList = {"All"};
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();
        setTitle("Expenses");

        spendingMonthSpinner = findViewById(R.id.spendingMonth);
        setAdapter();

        spendingMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setupAPIHandler();
//        APIHandler.sendEmptyMessage(Constants.EXPENSE_DATA);

        getExpenseMonths();
    }

    private void setupAPIHandler() {
        APIHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.EXPENSE_DATA:
                        createExpensePieChart();
                        break;
                    case Constants.SPENDING_MONTHS:
                        updateDropDownValues();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    //gets the months which have expense data
    private void getExpenseMonths() {
        RequestBody body = RequestBody.create(JSON, gson.toJson(tokenDTO));
        Request cashflowRequest = new Request.Builder().url(baseURL + "spending/months")
                .post(body).build();


        http.newCall(cashflowRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("Something went Wrong - Spending Months call");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    spendingMonths = gson.fromJson(response.body().string(), SpendingMonthsDTO.class);
                    APIHandler.sendEmptyMessage(Constants.SPENDING_MONTHS);
                }
            }
        });
    }

    private void setAdapter() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                spendingMonthsList);
        spendingMonthSpinner.setAdapter(adapter);
    }

    private void updateDropDownValues() {
        spendingMonthsList = new String[spendingMonths.getSpendingMonths().size() + 1];
        spendingMonthsList[0] = "All";
        for (int k = 0; k < spendingMonths.getSpendingMonths().size(); k++) {
            spendingMonthsList[k + 1] = spendingMonths.getSpendingMonths().get(k);
        }
        setAdapter();
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Pie Chart");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


    private void createExpensePieChart() {
        chart = findViewById(R.id.expensePieChart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);


        //set up legend
        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


    }

    private void setExpensePieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();


    }

}

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
import com.example.csi_5230_final.DTO.expense.ExpenseResultsDTO;
import com.example.csi_5230_final.DTO.expense.ExpenseSearchDTO;
import com.example.csi_5230_final.DTO.expense.SpendingItemDTO;
import com.example.csi_5230_final.DTO.expense.SpendingMonthsDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private ExpenseResultsDTO expenseResults;
    private ExpenseResultsDTO primaryExpenseResults;
    private Spinner spendingMonthSpinner;
    private String[] spendingMonthsList = {"All Months"};
    private int lastSelected = 0;
    ArrayAdapter adapter;

    HashMap formatMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();
        setTitle("Monthly Expenses");

        spendingMonthSpinner = findViewById(R.id.spendingMonth);
        setAdapter();

        spendingMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lastSelected != position) {
                    lastSelected = position;
                    System.out.println("Selected: " + position);
                    getSpending(spendingMonthsList[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setupAPIHandler();
        getSpending("All Months");
        getExpenseMonths();
        createExpensePieChart();
    }

    private void setupAPIHandler() {
        APIHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.EXPENSE_DATA:
                        setExpensePieChartData();
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

    //gets the months which have expense data
    private void getSpending(String month) {
        String monthParam = "";
        Request spendingRequest;


        if (month.equals("All Months")) {
            RequestBody body = RequestBody.create(JSON, gson.toJson(tokenDTO));
            spendingRequest = new Request.Builder().url(baseURL + "spending/bycategory")
                    .post(body).build();
        }
        else {
            ExpenseSearchDTO expenseSearch = new ExpenseSearchDTO();
            expenseSearch.setToken(tokenDTO.getToken());
            expenseSearch.setMonth(month);
            RequestBody body = RequestBody.create(JSON, gson.toJson(expenseSearch));
            spendingRequest = new Request.Builder().url(baseURL + "spending/bycategory")
                    .post(body).build();
        }

        http.newCall(spendingRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("Something went Wrong - Spending call");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    expenseResults = gson.fromJson(response.body().string(), ExpenseResultsDTO.class);
                    APIHandler.sendEmptyMessage(Constants.EXPENSE_DATA);
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
        spendingMonthsList[0] = "All Months";
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
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

//        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(45f);
        chart.setTransparentCircleRadius(55f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);


        //set up legend
        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setTextColor(Color.BLACK);
        l.setYOffset(5f);
        l.setWordWrapEnabled(true);
        l.setTextSize(12f);

    }

    //aggregate primary expenses
    private void groupPrimaryExpenses(ExpenseResultsDTO originalResults) {
        formatMap = new HashMap();

        for (int k = 0; k < originalResults.getSpending().size(); k++) {
            //if this primary category has already been added
            if (formatMap.containsKey(originalResults.getSpending().get(k).getCategory_primary())) {
                formatMap.put(originalResults.getSpending().get(k).getCategory_primary(),
                        (float) formatMap.get(originalResults.getSpending().get(k).getCategory_primary()) + originalResults.getSpending().get(k).getTotal());
            }
            else {
                formatMap.put(originalResults.getSpending().get(k).getCategory_primary(),
                        originalResults.getSpending().get(k).getTotal());
            }
        }
        primaryExpenseResults = new ExpenseResultsDTO();

        Iterator it = formatMap.entrySet().iterator();
        SpendingItemDTO newItem;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            newItem = new SpendingItemDTO();
            String key = pair.getKey().toString();
            float value = (float) pair.getValue();

            newItem.setCategory_primary(key);
            newItem.setTotal(value);
            primaryExpenseResults.getSpending().add(newItem);
        }

    }

    private void setExpensePieChartData() {
        groupPrimaryExpenses(expenseResults); //updates primaryExpenseResults

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int k = 0; k < primaryExpenseResults.getSpending().size(); k++) {
            entries.add(new PieEntry(primaryExpenseResults.getSpending().get(k).getTotal(),
                    primaryExpenseResults.getSpending().get(k).getCategory_primary()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
//        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        chart.invalidate();
    }

}

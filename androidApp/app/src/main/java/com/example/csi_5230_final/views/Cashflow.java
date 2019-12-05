package com.example.csi_5230_final.views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.csi_5230_final.DTO.TokenDTO;
import com.example.csi_5230_final.DTO.cashflow.CashflowResponseDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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

    private CashflowResponseDTO cashflowResponse;
    private CombinedChart chart;

    private Handler APIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow);
        setTitle("Cash Flow");

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();

        setupAPIHandler();

        //fetch the cashflow data
        getAllCashflow();
    }

    private void setupAPIHandler() {
        APIHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.CASHFLOW_DATA:
                        createCashflowChart();
                        break;
                    default:
                        break;
                }
            }
        };
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

                cashflowResponse = gson.fromJson(response.body().string(), CashflowResponseDTO.class);
                APIHandler.sendEmptyMessage(Constants.CASHFLOW_DATA);
            }
        });

    }

    private void createCashflowChart() {
        chart = findViewById(R.id.cashflowChart);

        //Set up chart properties
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);

        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        //Set up legend properties
        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        //set up axes
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMinimum(0f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return cashflowResponse.getIncome().get((int) value).getMonth();
            }
        });


        //contains the combined data of bar + line chart
        CombinedData data = new CombinedData();
        data.setData(generateBarChartData()); //create bar chart dataset and set it
        data.setData(generateLineChartData()); //create line chart data and set it

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        chart.setData(data);

    }

    //Bar chart will have two columns - Income, Expense
    private BarData generateBarChartData() {
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        ArrayList<BarEntry> expenseEntries = new ArrayList<>();

        for (int k = 0; k < cashflowResponse.getIncome().size(); k++) {
            incomeEntries.add(new BarEntry(0, cashflowResponse.getIncome().get(k).getValue()));
            expenseEntries.add(new BarEntry(0, cashflowResponse.getExpense().get(k).getValue()));
        }

        BarDataSet incomeSet = new BarDataSet(incomeEntries, "Income");
        incomeSet.setColor(Color.rgb(0, 255, 0));
        incomeSet.setValueTextColor(Color.rgb(0, 255, 0));
        incomeSet.setValueTextSize(10f);
        incomeSet.setAxisDependency(YAxis.AxisDependency.LEFT);


        BarDataSet expenseSet = new BarDataSet(expenseEntries, "Expense");
        expenseSet.setColors(Color.rgb(255, 0, 0));
        expenseSet.setValueTextColor(Color.rgb(255, 0, 0));
        expenseSet.setValueTextSize(10f);
        expenseSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"





        BarData barData = new BarData(incomeSet, expenseSet);
        barData.setBarWidth(barWidth);
        barData.groupBars(0, groupSpace, barSpace);

        return barData;
    }

    //Line chart contains the net flow per month - (Income - Expense)
    private LineData generateLineChartData() {
        LineData lineData = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int k = 0; k < cashflowResponse.getIncome().size(); k++) {
            entries.add(new Entry(k + 0.5f, cashflowResponse.getIncome().get(k).getValue() + cashflowResponse.getExpense().get(k).getValue()));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "Net Cashflow");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineDataSet.setColor(Color.rgb(0, 0, 0));
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleColor(Color.rgb(0, 0, 0));
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setFillColor(Color.rgb(255, 255, 255));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(Color.rgb(0, 0, 0));

        lineData.addDataSet(lineDataSet);

        return lineData;
    }
}

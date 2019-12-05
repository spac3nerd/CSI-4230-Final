package com.example.csi_5230_final.views;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csi_5230_final.DTO.TokenDTO;
import com.example.csi_5230_final.DTO.balances.BalanceAccountsDTO;
import com.example.csi_5230_final.DTO.balances.BalanceResponseDTO;
import com.example.csi_5230_final.DTO.expense.SpendingMonthsDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Balances extends AppCompatActivity {
    private TokenDTO tokenDTO;
    String baseURL = Constants.baseURL;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient http;


    private LineChart chart;
    private int[] chartColors = {Color.rgb(240, 238, 70),
            Color.rgb(120, 255, 30), Color.rgb(0, 0, 255)};

    private Handler APIHandler;
    private BalanceResponseDTO responseDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balances);

        tokenDTO = new TokenDTO();
        tokenDTO.setToken(getIntent().getStringExtra("authToken"));
        http = new OkHttpClient();
        setTitle("Account Balance");


        chart = findViewById(R.id.balanceLineChart);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);


        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String[] tokens = responseDTO.getAccounts().get(0).getHistory().get((int) value).getDate().split(" ");
                return tokens[1] + "/" + tokens[2] + "/" + tokens[3];
            }
        });

        setupAPIHandler();
        getBalances();
    }

    private void setupAPIHandler() {
        APIHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.BALANCE_DATA:
                        setBalanceLineChartData();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void getBalances() {
        RequestBody body = RequestBody.create(JSON, gson.toJson(tokenDTO));
        Request cashflowRequest = new Request.Builder().url(baseURL + "accounts/balancehistory")
                .post(body).build();


        http.newCall(cashflowRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("Something went Wrong - Balance History call");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    responseDTO = gson.fromJson(response.body().string(), BalanceResponseDTO.class);
                    APIHandler.sendEmptyMessage(Constants.BALANCE_DATA);
                }
            }
        });
    }



    private LineData generateLineData() {

        LineData data = new LineData();

        ArrayList<Entry> entries;

        LineDataSet set;
        int max = responseDTO.getAccounts().size();

        for (int k = 0; k < max; k++) {
            entries =  new ArrayList<>();
            BalanceAccountsDTO balanceAccount = responseDTO.getAccounts().get(k);
            for (int n = 0; n < balanceAccount.getHistory().size(); n++) {
                entries.add(new Entry(n,
                        balanceAccount.getHistory().get(n).getValue()));
            }
            set = new LineDataSet(entries, responseDTO.getAccounts().get(k).getAccount_name());
            set.setColor(chartColors[k % max]);
            set.setLineWidth(2.5f);
            set.setCircleColor(chartColors[k % max]);
            set.setCircleRadius(5f);
            set.setFillColor(chartColors[k % max]);
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set.setDrawValues(true);
            set.setValueTextSize(10f);
            set.setValueTextColor(chartColors[k % max]);
            set.setAxisDependency(YAxis.AxisDependency.LEFT);

            data.addDataSet(set);
        }


        return data;
    }

    private void setBalanceLineChartData() {
        LineData data = generateLineData();
        chart.setData(data);
        chart.invalidate();
    }



}

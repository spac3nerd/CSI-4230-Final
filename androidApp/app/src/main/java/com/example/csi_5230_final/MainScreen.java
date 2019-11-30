package com.example.csi_5230_final;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.csi_5230_final.customAdapters.MainViewAdapter;

public class MainScreen extends AppCompatActivity {

    String authToken;
    String[] options = {"Transactions", "Balances", "Cash Flow", "Expenses"};
    int [] optionImg = {R.drawable.table, R.drawable.balance, R.drawable.pie, R.drawable.pie};

    MainViewAdapter mainAdapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_selection);

        authToken = getIntent().getStringExtra("authToken");

        list = findViewById(R.id.itemList);
        mainAdapter = new MainViewAdapter(this, this.options, this.optionImg);
        list.setAdapter(mainAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getApplicationContext(), options[position], Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}

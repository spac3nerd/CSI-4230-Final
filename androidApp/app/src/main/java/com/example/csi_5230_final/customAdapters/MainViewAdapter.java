package com.example.csi_5230_final.customAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.csi_5230_final.R;

public class MainViewAdapter extends ArrayAdapter<String> {

    private String[] itemList;
    private int[] itemImg;
    private Activity myContext;

    public MainViewAdapter(Activity context, String[] itemList, int[] itemImg) {
        super(context, R.layout.main_screen_selection, itemList);
        this.itemList = itemList;
        this.itemImg = itemImg;
        this.myContext = context;
    }

    static class ViewHolder {
        ImageView imgView;
        TextView txtView;
    }

    @Override
    public View getView(int position, @Nullable View rowView, @NonNull ViewGroup parent) {
        ViewHolder myVH = new ViewHolder();

        if (rowView == null) {
            LayoutInflater myInflater = myContext.getLayoutInflater();
            rowView = myInflater.inflate(R.layout.main_screen_selection, parent, false);
            myVH.imgView = rowView.findViewById(R.id.itemImage);
            myVH.txtView = rowView.findViewById(R.id.itemText);
            rowView.setTag(myVH);
        }
        else {
            myVH = (ViewHolder) rowView.getTag();
        }

        myVH.imgView.setImageResource(this.itemImg[position]);
        myVH.txtView.setText(this.itemList[position]);

        return rowView;
    }

}

package com.example.csi_5230_final.customAdapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.csi_5230_final.DTO.transaction.TransactionDTO;
import com.example.csi_5230_final.DTO.transaction.TransactionItemDTO;
import com.example.csi_5230_final.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;

import static java.lang.String.format;

public class TransactionTableAdapter extends LongPressAwareTableDataAdapter<TransactionItemDTO> {


    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();
    private Context context;


    public TransactionTableAdapter(final Context context, final List<TransactionItemDTO> data, final TableView<TransactionItemDTO> tableView) {
        super(context, data, tableView);
        this.context = context;
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TransactionItemDTO itemDTO = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderDate(itemDTO);
                break;
            case 1:
                renderedView = renderAccount(itemDTO);
                break;
            case 2:
                renderedView = renderName(itemDTO);
                break;
            case 3:
                renderedView = renderCategory(itemDTO);
                break;
            case 4:
                renderedView = renderSubCategory(itemDTO);
                break;
            case 5:
                renderedView = renderAmount(itemDTO);
                break;

        }

        return renderedView;
    }


    //**Render functions**/

    private View renderDate(final TransactionItemDTO itemDTO) {
        final TextView textView = new TextView(getContext());
        String dateText = itemDTO.getDate().split("T")[0];
        textView.setText(dateText);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setSingleLine();

        return textView;
    }

    private View renderAccount(final TransactionItemDTO itemDTO) {
        final TextView textView = new TextView(getContext());
        textView.setText(itemDTO.getAccount_name());
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setSingleLine();

        return textView;
    }


    private View renderName(final TransactionItemDTO itemDTO) {
        final TextView textView = new TextView(getContext());
        textView.setText(itemDTO.getName());
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setSingleLine();

        return textView;
    }

    private View renderCategory(final TransactionItemDTO itemDTO) {
        final TextView textView = new TextView(getContext());
        textView.setText(itemDTO.getCategory_primary());
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setSingleLine();

        return textView;
    }

    private View renderSubCategory(final TransactionItemDTO itemDTO) {
        final TextView textView = new TextView(getContext());
        textView.setText(itemDTO.getCategory_secondary());
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setSingleLine();

        return textView;
    }

    private View renderAmount(final TransactionItemDTO itemDTO) {
        final TextView textView = new TextView(getContext());
        float amount = itemDTO.getAmount() * -1;
        if (amount < 0.0) {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.table_loss));
        }
        else {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.table_income));
        }

        String displayAmount = String.format("%.2f", amount);
        textView.setText("$" + displayAmount);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setSingleLine();

        return textView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        return getDefaultCellView(rowIndex, columnIndex, parentView);
    }



}

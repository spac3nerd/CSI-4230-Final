package com.example.csi_5230_final.views.transaction_table;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;

import com.example.csi_5230_final.DTO.transaction.TransactionDTO;
import com.example.csi_5230_final.DTO.transaction.TransactionItemDTO;
import com.example.csi_5230_final.R;
import com.example.csi_5230_final.constants.Constants;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class TransactionTableView extends SortableTableView<TransactionItemDTO>{

    public TransactionTableView(final Context context) {
        this(context, null);
    }

    public TransactionTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public TransactionTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);

        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context,
                Constants.HEADER_DATE, Constants.HEADER_ACCOUNT, Constants.HEADER_NAME, Constants.HEADER_CAT, Constants.HEADER_SUB_CAT, Constants.HEADER_AMOUNT);
        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.table_header_text));
        setHeaderAdapter(simpleTableHeaderAdapter);

        final int rowColorEven = ContextCompat.getColor(context, R.color.table_data_row_even);
        final int rowColorOdd = ContextCompat.getColor(context, R.color.table_data_row_odd);
        setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        final TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(6);
        tableColumnWeightModel.setColumnWeight(0, 1);
        tableColumnWeightModel.setColumnWeight(1, 1);
        tableColumnWeightModel.setColumnWeight(2, 2);
        tableColumnWeightModel.setColumnWeight(3, 1);
        tableColumnWeightModel.setColumnWeight(4, 1);
        tableColumnWeightModel.setColumnWeight(5, 1);

        setColumnModel(tableColumnWeightModel);

//        setColumnComparator(0, CarComparators.getCarProducerComparator());
//        setColumnComparator(1, CarComparators.getCarNameComparator());
//        setColumnComparator(2, CarComparators.getCarPowerComparator());
//        setColumnComparator(3, CarComparators.getCarPriceComparator());
    }
}

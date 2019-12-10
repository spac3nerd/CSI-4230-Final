package com.example.csi_5230_final.views.transaction_table.comparators;

import com.example.csi_5230_final.DTO.transaction.TransactionItemDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public final class TransactionTableComparators {
    private TransactionTableComparators() {

    }

    public static Comparator<TransactionItemDTO> getAmountComparator() {
        return new getAmountComparator();
    }

    public static Comparator<TransactionItemDTO> getDateComparator() {
        return new getDateComparator();
    }

    private static class getAmountComparator implements Comparator<TransactionItemDTO> {

        @Override
        public int compare(final TransactionItemDTO item1, final TransactionItemDTO item2) {
            if (item1.getAmount() < item2.getAmount()) {
                return -1;
            }
            if (item1.getAmount() > item2.getAmount()) {
                return 1;
            }
            return 0;
        }
    }

    //TODO: I can improve the performance of this by quite a lot by sending the data packet differently from the server

    private static class getDateComparator implements Comparator<TransactionItemDTO> {

        @Override
        public int compare(final TransactionItemDTO item1, final TransactionItemDTO item2) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            Date date1;
            Date date2;
            try {
                String dateText1 = item1.getDate().split("T")[0];
                String dateText2 = item2.getDate().split("T")[0];
                date1 = formatter.parse(dateText1);
                date2 = formatter.parse(dateText2);

                if (date1.after(date2)) {
                    return -1;
                }
                if (date2.after(date1)) {
                    return 1;
                }

                return 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;


        }
    }
}

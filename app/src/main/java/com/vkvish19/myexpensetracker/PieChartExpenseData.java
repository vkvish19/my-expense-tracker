package com.vkvish19.myexpensetracker;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartExpenseData {
    private final ArrayList<PieEntry> expensesAmounts = new ArrayList<>();
    private int totalExpenseAmount = 0;

    public void add(String category, int expenseAmount) {
        totalExpenseAmount += expenseAmount;
        expensesAmounts.add(new PieEntry(expenseAmount, category));
    }

    public PieDataSet getPieDataSet() {
        PieDataSet pieDataSet = new PieDataSet(expensesAmounts, "");
        pieDataSet.setSliceSpace(10);
        pieDataSet.setValueTextSize(20f);
        pieDataSet.setFormSize(20f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        return pieDataSet;
    }

    public int getTotalExpenseAmount() {
        return totalExpenseAmount;
    }
}

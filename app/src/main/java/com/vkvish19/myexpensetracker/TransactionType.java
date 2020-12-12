package com.vkvish19.myexpensetracker;

import androidx.annotation.NonNull;

public enum TransactionType {
    Income("income"),
    Expense("expense");

    private final String stringValue;

    TransactionType(String stringValue) {
        this.stringValue = stringValue;
    }


    @NonNull
    @Override
    public String toString() {
        return this.stringValue;
    }
}

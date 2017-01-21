package com.shivam.pillbox.extras;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shivam on 19/01/17.
 */

public class DeleteOldMedicinesTask extends AsyncTask<Context, Void, Void> {
    @Override
    protected Void doInBackground(Context... contexts) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String time = calendar.getTimeInMillis() + "";

        Cursor cursor = null;
        try {
            cursor = contexts[0].getContentResolver().query(MedicineProvider.Medicines
                            .CONTENT_URI,
                    new String[]{MedicineColumns._ID},
                    MedicineColumns.TIME_IN_MILLIS + " < ?",
                    new String[]{time},
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor == null)
            return null;

        ContentProviderOperation.Builder builder;
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ArrayList<String> idArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            idArrayList.add(cursor.getLong(0) + "");
        }

        if (idArrayList.size() == 0)
            return null;

        final int size = idArrayList.size();
        String idArgs = "( ";
        for (int i = 0; i < size - 1; i++)
            idArgs += idArrayList.get(i) + ", ";
        idArgs += idArrayList.get(size - 1) + " )";

        builder = ContentProviderOperation.newDelete(MedicineProvider.Medicines.CONTENT_URI);
        builder.withSelection(MedicineColumns._ID + " IN " + idArgs, null);

        batchOperations.add(builder.build());
        try {
            contexts[0].getContentResolver().applyBatch(MedicineProvider.AUTHORITY, batchOperations);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
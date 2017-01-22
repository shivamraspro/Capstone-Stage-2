package com.shivam.pillbox.tasks;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;
import com.shivam.pillbox.extras.Utility;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shivam on 19/01/17.
 */

public class DeleteOldMedicinesTask extends AsyncTask<Context, Void, Void> {
    @Override
    protected Void doInBackground(Context... contexts) {

        Log.e("xxx", "Delete Old Medicines Start");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String time = calendar.getTimeInMillis() + "";

        Cursor cursor = null;
        try {
            cursor = contexts[0].getContentResolver().query(MedicineProvider.Medicines
                            .CONTENT_URI,
                    new String[]{MedicineColumns._ID, MedicineColumns.DAY_FREQUENCY},
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

        //code to update the frequency
        builder = ContentProviderOperation.newUpdate(MedicineProvider.Medicines.CONTENT_URI);
        while(cursor.moveToNext()) {
            if(cursor.getInt(1) > 1)
                builder.withValue(MedicineColumns.DAY_FREQUENCY, cursor.getInt(1) - 1);
        }

        try {
            batchOperations.add(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //code to delete the old alarms
        //resetting the cursor
        if(cursor.moveToFirst())
          idArrayList.add(cursor.getLong(0) + "");
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

        Utility.updateWidgets(contexts[0]);

        try {
            contexts[0].getContentResolver().applyBatch(MedicineProvider.AUTHORITY, batchOperations);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("xxx", "Delete Old Medicines Successful");

        return null;
    }

}
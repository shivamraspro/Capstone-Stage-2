package com.shivam.pillbox.tasks;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;
import com.shivam.pillbox.ui.DetailsActivity;

/**
 * Created by shivam on 22/01/17.
 */
public class ShowNotification extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("xxx", "Show Notification Initiate");

        int id = intent.getIntExtra("_id", -1);
        Log.d("xxx", "id = " +  id);
        Cursor cursor = context.getContentResolver().query(
                MedicineProvider.Medicines.withId(id),
                MedicineColumns.ALL_COLUMNS,
//                MedicineColumns._ID + " = " + id,
                null,
                null,
                null
        );

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Take medicine")
//                .setContentText(cursor.getString(MedicineColumns.NAME_INDEX));
//
//        // Creates an explicit resultIntent for an Activity in your app
//        Intent resultIntent = new Intent(context, DetailsActivity.class);
//        resultIntent.putExtra("medId", cursor.getString(MedicineColumns
//                .MEDICINE_ID_INDEX));
//        resultIntent.putExtra("medName", cursor.getString(MedicineColumns.NAME_INDEX));
//        resultIntent.putExtra("medFreq", cursor.getInt(MedicineColumns
//                .DAY_FREQUENCY_INDEX));
//        resultIntent.putExtra("medFoodMessage", cursor.getString(MedicineColumns
//                .MESSAGE_FOOD_INDEX));
//        resultIntent.putExtra("medFreeMessage", cursor.getString(MedicineColumns
//                .MESSAGE_FREE_INDEX));
//        resultIntent.putExtra("medColor", cursor.getInt(MedicineColumns.COLOR_INDEX));
//
//        // The stack builder object will contain an artificial back stack for the
//        // started Activity.
//        // This ensures that navigating backward from the Activity leads out of
//        // your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(DetailsActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        builder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        // mId allows you to update the notification later on.
//        mNotificationManager.notify(id, builder.build());

        try {
            boolean b = cursor.moveToFirst();
            int c = cursor.getColumnCount();
            // Creates an explicit resultIntent for an Activity in your app
            Intent resultIntent = new Intent(context, DetailsActivity.class);
            resultIntent.putExtra("medId", cursor.getString(MedicineColumns
                    .MEDICINE_ID_INDEX));
            resultIntent.putExtra("medName", cursor.getString(MedicineColumns.NAME_INDEX));
            resultIntent.putExtra("medFreq", cursor.getInt(MedicineColumns
                    .DAY_FREQUENCY_INDEX));
            resultIntent.putExtra("medFoodMessage", cursor.getString(MedicineColumns
                    .MESSAGE_FOOD_INDEX));
            resultIntent.putExtra("medFreeMessage", cursor.getString(MedicineColumns
                    .MESSAGE_FREE_INDEX));
            resultIntent.putExtra("medColor", cursor.getInt(MedicineColumns.COLOR_INDEX));
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }


        Log.e("xxx", "Show Notification Success");

       // new DeleteOldMedicinesTask().execute(context);


    }
}

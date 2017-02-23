package com.shivam.pillbox.alarms;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.ui.DetailsActivity;

/**
 * Created by shivam on 23/02/17.
 */

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;

    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, long alarmTime, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        //This acts as a differentiator for different alarm times instances of the same medicine
        //TODO : If i set multiple alarms for same medicines, only the last one gets set.
        //Fix this
        action.putExtra("medicine_time", alarmTime);
        return PendingIntent.getService(context, 0, action, 0);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        //Display a notification to view the task details
        Intent action = new Intent(this, DetailsActivity.class);
        action.setData(uri);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Grab the task description
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String title = "";
        String descString = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                title = cursor.getString(MedicineColumns.NAME_INDEX).toUpperCase();
                descString = generateDescString(getApplicationContext(), cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(descString)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(operation)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);
    }

    private String generateDescString(Context context, Cursor cursor) {
        cursor.moveToFirst();

        String descString = "";
        float dose = cursor.getFloat(MedicineColumns.DOSE_INDEX);
        if (((int) (dose * 100)) % 100 == 0)
            descString = context.getString(R.string.selectedDoseStringInt, (int) dose);
        else
            descString = context.getString(R.string.selectedDoseString, dose);
        descString += " " + cursor.getString(MedicineColumns.MESSAGE_FOOD_INDEX);


        String freeMsg = cursor.getString(MedicineColumns.MESSAGE_FREE_INDEX);
        if (!freeMsg.equals(""))
            descString += ". " + freeMsg;

        return descString;
    }
}
package com.shivam.pillbox.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

/**
 * Created by shivam on 23/02/17.
 */

public class AlarmScheduler {
    public static void scheduleAlarm(Context context, long alarmTime, Uri uri) {
        //Schedule the alarm. Will update an existing item for the same task.
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ReminderAlarmService.getReminderPendingIntent(context, alarmTime, uri);

        //

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC, alarmTime, operation);
        } else
            manager.set(AlarmManager.RTC, alarmTime, operation);
    }
}

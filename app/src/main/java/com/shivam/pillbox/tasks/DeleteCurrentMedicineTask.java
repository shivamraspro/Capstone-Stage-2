package com.shivam.pillbox.tasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shivam.pillbox.R;
import com.shivam.pillbox.alarms.ReminderAlarmService;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;
import com.shivam.pillbox.extras.ContextAndId;
import com.shivam.pillbox.extras.Utility;

import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by shivam on 20/01/17.
 */

public class DeleteCurrentMedicineTask extends AsyncTask<ContextAndId, Void, Context> {

    @Override
    protected Context doInBackground(ContextAndId... contextAndIds) {

        Context context = contextAndIds[0].getContext();
        String medId = contextAndIds[0].getMedId();

        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();

        Uri deleteUri = MedicineProvider.Medicines.withId(medId);

        ContentProviderOperation.Builder builder = ContentProviderOperation.newDelete(deleteUri);
        //TODO : update this with the new Inexact Content Uri
     //   builder.withSelection(MedicineColumns.MEDICINE_ID + " = \"" + medId + "\"", null);

        batchOperations.add(builder.build());

        Cursor cursor = context.getContentResolver().query(
                deleteUri,
                MedicineColumns.ALL_COLUMNS,
                null, null, null
        );

        for(int i = 0; cursor.moveToPosition(i); i++) {
            long medicineTime = cursor.getLong(MedicineColumns.TIME_IN_MILLIS_INDEX);

            //TODO : write code to delete all instances of alarmsq
            //Cancel any reminders that might be set for this item
            PendingIntent operation =
                    ReminderAlarmService.getReminderPendingIntent(context, medicineTime ,deleteUri);
            AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            manager.cancel(operation);
        }

        try {
            context.getContentResolver().applyBatch(MedicineProvider.AUTHORITY, batchOperations);
        } catch (Exception e) {
            e.printStackTrace();
        }



        Utility.updateWidgets(context);

        return context;
    }

    @Override
    protected void onPostExecute(Context context) {
        if (context != null)
            Toast.makeText(context, context.getString(R.string.delete_medicine_toast), Toast
                    .LENGTH_SHORT).show();
    }

}

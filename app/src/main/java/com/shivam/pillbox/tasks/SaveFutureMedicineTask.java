package com.shivam.pillbox.tasks;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.AsyncTask;
import android.os.RemoteException;

import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;
import com.shivam.pillbox.extras.MedicineProperties;
import com.shivam.pillbox.extras.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by shivam on 25/01/17.
 */

public class SaveFutureMedicineTask extends AsyncTask<MedicineProperties, Void, Void> {

    private Context mContext;

    @Override
    protected Void doInBackground(MedicineProperties... medicineProperties) {

        int medicinePeriod = medicineProperties[0].getMedicinePeriod();

        if (medicinePeriod == 1)
            return null;

        mContext = medicineProperties[0].getContext();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        int nowMins = calendar.get(Calendar.MINUTE);

        Random r = new Random();

        int hourOfDay;
        int mins;
        float dose;
        ContentProviderOperation.Builder builder;
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();

        String medicineId = medicineProperties[0].getMedID();

        for (int i = 2; i <= medicinePeriod; i++) {

            for (int key : medicineProperties[0].getMedicineTimes().keySet()) {

                calendar.setTimeInMillis(System.currentTimeMillis());

                builder = ContentProviderOperation.newInsert(MedicineProvider.Medicines.CONTENT_URI);

                int _id = r.nextInt((int) (calendar.getTimeInMillis() % 9999991) - 1) + 1;
                builder.withValue(MedicineColumns._ID, _id);

                hourOfDay = medicineProperties[0].getMedicineTimes().get(key).getHourOfDay();
                mins = medicineProperties[0].getMedicineTimes().get(key).getMins();
                dose = medicineProperties[0].getMedicineTimes().get(key).getDose();

                if (hourOfDay < nowHour || (hourOfDay == nowHour && mins < nowMins)) {
                    calendar.add(Calendar.DAY_OF_YEAR, i + 1);
                } else
                    calendar.add(Calendar.DAY_OF_YEAR, i);

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, mins);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                builder.withValue(MedicineColumns.MEDICINE_ID, medicineId);
                builder.withValue(MedicineColumns.HOUR_OF_DAY, hourOfDay);
                builder.withValue(MedicineColumns.MINUTES, mins);
                builder.withValue(MedicineColumns.TIME_IN_MILLIS, calendar.getTimeInMillis());
                builder.withValue(MedicineColumns.NAME, medicineProperties[0].getMedicineName());
                builder.withValue(MedicineColumns.DOSE, dose);
                builder.withValue(MedicineColumns.MESSAGE_FOOD, medicineProperties[0].getFoodMessage());
                builder.withValue(MedicineColumns.MESSAGE_FREE, medicineProperties[0].getFreeMessage());
                builder.withValue(MedicineColumns.SHAPE, medicineProperties[0].getShapeSelected());
                builder.withValue(MedicineColumns.COLOR, medicineProperties[0].getColorSelected());
                builder.withValue(MedicineColumns.DAY_FREQUENCY, medicineProperties[0]
                        .getMedicineReminderFrequency());

                batchOperations.add(builder.build());

                Utility.updateWidgets(mContext);
            }
        }

        try {
            medicineProperties[0].getContext().getContentResolver().applyBatch(MedicineProvider
                    .AUTHORITY, batchOperations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

        return null;
    }
}

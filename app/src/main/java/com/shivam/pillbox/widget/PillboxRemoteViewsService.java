package com.shivam.pillbox.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;
import com.shivam.pillbox.extras.Utility;

import java.util.Calendar;

public class PillBoxRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        return new RemoteViewsFactory() {
            private String midnightTime;
            private String timeString;
            private String ampmString;
            private String minutes;
            private int hour;
            private int mins;
            private float dose;
            private int colorIndex;
            private int shapeIndex;
            private String descString;

            Cursor cursor = null;

            @Override
            public void onCreate() {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.set(Calendar.HOUR_OF_DAY, 23);
//                calendar.set(Calendar.MINUTE, 59);
//                calendar.set(Calendar.SECOND, 59);
//                midnightTime = calendar.getTimeInMillis() + "";

//                cursor = getContentResolver().query(
//                        QuoteProvider.Quotes.CONTENT_URI,
//                        QUOTE_COLUMNS,
//                        null,
//                        null,
//                        QuoteColumns._ID + " desc"
//                );

            }

            @Override
            public void onDataSetChanged() {
                if(cursor != null)
                    cursor.close();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                midnightTime = calendar.getTimeInMillis() + "";

                cursor = getContentResolver().query(
                        MedicineProvider.Medicines.CONTENT_URI,
                        MedicineColumns.ALL_COLUMNS,
                        MedicineColumns.TIME_IN_MILLIS + " <= ?",
                        new String[]{midnightTime},
                        null
                );
            }

            @Override
            public void onDestroy() {
                 if(cursor != null) {
                     cursor.close();
                     cursor = null;
                 }
            }

            @Override
            public int getCount() {
                return cursor == null ? 0 : cursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if (position == AdapterView.INVALID_POSITION ||
                        cursor == null || !cursor.moveToPosition(position)) {
                    return null;
                }

                RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget_list_item);

                colorIndex = cursor.getInt(MedicineColumns.COLOR_INDEX);
                shapeIndex = cursor.getInt(MedicineColumns.SHAPE_INDEX);

                generateTimeString();
                generateDescString();

                view.setTextViewText(R.id.med_time_widget, timeString);
                view.setTextViewText(R.id.med_am_pm_widget, ampmString);
                view.setTextViewText(R.id.med_desc_widget, descString);
                view.setTextViewText(R.id.med_name_widget, cursor.getString(MedicineColumns.NAME_INDEX));

                view.setInt(R.id.medicine_info_widget, "setBackground", Utility
                        .getColorBackground(colorIndex));
                view.setInt(R.id.med_icon_widget, "setImageDrawable", Utility.getPillDrawable
                        (shapeIndex, colorIndex));
                view.setInt(R.id.med_name_widget, "setTextColor", Utility.getColorText
                        (colorIndex));

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra("medId", cursor.getString(MedicineColumns
                        .MEDICINE_ID_INDEX));
                fillInIntent.putExtra("medName", cursor.getString(MedicineColumns.NAME_INDEX));
                fillInIntent.putExtra("medFreq", cursor.getInt(MedicineColumns
                        .DAY_FREQUENCY_INDEX));
                fillInIntent.putExtra("medFoodMessage", cursor.getString(MedicineColumns
                        .MESSAGE_FOOD_INDEX));
                fillInIntent.putExtra("medFreeMessage", cursor.getString(MedicineColumns
                        .MESSAGE_FREE_INDEX));
                fillInIntent.putExtra("medColor", colorIndex);

                view.setOnClickFillInIntent(R.id.medicine_info_widget, fillInIntent);

                return view;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            private void generateTimeString() {

                hour = cursor.getInt(MedicineColumns.HOUR_OF_DAY_INDEX);
                mins = cursor.getInt(MedicineColumns.MINUTES_INDEX);

                if (mins < 10)
                    minutes = "0" + mins;
                else
                    minutes = "" + mins;

                if (hour < 12) {
                    ampmString = "AM";
                } else {
                    if (hour != 12)
                        hour -= 12;
                    ampmString = "PM";
                }

                if (hour < 10)
                    timeString = " " + hour + ":" + minutes + " ";
                else
                    timeString = hour + ":" + minutes;
            }

            private void generateDescString() {

                dose = cursor.getFloat(MedicineColumns.DOSE_INDEX);
                if (((int) (dose * 100)) % 100 == 0)
                    descString = getApplicationContext().getString(R.string.selectedDoseStringInt, (int) dose);
                else
                    descString = getApplicationContext().getString(R.string.selectedDoseString, dose);
                descString += " " + cursor.getString(MedicineColumns.MESSAGE_FOOD_INDEX);


                String freeMsg = cursor.getString(MedicineColumns.MESSAGE_FREE_INDEX);
                if(!freeMsg.equals(""))
                    descString += ". " + freeMsg;
            }

        };
    }

}

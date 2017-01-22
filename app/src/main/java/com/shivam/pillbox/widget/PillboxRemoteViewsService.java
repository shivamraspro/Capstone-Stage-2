package com.shivam.pillbox.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;

import java.util.Calendar;

public class PillBoxRemoteViewsService extends RemoteViewsService {

    int[] bgColors = {R.color.red_50,R.color.purple_50, R.color.indigo_50, R.color.lightblue_50,
            R.color.green_50, R.color.yellow_50, R.color.deeporange_50, R.color.brown_50};

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
                int count = cursor.getCount();
                return cursor == null ? 0 : count;
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

//                view.setInt(R.id.medicine_info_widget, "setBackground", bgColors[colorIndex]);
//                view.setInt(R.id.med_icon_widget, "setImageDrawable", getPillDrawable
//                        (shapeIndex, colorIndex));
//                view.setInt(R.id.med_name_widget, "setTextColor", getColorText
//                        (colorIndex));

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

    public int getColorBackground(int c) {
        switch (c) {
            case 0:
                return R.color.red_50;
            case 1:
                return R.color.purple_50;
            case 2:
                return R.color.indigo_50;
            case 3:
                return R.color.lightblue_50;
            case 4:
                return R.color.green_50;
            case 5:
                return R.color.yellow_50;
            case 6:
                return R.color.deeporange_50;
            case 7:
                return R.color.brown_50;
        }
        return -1;
    }

    public int getColorText(int c) {
        switch (c) {
            case 0:
                return R.color.red_700;
            case 1:
                return R.color.purple_700;
            case 2:
                return R.color.indigo_700;
            case 3:
                return R.color.lightblue_700;
            case 4:
                return R.color.green_700;
            case 5:
                return R.color.yellow_800;
            case 6:
                return R.color.deeporange_700;
            case 7:
                return R.color.brown_700;
        }
        return -1;
    }


    public int getPillDrawable(int s, int c) {
        switch (s) {
            case 0:
                switch (c) {
                    case 0:
                        return R.drawable.ic_circle_red;
                    case 1:
                        return R.drawable.ic_circle_purple;
                    case 2:
                        return R.drawable.ic_circle_indigo;
                    case 3:
                        return R.drawable.ic_circle_lightblue;
                    case 4:
                        return R.drawable.ic_circle_green;
                    case 5:
                        return R.drawable.ic_circle_yellow;
                    case 6:
                        return R.drawable.ic_circle_deeporange;
                    case 7:
                        return R.drawable.ic_circle_brown;
                }
            case 1:
                switch (c) {
                    case 0:
                        return R.drawable.ic_rect_red;
                    case 1:
                        return R.drawable.ic_rect_purple;
                    case 2:
                        return R.drawable.ic_rect_indigo;
                    case 3:
                        return R.drawable.ic_rect_lightblue;
                    case 4:
                        return R.drawable.ic_rect_green;
                    case 5:
                        return R.drawable.ic_rect_yellow;
                    case 6:
                        return R.drawable.ic_rect_deeporange;
                    case 7:
                        return R.drawable.ic_rect_brown;
                }
        }
        return -1;
    }
}

package com.shivam.pillbox.recyclerViewHelpers;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.extras.Utility;

import java.util.Calendar;

/**
 * Created by shivam on 20/01/17.
 */

public class MedicineDetailsCursorAdapter extends CursorRecyclerViewAdapter<MedicineDetailsCursorAdapter
        .ViewHolder> {

    private static Context mContext;
    private String timeString;
    private String ampmString;
    private String minutes;
    private int hour;
    private int mins;
    private String descString;
    private int colorIndex;
    private int shapeIndex;
    private float dose;
    private String dayString;

    public MedicineDetailsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public MedicineDetailsCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_details_item, parent, false);
        MedicineDetailsCursorAdapter.ViewHolder vh = new MedicineDetailsCursorAdapter.ViewHolder
                (itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MedicineDetailsCursorAdapter.ViewHolder viewHolder, final Cursor
            cursor) {

        colorIndex = cursor.getInt(MedicineColumns.COLOR_INDEX);
        shapeIndex = cursor.getInt(MedicineColumns.SHAPE_INDEX);

        viewHolder.medIcon.setImageDrawable(mContext.getResources().getDrawable(
                Utility.getPillDrawable(shapeIndex, colorIndex)));

        generateTimeString(cursor);
        viewHolder.medTime.setText(timeString);
        viewHolder.medAMPM.setText(ampmString);

        generateDescString(cursor);
        viewHolder.medDesc.setText(descString);
        viewHolder.medDesc.setTextColor(mContext.getResources().getColor(Utility.getColorText
                (colorIndex)));

        generateDayString(cursor.getLong(MedicineColumns.TIME_IN_MILLIS_INDEX));
        viewHolder.medDay.setText(dayString);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView medIcon;
        public final TextView medTime;
        public final TextView medAMPM;
        public final TextView medDesc;
        public final TextView medDay;

        public ViewHolder(View itemView) {
            super(itemView);

            medIcon = (ImageView) itemView.findViewById(R.id.med_icon);
            medTime = (TextView) itemView.findViewById(R.id.med_time);
            medAMPM = (TextView) itemView.findViewById(R.id.med_am_pm);
            medDesc = (TextView) itemView.findViewById(R.id.med_desc);
            medDay = (TextView) itemView.findViewById(R.id.med_day);
        }
    }

    private void generateTimeString(Cursor cursor) {

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

    private void generateDescString(Cursor cursor) {

        dose = cursor.getFloat(MedicineColumns.DOSE_INDEX);
        if (((int) (dose * 100)) % 100 == 0)
            descString = mContext.getString(R.string.selectedDoseStringInt, (int) dose);
        else
            descString = mContext.getString(R.string.selectedDoseString, dose);

        descString += " " + cursor.getString(MedicineColumns.MESSAGE_FOOD_INDEX);
    }

    private void generateDayString(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        dayString = Utility.getMonthName(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar
                .DAY_OF_MONTH);
    }
}

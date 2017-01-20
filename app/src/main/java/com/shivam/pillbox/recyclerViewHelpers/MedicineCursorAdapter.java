package com.shivam.pillbox.recyclerViewHelpers;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.ui.AddMedicationActivity;

/**
 * Created by shivam on 19/01/17.
 */

public class MedicineCursorAdapter extends CursorRecyclerViewAdapter<MedicineCursorAdapter
        .ViewHolder> {

    private static Context mContext;
    private String timeString;
    private String ampmString;
    private String minutes;
    private int hour;
    private int mins;
    private String descString;
    private int colorIndex;
    private float dose;


    public MedicineCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {

        if(cursor.getInt(MedicineColumns.SHAPE_INDEX) == 0) {
            viewHolder.medIconRectangle.setVisibility(View.GONE);
            viewHolder.medIconCircle.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.medIconCircle.setVisibility(View.GONE);
            viewHolder.medIconRectangle.setVisibility(View.VISIBLE);
        }
        generateTimeString(cursor);
        viewHolder.medTime.setText(timeString);
        viewHolder.medAMPM.setText(ampmString);

        viewHolder.medName.setText(cursor.getString(MedicineColumns.NAME_INDEX));

        generateDescString(cursor);

        viewHolder.medDesc.setText(descString);

        colorIndex = cursor.getInt(MedicineColumns.COLOR_INDEX);

        viewHolder.medIconContainer.setBackground(mContext.getResources().getDrawable
                (AddMedicationActivity.colors[colorIndex]));

        viewHolder.medTimeContainer.setBackground(mContext.getResources().getDrawable
                (AddMedicationActivity.colors[colorIndex]));

        viewHolder.medDescContainer.setBackground(mContext.getResources().getDrawable
                (AddMedicationActivity.colors[colorIndex]));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout medIconContainer;
        public final ImageView medIconCircle;
        public final ImageView medIconRectangle;
        public final LinearLayout medTimeContainer;
        public final TextView medTime;
        public final TextView medAMPM;
        public final LinearLayout medDescContainer;
        public final TextView medName;
        public final TextView medDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            medIconContainer = (FrameLayout) itemView.findViewById(R.id.med_icon_container);
            medIconCircle = (ImageView) itemView.findViewById(R.id.med_icon_circle);
            medIconRectangle = (ImageView) itemView.findViewById(R.id.med_icon_rectangle);
            medTimeContainer = (LinearLayout) itemView.findViewById(R.id.med_time_container);
            medTime = (TextView) itemView.findViewById(R.id.med_time);
            medAMPM = (TextView) itemView.findViewById(R.id.med_am_pm);
            medDescContainer = (LinearLayout) itemView.findViewById(R.id.med_desc_container);
            medName = (TextView) itemView.findViewById(R.id.med_name);
            medDesc = (TextView) itemView.findViewById(R.id.med_desc);
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
        }
        else {
            if (hour != 12)
                hour -= 12;
            ampmString = "PM";
        }

        if(hour < 10)
            timeString = " " + hour + ":" + minutes;
        else
            timeString = hour + ":" + minutes;
    }

    private void generateDescString(Cursor cursor) {

        dose = cursor.getFloat(MedicineColumns.DOSE_INDEX);
        if  (((int) (dose * 100)) % 100 == 0)
            descString = mContext.getString(R.string.selectedDoseStringInt, (int)dose);
        else
            descString = mContext.getString(R.string.selectedDoseString, dose);

        descString += " " + cursor.getString(MedicineColumns.MESSAGE_FOOD_INDEX) + ". ";
        descString += cursor.getString(MedicineColumns.MESSAGE_FREE_INDEX);
    }

}

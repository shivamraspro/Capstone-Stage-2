package com.shivam.pillbox.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.shivam.pillbox.R;
import com.shivam.pillbox.extras.ColorPalletteAdapter;
import com.shivam.pillbox.extras.RecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
        MedicineTimePickerFragment.TimePickerAndDosageDialogListener {

    @BindView(R.id.medication_name_edittext)
    EditText medicationNameEditText;

    @BindView(R.id.done_button)
    Button button;

    @BindView(R.id.medication_reminder_times)
    CardView reminderTimesCardView;

    @BindView(R.id.reminder_times_switch)
    Switch reminderTimesSwitch;

    @BindView(R.id.no_reminders_text)
    TextView noRemindersText;

    @BindView(R.id.reminder_times_spinner)
    Spinner reminderTimesSpinner;

    @BindView(R.id.time_selector_1)
    LinearLayout timeSelector1;
    @BindView(R.id.time_selector_2)
    LinearLayout timeSelector2;
    @BindView(R.id.time_selector_3)
    LinearLayout timeSelector3;
    @BindView(R.id.time_selector_4)
    LinearLayout timeSelector4;
    @BindView(R.id.time_selector_5)
    LinearLayout timeSelector5;
    @BindView(R.id.time_selector_6)
    LinearLayout timeSelector6;
    @BindView(R.id.time_selector_7)
    LinearLayout timeSelector7;

    @BindView(R.id.selectedTime_1)
    TextView selectedTime1;
    @BindView(R.id.selectedTime_2)
    TextView selectedTime2;
    @BindView(R.id.selectedTime_3)
    TextView selectedTime3;
    @BindView(R.id.selectedTime_4)
    TextView selectedTime4;
    @BindView(R.id.selectedTime_5)
    TextView selectedTime5;
    @BindView(R.id.selectedTime_6)
    TextView selectedTime6;
    @BindView(R.id.selectedTime_7)
    TextView selectedTime7;

    @BindView(R.id.selectedDose_1)
    TextView selectedDose1;
    @BindView(R.id.selectedDose_2)
    TextView selectedDose2;
    @BindView(R.id.selectedDose_3)
    TextView selectedDose3;
    @BindView(R.id.selectedDose_4)
    TextView selectedDose4;
    @BindView(R.id.selectedDose_5)
    TextView selectedDose5;
    @BindView(R.id.selectedDose_6)
    TextView selectedDose6;
    @BindView(R.id.selectedDose_7)
    TextView selectedDose7;

    @BindView(R.id.medication_message_edittext)
    EditText freeMessageEditText;

    @BindView(R.id.shape_circle)
    ImageView shapeCircleImageView;

    @BindView(R.id.shape_rectangle)
    ImageView shapeRectangleImageView;

    @BindDrawable(R.drawable.scrim)
    Drawable scrim;

    @BindView(R.id.color_pallette)
    RecyclerView colorPalleteRV;

    private Context mContext;
    private int currentHour;
    private int currentMins;
    final Calendar calendar = Calendar.getInstance();
    public static FrameLayout oldView;

    private String medicineName = "";
    private boolean medicineNameValid = false;

    private boolean medicineReminder;
    private int medicineReminderFrequency;

    private ArrayList<LinearLayout> timeSelectors;

    private String foodMessage = "";
    private String freeMessage = "";

    //Shape of medicine (0 for circle, 1 for rectangle)
    private int shapeSelected = 0;

    //Color of medicine (0-7)
    private int colorSelected = 0;

    public static int[] colors = {R.color.red_500, R.color.purple_500, R.color.indigo_500,
            R.color.lightblue_500, R.color.green_500, R.color.yellow_500,
            R.color.deeporange_500, R.color.brown_500};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        ButterKnife.bind(this);

        mContext = this;

        timeSelectors = new ArrayList<>();
        timeSelectors.add(timeSelector1);
        timeSelectors.add(timeSelector2);
        timeSelectors.add(timeSelector3);
        timeSelectors.add(timeSelector4);
        timeSelectors.add(timeSelector5);
        timeSelectors.add(timeSelector6);
        timeSelectors.add(timeSelector7);
//
//        medicationNameEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(charSequence.length() != 0) {
//                    medicineNameValid = true;
//                }
//                else
//                    medicineNameValid = false;
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                medicineName = editable.toString();
//            }
//        });

        medicineReminderFrequency = 1;
        reminderTimesSpinner.setOnItemSelectedListener(this);

        reminderTimesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                medicineReminder = b;
                if (b) {
                    noRemindersText.setVisibility(View.GONE);
                    reminderTimesSpinner.setVisibility(View.VISIBLE);
                    for (int i = 0; i < medicineReminderFrequency; i++) {
                        timeSelectors.get(i).setVisibility(View.VISIBLE);
                    }
                } else {
                    reminderTimesSpinner.setVisibility(View.GONE);
                    for (int i = 0; i < medicineReminderFrequency; i++) {
                        timeSelectors.get(i).setVisibility(View.GONE);
                    }
                    noRemindersText.setVisibility(View.VISIBLE);
                    medicineReminderFrequency = 0;
                }
            }
        });

        calendar.setTimeInMillis(System.currentTimeMillis());
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMins = calendar.get(Calendar.MINUTE);

        String timeString = getTimeString(currentHour, currentMins);

        selectedTime1.setText(timeString);
        selectedTime2.setText(timeString);
        selectedTime3.setText(timeString);
        selectedTime4.setText(timeString);
        selectedTime5.setText(timeString);
        selectedTime6.setText(timeString);
        selectedTime7.setText(timeString);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        colorPalleteRV.setHasFixedSize(true);
        colorPalleteRV.setLayoutManager(new GridLayoutManager(mContext, 4));
        ColorPalletteAdapter adapter = new ColorPalletteAdapter(mContext, colors);
        colorPalleteRV.setAdapter(adapter);
        colorPalleteRV.addOnItemTouchListener(new RecyclerViewTouchListener(mContext,
                colorPalleteRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                colorSelected = position;
                if(oldView != null)
                    oldView.setBackground(null);
                FrameLayout f = (FrameLayout) view.findViewById(R.id.color_view_container);
                f.setBackground(scrim);
                oldView = f;
            }
        }));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        for (int i = 0; i < 7; i++) {
            timeSelectors.get(i).setVisibility(View.GONE);
        }
        medicineReminderFrequency = pos + 1;
        for (int i = 0; i < medicineReminderFrequency; i++) {
            timeSelectors.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void showMedicineTimePickerDialog(View view) {
        int id = view.getId();
        MedicineTimePickerFragment timePickerFragment = new MedicineTimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        timePickerFragment.setArguments(bundle);
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }

    //callback from the dialog
    @Override
    public void getTimeAndDosage(int hour, int mins, float dose, int id) {
        if (hour != -1 && mins != -1) {
            bindTimeAndDosage(hour, mins, dose, id);
        } else {
            bindTimeAndDosage(currentHour, currentMins, dose, id);
        }
    }

    //display the time and dosage as selected from the dialog
    private void bindTimeAndDosage(int hour, int mins, float dose, int id) {
        String timeString = getTimeString(hour, mins);

        String doseString;

        if (((int) (dose * 100)) % 100 == 0)
            doseString = getString(R.string.selectedDoseStringInt, (int) dose);
        else
            doseString = getString(R.string.selectedDoseString, dose);

        switch (id) {
            case R.id.time_selector_1:
                selectedTime1.setText(timeString);
                selectedDose1.setText(doseString);
                break;
            case R.id.time_selector_2:
                selectedTime2.setText(timeString);
                selectedDose2.setText(doseString);
                break;
            case R.id.time_selector_3:
                selectedTime3.setText(timeString);
                selectedDose3.setText(doseString);
                break;
            case R.id.time_selector_4:
                selectedTime4.setText(timeString);
                selectedDose4.setText(doseString);
                break;
            case R.id.time_selector_5:
                selectedTime5.setText(timeString);
                selectedDose5.setText(doseString);
                break;
            case R.id.time_selector_6:
                selectedTime6.setText(timeString);
                selectedDose6.setText(doseString);
                break;
            case R.id.time_selector_7:
                selectedTime7.setText(timeString);
                selectedDose7.setText(doseString);
                break;
        }
    }

//    private String getTimeString() {
//
//        //0 for AM and 1 for PM
//        int am_pm = c.get(Calendar.AM_PM);
//
//        String currentTime = "";
//        if(currentHour < 10)
//            currentTime = "0" + currentHour + ":" + currentMins;
//        else
//            currentTime = currentHour + ": ";
//
//        if(am_pm == 0)
//            currentTime += " AM";
//        else
//            currentTime += " PM";
//
//        return currentTime;
//    }

    //get time in string format to be displayed on ReminderTimes Cardview
    private String getTimeString(int hour, int mins) {

        String currentTime = "";

        String minutes;

        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = "" + mins;

        if (hour < 12)
            currentTime = hour + ":" + minutes + " AM";
        else {
            hour -= 12;
            currentTime = hour + ":" + minutes + " PM";
        }

        return currentTime;
    }

    public void foodMessageChanged(View view) {
        switch (view.getId()) {
            case R.id.before_food:
                foodMessage = getString(R.string.before_food_instruction);
                break;
            case R.id.after_food:
                foodMessage = getString(R.string.after_food_instruction);
                break;
            case R.id.with_food:
                foodMessage = getString(R.string.with_food_instruction);
                break;
            case R.id.no_food_instructions:
                foodMessage = "";
                break;
        }
    }

    public void selectedShapeChanged(View view) {
        switch (view.getId()) {
            case R.id.shape_circle :
                shapeSelected = 0;
                view.setBackground(scrim);
                shapeRectangleImageView.setBackground(null);
            break;

            case R.id.shape_rectangle:
                shapeSelected = 1;
                view.setBackground(scrim);
                shapeCircleImageView.setBackground(null);
            break;
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }


//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setMessage("Are you sure you want to exit?")
//                .setCancelable(true)
//                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        AddMedicationActivity.this.finish();
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .show();
//    }
}

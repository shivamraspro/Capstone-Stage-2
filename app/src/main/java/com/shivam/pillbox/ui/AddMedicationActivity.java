package com.shivam.pillbox.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shivam.pillbox.R;
import com.shivam.pillbox.extras.MedicineProperties;
import com.shivam.pillbox.extras.MedicineTime;
import com.shivam.pillbox.recyclerViewHelpers.ColorPalletteAdapter;
import com.shivam.pillbox.recyclerViewHelpers.RecyclerViewClickListener;
import com.shivam.pillbox.tasks.SaveMedicineTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
        MedicineTimePickerFragment.TimePickerAndDosageDialogListener {

    @BindView(R.id.add_medication_coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.medication_name_edittext)
    EditText medicationNameEditText;

    @BindView(R.id.done_button)
    Button button;

    @BindView(R.id.medication_reminder_times)
    CardView reminderTimesCardView;

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
    private int[] timeSelectorId = {R.id.time_selector_1, R.id.time_selector_2, R.id
            .time_selector_3, R.id.time_selector_4, R.id.time_selector_5, R.id.time_selector_6, R
            .id.time_selector_7};

    private String medicineName = "";

    private int medicineReminderFrequency;

    private ArrayList<LinearLayout> timeSelectors;

    private String foodMessage = "";
    private String freeMessage = "";

    //Shape of medicine (0 for circle, 1 for rectangle)
    private int shapeSelected = 0;

    //Color of medicine (0-7)
    private int colorSelected = 0;

    private HashMap<Integer, MedicineTime> medicineTimes;

    private FirebaseAnalytics mFirebaseAnalytics;

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

        medicineReminderFrequency = 1;
        reminderTimesSpinner.setOnItemSelectedListener(this);

        calendar.setTimeInMillis(System.currentTimeMillis());
        bindLatestTimeOnTimeSelectorViews();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        colorPalleteRV.setHasFixedSize(true);
        colorPalleteRV.setLayoutManager(new GridLayoutManager(mContext, 4));
        ColorPalletteAdapter adapter = new ColorPalletteAdapter(mContext);
        colorPalleteRV.setAdapter(adapter);
        colorPalleteRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, new
                RecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        colorSelected = position;
                        if (oldView != null)
                            oldView.setBackground(null);
                        FrameLayout f = (FrameLayout) v.findViewById(R.id.color_view_container);
                        f.setBackground(scrim);
                        oldView = f;
                    }
                }) {
        });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        for (int i = 0; i < 7; i++) {
            timeSelectors.get(i).setVisibility(View.GONE);
        }
        medicineTimes = new HashMap<>();
        medicineReminderFrequency = pos + 1;
        for (int i = 0; i < medicineReminderFrequency; i++) {
            timeSelectors.get(i).setVisibility(View.VISIBLE);
            medicineTimes.put(timeSelectorId[i], new MedicineTime(currentHour, currentMins, 1.00f));
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
        bundle.putFloat("doseQty", medicineTimes.get(id).getDose());
        timePickerFragment.setArguments(bundle);
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }

    //callback from the dialog
    @Override
    public void getTimeAndDosage(int hour, int mins, float dose, int id) {
        if (hour != -1 && mins != -1) {
            medicineTimes.put(id, new MedicineTime(hour, mins, dose));
            bindTimeAndDosage(hour, mins, dose, id);
        } else {
            hour = medicineTimes.get(id).getHourOfDay();
            mins = medicineTimes.get(id).getMins();
            medicineTimes.put(id, new MedicineTime(hour, mins, dose));
            Log.d("xxx", medicineTimes.get(id).getHourOfDay() + " : " + medicineTimes.get(id).getMins() +
                    "    " +
                    medicineTimes.get(id).getDose());
            bindTimeAndDosage(hour, mins, dose, id);
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
            if (hour != 12)
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
            case R.id.shape_circle:
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

    public void saveMedication(View view) {
        medicineName = medicationNameEditText.getText().toString();
        freeMessage = freeMessageEditText.getText().toString();

        if (medicineName.equals("")) {
            Snackbar.make(coordinatorLayout, getString(R.string
                    .alert_dialog_medication_name_invalid_msg), Snackbar.LENGTH_SHORT).show();

            return;
        }

        new SaveMedicineTask().execute(new MedicineProperties(
                mContext,
                medicineTimes,
                medicineName,
                medicineReminderFrequency,
                foodMessage,
                freeMessage,
                shapeSelected,
                colorSelected
        ));

        Bundle bundle = new Bundle();

        bundle.putInt("day_frequency", medicineReminderFrequency);
        bundle.putInt("shape", shapeSelected);
        bundle.putInt("color", colorSelected);

        mFirebaseAnalytics.logEvent("add_medicine", bundle);

        AddMedicationActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        createClosingDialog(null);
    }

    public void createClosingDialog(View view) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.add_medication_quit_dialog_text))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.dialog_add_medication_quit_positive_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AddMedicationActivity.this.finish();
                            }
                        })
                .setNegativeButton(getString(R.string.dialog_time_picker_negative_button), null)
                .show();
    }

    private void bindLatestTimeOnTimeSelectorViews() {
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
    }
}

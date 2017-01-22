package com.shivam.pillbox.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shivam.pillbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shivam on 17/01/17.
 */

public class MedicineTimePickerFragment extends DialogFragment implements TimePicker.OnTimeChangedListener {

    @BindView(R.id.simple_time_picker)
    TimePicker timePicker;

    @BindView(R.id.minus)
    ImageButton minusButton;

    @BindView(R.id.plus)
    ImageButton plusButton;

    @BindView(R.id.dose_quantity)
    EditText doseQuantity;

    @BindView(R.id.negative_button)
    TextView negativeButton;

    @BindView(R.id.positive_button)
    TextView positiveButton;

    private Float doseQty = 1.00f;
    private int hour;
    private int mins;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_time_picker, container);
        ButterKnife.bind(this, view);

        timePicker.setOnTimeChangedListener(this);

        id = getArguments().getInt("id");
        doseQty = getArguments().getFloat("doseQty");

        doseQuantity.setText(doseQty + "");

        hour = -1;
        mins = -1;

        return view;
    }

    @OnClick({R.id.positive_button, R.id.negative_button})
    public void dialogButtons(View view) {

        switch (view.getId()) {
            case R.id.positive_button:
                ((TimePickerAndDosageDialogListener)getActivity()).getTimeAndDosage(
                        //hourOfDay
                        hour,
                        mins,
                        Float.parseFloat(doseQuantity.getText().toString()),
                        id
                );
                break;
            case R.id.negative_button:
                doseQty = getArguments().getFloat("doseQty");
                //TODO error handling
                ((TimePickerAndDosageDialogListener)getActivity()).getTimeAndDosage(
                        -1,
                        -1,
                        doseQty,
                        id
                );
                break;
        }

        this.dismiss();
    }


    @OnClick({R.id.minus, R.id.plus})
    public void changeQuantity(View view) {

        doseQty = Float.parseFloat(doseQuantity.getText().toString());

        doseQty = ((int)(doseQty*100))/100.0f;

        switch (view.getId()) {
            case R.id.plus:
                doseQty = doseQty + 0.25f;
                break;
            case R.id.minus:
                if (doseQty >= 0.50f) {
                    doseQty = doseQty - 0.25f;
                }
                break;
        }
        doseQuantity.setText(doseQty + "");
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minutes) {
        //hourOfDay
        hour = hourOfDay;
        mins = minutes;
    }

    public interface TimePickerAndDosageDialogListener{
        void getTimeAndDosage(int hour, int mins, float dose, int id);
    }

}

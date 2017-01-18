package com.shivam.pillbox.extras;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by shivam on 18/01/17.
 */

public class MedicineProperties {
    private HashMap<Integer, MedicineTime> medicineTimes;
    private String medicineName;
    //todo not needed
    private int medicineReminderFrequency;
    private String foodMessage;
    private String freeMessage;
    private int shapeSelected;
    private int colorSelected;
    private Context context;

    public MedicineProperties(Context context, HashMap<Integer, MedicineTime> medicineTimes, String
            medicineName, int medicineReminderFrequency, String foodMessage, String freeMessage,
                              int shapeSelected, int colorSelected) {
        this.context = context;
        this.medicineTimes = medicineTimes;
        this.medicineName = medicineName;
        this.medicineReminderFrequency = medicineReminderFrequency;
        this.foodMessage = foodMessage;
        this.freeMessage = freeMessage;
        this.shapeSelected = shapeSelected;
        this.colorSelected = colorSelected;
    }


    public HashMap<Integer, MedicineTime> getMedicineTimes() {
        return medicineTimes;
    }

    public String getMedicineName() {
        return medicineName;
    }

    //todo not needed
    public int getMedicineReminderFrequency() {
        return medicineReminderFrequency;
    }

    public String getFoodMessage() {
        return foodMessage;
    }

    public String getFreeMessage() {
        return freeMessage;
    }

    public int getShapeSelected() {
        return shapeSelected;
    }

    public int getColorSelected() {
        return colorSelected;
    }

    public Context getContext() {
        return context;
    }
}

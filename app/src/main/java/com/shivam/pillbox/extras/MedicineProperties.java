package com.shivam.pillbox.extras;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by shivam on 18/01/17.
 */

public class MedicineProperties {
    private HashMap<Integer, MedicineTime> medicineTimes;
    private String medicineName;
    private int medicineReminderFrequency;
    private String foodMessage;
    private String freeMessage;
    private int shapeSelected;
    private int colorSelected;
    private int medicinePeriod;
    private String medID;

    private Context context;

    public MedicineProperties(Context context, HashMap<Integer, MedicineTime> medicineTimes, String
            medicineName, int medicineReminderFrequency, String foodMessage, String freeMessage,
                              int shapeSelected, int colorSelected, int medicinePeriod) {
        this.context = context;
        this.medicineTimes = medicineTimes;
        this.medicineName = medicineName;
        this.medicineReminderFrequency = medicineReminderFrequency;
        this.foodMessage = foodMessage;
        this.freeMessage = freeMessage;
        this.shapeSelected = shapeSelected;
        this.colorSelected = colorSelected;
        this.medicinePeriod = medicinePeriod;
    }


    public HashMap<Integer, MedicineTime> getMedicineTimes() {
        return medicineTimes;
    }

    public String getMedicineName() {
        return medicineName;
    }

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

    public int getMedicinePeriod() {
        return medicinePeriod;
    }

    public String getMedID() {
        return medID;
    }

    public Context getContext() {
        return context;
    }


    public void setMedID(String medID) {
        this.medID = medID;
    }
}

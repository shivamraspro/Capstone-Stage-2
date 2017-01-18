package com.shivam.pillbox.extras;

/**
 * Created by shivam on 18/01/17.
 */

public class MedicineTime {
    private int hourOfDay;
    private int mins;
    private float dose;

    public MedicineTime() {
    }

    public MedicineTime(int h, int m, float d) {
        hourOfDay = h;
        mins= m;
        dose = d;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public int getMins() {
        return mins;
    }

    public float getDose() {
        return dose;
    }
}

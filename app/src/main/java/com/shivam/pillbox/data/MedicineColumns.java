package com.shivam.pillbox.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by shivam on 16/01/17.
 */

public class MedicineColumns {

    //Each alarm has a unique alarm ID
    //To make each alarm ID unique, it will be the current system time in Millis
    @DataType(DataType.Type.REAL)
    @PrimaryKey
    public static final String _ID = "_id";

    //Hour in 24-hour format (0 - 23)
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String HOUR = "hour";

    //Minutes (0-59)
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String MINUTES = "mins";

    //Alarm Date & Time in milliseconds
    //TODO consider using separate Day, Month, Year Field instead of this field
    @DataType(DataType.Type.REAL)
    @NotNull
    public static final String TIME_IN_MILLIS = "millis";

    //Name of the medication
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String NAME = "name";

    //Dosage for the medication
    @DataType(DataType.Type.REAL)
    @NotNull
    public static final String DOSE  = "dose";

    //Extra Message for the medication
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MESSAGE  = "message";

    //Boolean for if the reminders are needed for a particular medicine
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String REMINDER = "reminder";


    //Columns array for faster query buildup
    public static final String[] ALL_COLUMNS = {
            _ID,
            HOUR,
            MINUTES,
            TIME_IN_MILLIS,
            NAME,
            DOSE,
            MESSAGE,
            REMINDER
    };

    //Columns Indices
    public static final int ID_INDEX = 0;
    public static final int HOUR_INDEX = 1;
    public static final int MINUTES_INDEX = 2;
    public static final int TIME_IN_MILLIS_INDEX = 3;
    public static final int NAME_INDEX = 4;
    public static final int DOSE_INDEX = 5;
    public static final int MESSAGE_INDEX = 6;
    public static final int REMINDER_INDEX = 7;

}

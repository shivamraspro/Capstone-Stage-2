package com.shivam.pillbox.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by shivam on 16/01/17.
 */

public class MedicineColumns {

    //Each medicine alarm has a unique alarm ID
    //To make each medicine alarm ID unique, it will set using the current system time in Millis
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @Unique
    public static final String _ID = "_id";

    //Each medicine will have this particular id
    //It will be common across all alarm times
    //It will be a combination of medicine name and the current system time in millis
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MEDICINE_ID = "med_id";

    //Hour in 24-hour format (0 - 23)
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String HOUR_OF_DAY = "hour";

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
    public static final String DOSE = "dose";

    //Extra Free Text Message for the medication
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MESSAGE_FOOD = "food_message";

    //Extra Free Text Message for the medication
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MESSAGE_FREE = "free_message";

    //Shape of medicine (0 for circle, 1 for rectangle)
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String SHAPE = "shape";

    //Color of medicine (0-7)
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String COLOR = "color";

    //Number of times the medicine has to be taken in a day
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String DAY_FREQUENCY = "day_frequency";


    //Columns array for faster query buildup
    public static final String[] ALL_COLUMNS = {
            _ID,
            MEDICINE_ID,
            HOUR_OF_DAY,
            MINUTES,
            TIME_IN_MILLIS,
            NAME,
            DOSE,
            MESSAGE_FOOD,
            MESSAGE_FREE,
            SHAPE,
            COLOR,
            DAY_FREQUENCY
    };

    //Columns Indices
    public static final int ID_INDEX = 0;
    public static final int MEDICINE_ID_INDEX = 1;
    public static final int HOUR_OF_DAY_INDEX = 2;
    public static final int MINUTES_INDEX = 3;
    public static final int TIME_IN_MILLIS_INDEX = 4;
    public static final int NAME_INDEX = 5;
    public static final int DOSE_INDEX = 6;
    public static final int MESSAGE_FOOD_INDEX = 7;
    public static final int MESSAGE_FREE_INDEX = 8;
    public static final int SHAPE_INDEX = 9;
    public static final int COLOR_INDEX = 10;
    public static final int DAY_FREQUENCY_INDEX = 11;

}

package com.shivam.pillbox.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by shivam on 16/01/17.
 */

@Database(version = MedicineDatabase.VERSION)
public class MedicineDatabase {

    public static final int VERSION = 1;

    @Table(MedicineColumns.class)
    public static final String MEDICINES = "medicines";
}

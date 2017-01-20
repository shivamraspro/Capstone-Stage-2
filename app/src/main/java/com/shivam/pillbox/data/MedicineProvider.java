package com.shivam.pillbox.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by shivam on 16/01/17.
 */

@ContentProvider(authority = MedicineProvider.AUTHORITY, database = MedicineDatabase.class)
public class MedicineProvider {

    public static final String AUTHORITY = "com.shivam.pillbox.data.MedicineProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String MEDICINES = "medicines";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MedicineDatabase.MEDICINES)
    public static class Medicines {
        @ContentUri(
                path = Path.MEDICINES,
                type = "vnd.android.cursor.dir/quote",
                defaultSort = MedicineColumns.TIME_IN_MILLIS + " ASC"
        )
        public static final Uri CONTENT_URI = buildUri(Path.MEDICINES);

//        @InexactContentUri(
//                name = "QUOTE_SYMBOL",
//                path = Path.MEDICINES + "/*",
//                type = "vnd.android.cursor.item/medicines",
//                whereColumn = MedicineColumns._ID,
//                pathSegment = 1
//        )
//        public static Uri withId(Long ID) {
//            return buildUri(Path.MEDICINES, ID + "");
//        }

//        @ContentUri(
//                path = Path.MEDICINES,
//                type = "vnd.android.cursor.dir/quote",
//                where = MedicineColumns.TIME_IN_MILLIS + " < ?",
//
//        )
//        public static final Uri CONTENT_URI_ISNOTCURRENT = buildUri(Path.MEDICINES);
    }
}

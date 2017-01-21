package com.shivam.pillbox.extras;

import com.shivam.pillbox.R;

/**
 * Created by shivam on 20/01/17.
 */

public class Utility {

    public static int getColorPill(int c) {
        switch (c) {
            case 0 : return R.color.red_200;
            case 1 : return R.color.purple_200;
            case 2 : return R.color.indigo_200;
            case 3 : return R.color.lightblue_200;
            case 4 : return R.color.green_200;
            case 5 : return R.color.yellow_200;
            case 6 : return R.color.deeporange_200;
            case 7 : return R.color.brown_200;
        }
        return -1;
    }

    public static int getColorBackground(int c) {
        switch (c) {
            case 0 : return R.color.red_50;
            case 1 : return R.color.purple_50;
            case 2 : return R.color.indigo_50;
            case 3 : return R.color.lightblue_50;
            case 4 : return R.color.green_50;
            case 5 : return R.color.yellow_50;
            case 6 : return R.color.deeporange_50;
            case 7 : return R.color.brown_50;
        }
        return -1;
    }

    public static int getColorText(int c) {
        switch (c) {
            case 0 : return R.color.red_700;
            case 1 : return R.color.purple_700;
            case 2 : return R.color.indigo_700;
            case 3 : return R.color.lightblue_700;
            case 4 : return R.color.green_700;
            case 5 : return R.color.yellow_800;
            case 6 : return R.color.deeporange_700;
            case 7 : return R.color.brown_700;
        }
        return -1;
    }


    public static int getPillDrawable(int s, int c) {
        switch (s) {
            case 0 :
                switch (c) {
                    case 0 : return R.drawable.ic_circle_red;
                    case 1 : return R.drawable.ic_circle_purple;
                    case 2 : return R.drawable.ic_circle_indigo;
                    case 3 : return R.drawable.ic_circle_lightblue;
                    case 4 : return R.drawable.ic_circle_green;
                    case 5 : return R.drawable.ic_circle_yellow;
                    case 6 : return R.drawable.ic_circle_deeporange;
                    case 7 : return R.drawable.ic_circle_brown;
                }
            case 1 :
                switch (c) {
                    case 0 : return R.drawable.ic_rect_red;
                    case 1 : return R.drawable.ic_rect_purple;
                    case 2 : return R.drawable.ic_rect_indigo;
                    case 3 : return R.drawable.ic_rect_lightblue;
                    case 4 : return R.drawable.ic_rect_green;
                    case 5 : return R.drawable.ic_rect_yellow;
                    case 6 : return R.drawable.ic_rect_deeporange;
                    case 7 : return R.drawable.ic_rect_brown;
                }
        }
        return -1;
    }

    public static String getMonthName(int m) {
        switch (m) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
        }
        return null;
    }
}

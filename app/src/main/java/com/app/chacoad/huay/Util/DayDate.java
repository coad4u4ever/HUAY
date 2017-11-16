package com.app.chacoad.huay.Util;

import android.content.Context;
import android.text.TextUtils;

import com.app.chacoad.huay.R;

import java.util.Calendar;

/**
 * Created by Chacoad on 5/11/2560.
 */

public class DayDate {
    Calendar mCalendar = null;
    int mDay;
    int mDate;
    int mMonth;
    int mYear;
    int mHour;
    int mMin;
    int mSec;
    private Context context;

    public DayDate() {
        mCalendar = Calendar.getInstance();
        mDay = mCalendar.get(Calendar.DAY_OF_WEEK);
        mDate = mCalendar.get(Calendar.DAY_OF_MONTH);
        mMonth = mCalendar.get(Calendar.MONTH);
        mYear = mCalendar.get(Calendar.YEAR);
        mHour = mCalendar.get(Calendar.HOUR);
        mMin = mCalendar.get(Calendar.MINUTE);
        mSec = mCalendar.get(Calendar.SECOND);
    }

    public DayDate(Context context) {
        this.context = context;
        mCalendar = Calendar.getInstance();
        mDay = mCalendar.get(Calendar.DAY_OF_WEEK);
        mDate = mCalendar.get(Calendar.DAY_OF_MONTH);
        mMonth = mCalendar.get(Calendar.MONTH);
        mYear = mCalendar.get(Calendar.YEAR);
        mHour = mCalendar.get(Calendar.HOUR);
        mMin = mCalendar.get(Calendar.MINUTE);
        mSec = mCalendar.get(Calendar.SECOND);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getDayOfWeek() {
        String returnDay = null;

        switch (mDay) {
            case Calendar.SUNDAY:
                returnDay = context.getString(R.string.th_day_sunday);
                break;
            case Calendar.MONDAY:
                returnDay = context.getString(R.string.th_day_monday);
                break;
            case Calendar.TUESDAY:
                returnDay = context.getString(R.string.th_day_tuesday);
                break;
            case Calendar.WEDNESDAY:
                returnDay = context.getString(R.string.th_day_wednesday);
                break;
            case Calendar.THURSDAY:
                returnDay = context.getString(R.string.th_day_thursday);
                break;
            case Calendar.FRIDAY:
                returnDay = context.getString(R.string.th_day_friday);
                break;
            case Calendar.SATURDAY:
                returnDay = context.getString(R.string.th_day_saturday);
                break;
        }

        return returnDay;
    }

    public String getMonth(int month) {
        String returnMonth = null;

        switch (month) {
            case Calendar.JANUARY:
                returnMonth = context.getString(R.string.th_day_january);
                break;
            case Calendar.FEBRUARY:
                returnMonth = context.getString(R.string.th_day_february);
                break;
            case Calendar.MARCH:
                returnMonth = context.getString(R.string.th_day_march);
                break;
            case Calendar.APRIL:
                returnMonth = context.getString(R.string.th_day_april);
                break;
            case Calendar.MAY:
                returnMonth = context.getString(R.string.th_day_may);
                break;
            case Calendar.JUNE:
                returnMonth = context.getString(R.string.th_day_june);
                break;
            case Calendar.JULY:
                returnMonth = context.getString(R.string.th_day_july);
                break;
            case Calendar.AUGUST:
                returnMonth = context.getString(R.string.th_day_august);
                break;
            case Calendar.SEPTEMBER:
                returnMonth = context.getString(R.string.th_day_september);
                break;
            case Calendar.OCTOBER:
                returnMonth = context.getString(R.string.th_day_october);
                break;
            case Calendar.NOVEMBER:
                returnMonth = context.getString(R.string.th_day_november);
                break;
            case Calendar.DECEMBER:
                returnMonth = context.getString(R.string.th_day_december);
                break;
        }

        return returnMonth;
    }

    public String getDay() {
        return Integer.toString(mDate);
    }

    public String getYear() {
        return Integer.toString(mYear + 543);
    }

    public int getMonth() {
        return mMonth + 1;
    }


    public String getDateFullFormat() {
        String date = getDayOfWeek() + context.getString(R.string.th_day_at);
        String day = getDay();
        String month = getMonth(mMonth);
        String yearText = context.getString(R.string.th_day_year);
        String year = getYear();

        return formatDate(date, day, month, yearText, year);

    }

    public String getNextHuayDate() {
        String nextHuayDate;
        String nextHuayDay = null;
        String monthText = context.getString(R.string.th_day_month) + getMonth(mMonth);
        String year = getYear();
        if (mDate >= 2 && mDate <= 16) {
            nextHuayDay = "16";
        } else if (mDate == 1) {
            nextHuayDay = "1";
        } else if (mDate > 16) {
            nextHuayDay = "1";
            if (mMonth < 12) {
                monthText = context.getString(R.string.th_day_month) + getMonth(mMonth + 1);
            } else {
                monthText = context.getString(R.string.th_day_month) + getMonth(1);
                year = year + 1;
            }
        }
        nextHuayDate = formatDate(nextHuayDay, monthText, year);
        return nextHuayDate;
    }

    public String getNextHuayDateKey() {
        String nextHuayDate = "t";

        if (mDate >= 2 && mDate <= 16) {
            nextHuayDate += mYear;
            nextHuayDate += getMonth();
            nextHuayDate += "16";
        } else if (mDate == 1) {
            nextHuayDate += mYear;
            nextHuayDate += getMonth();
            nextHuayDate = "01";
        } else if (mDate > 16) {
            if (mMonth < 12) {
                nextHuayDate += mYear;
                nextHuayDate += (getMonth() + 1);

            } else {
                nextHuayDate += (mYear + 1);
                nextHuayDate += "01";
            }
            nextHuayDate += "01";
        }

        return nextHuayDate;
    }

    private String formatDate(String... strings) {
        return TextUtils.join(" ", strings);
    }

    public String getCurrentCycle() {
        String currentCycleDay = (mDay < 16) ? "01" : "16";
        String currnetCycleMonth = (mMonth + 1 < 10) ? "0".concat(String.valueOf(mMonth + 1)) : String.valueOf(mMonth + 1);
        String currentCycle = String.valueOf(mYear).concat(currnetCycleMonth).concat(currentCycleDay);
        return currentCycle;
    }
}
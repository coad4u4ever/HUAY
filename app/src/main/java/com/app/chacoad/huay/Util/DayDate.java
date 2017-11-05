package com.app.chacoad.huay.Util;

import android.content.Context;

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

    public String getDateFullFormat() {
        return getDayOfWeek() +
                context.getString(R.string.th_day_at) +
                " " +
                getDay() +
                " " +
                getMonth(mMonth) +
                " " +
                context.getString(R.string.th_day_year) +
                " " +
                getYear()
                ;
    }

    public String getNextHuayDate() {
        String nextHuayDate = null;
        if (mDate >= 2 && mDate <= 16) {
            nextHuayDate = "16" +
                    " " +
                    context.getString(R.string.th_day_month) +
                    getMonth(mMonth) +
                    " " +
                    getYear();
        } else if (mDate == 1) {
            nextHuayDate = "1" +
                    " " +
                    context.getString(R.string.th_day_month) +
                    getMonth(mMonth) +
                    " " +
                    getYear();
        } else if (mDate > 16) {
            nextHuayDate = Integer.toString(1) +
                    " " +
                    context.getString(R.string.th_day_month);

            if (mMonth >= 1 && mMonth <= 11) {
                nextHuayDate += nextHuayDate +
                        getMonth(mMonth + 1) +
                        " " +
                        getYear();
            } else if (mMonth == 12) {
                nextHuayDate += nextHuayDate +
                        getMonth(1) +
                        " " +
                        getYear() + 1;
            }

        }

        return nextHuayDate;
    }
}

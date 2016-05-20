package br.com.managersystems.guardasaude.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class that handles and/or returns Date objects.
 * Currently handles:
 * - Adding days to a date.
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 */
public class DateUtil
{
    /**
     * Adds days.
     * @param date Date object that represents the date before adding days.
     * @param days int object that represents the amount of days to be added.
     * @return Date object that represents the new date.
     */
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


}
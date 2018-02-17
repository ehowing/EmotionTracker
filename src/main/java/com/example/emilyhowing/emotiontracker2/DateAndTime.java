package com.example.emilyhowing.emotiontracker2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by emilyhowing on 5/1/17.
 */

public class DateAndTime {

    /**
     * gets the current date
     * @return string of current date
     */
    public static String getCurrentDate(){

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(currentDate);
        return date;
    }

    /**
     * gets the current time
     * @return string of current time in 12 hour format with am/pm
     */
    public static String getCurrentTime(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String time = timeFormat.format(calendar.getTime());
        return time;
    }

    /**
     * checks to see if the time is valid (i.e semicolon is present, correct length)
     * @param time
     * @return boolean if time is valid, false otherwise
     */
    public static boolean validTime(String time) {

        boolean validHour = false;
        boolean validMinute = false;
        boolean validColon = false;
        boolean validLength = false;
        int indexOfColon = time.indexOf(":");

        //check to see if colon is in the correct place
        if ((indexOfColon == 1) || (indexOfColon == 2)) {
            validColon = true;
        } else {
            return false;
        }

        //makes sure the time is the correct length
        if ((time.length() == 5) || (time.length() == 4)) {
            validLength = true;
        } else {
            return false;
        }


        if (validColon && validLength) {

            String hourString = time.substring(0, indexOfColon);

            int hour = -1;

            if(isNumber(hourString)){
                hour = Integer.parseInt(hourString);
            }

            String minuteString = time.substring(indexOfColon + 1, time.length());

            int minute = -1;

            if (minuteString.length() == 2 && isNumber(minuteString)) {
                minute = Integer.parseInt(minuteString);
            }

            if (hour > 0 && hour < 13) {
                validHour = true;
            }

            if (minute >= 0 && minute < 60) {
                validMinute = true;
            }

            if (validHour == true && validMinute == true) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    /**
     * @param date
     * @return true if date is valid, false otherwise
     */
    public static boolean validDate(String date) {

        boolean numSlashes = false;
        boolean validMonth = false;
        boolean validDate = false;
        boolean validYearLength = false;

        //if less than 6, the date doesn't have the minimum length (m/d/yy)
        if (date.length() < 6) {
            return false;
        }

        //checks to see if date format is in mm/dd/year, m/dd/year, m/d/year, mm/d/year
        if ((date.charAt(2) == '/' && date.charAt(5) == '/') || (date.charAt(1) == '/' &&
                date.charAt(4) == '/') || (date.charAt(1) == '/' && date.charAt(3) == '/') ||
                (date.charAt(2) == '/' && date.charAt(4) == '/')) {
            numSlashes = true;
        } else {
            return false;
        }

        if (numSlashes) {

            String monthString = date.substring(0, date.indexOf("/"));

            int month = -1;

            if(isNumber(monthString) && monthString.length() > 0) {
                month = Integer.parseInt(monthString);
            } else {
                return false;
            }

            if (month > 0 && month < 13) {
                validMonth = true;
            }

            String dayAndYear = date.substring(date.indexOf("/") + 1, date.length());

            String dayString = dayAndYear.substring(0, dayAndYear.indexOf("/"));

            int day = -1;

            if(isNumber(dayString) & dayString.length() > 0){
                day = Integer.parseInt(dayString);
            } else {
                return false;
            }

            if (day > 0 && day <= 31) {
                validDate = true;
            }

            String year = dayAndYear.substring(dayAndYear.indexOf("/") + 1, dayAndYear.length());

            if(!isNumber(year)) {
                return false;
            }

            if (year.length() == 2 || year.length() == 4) {
                validYearLength = true;
            }
        }

        if (validMonth && validDate && validYearLength) {
            return true;
        }

        return false;
    }

    /**
     * method parses a string and if a numberFormatException happens, it returns false
     * @param number
     * @return true of false if the string is a number
     */
    public static boolean isNumber(String number) {

        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

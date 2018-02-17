package com.example.emilyhowing.emotiontracker2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void validTimeTestTrue() throws Exception {

        String time1 = "1:34";
        assertTrue(DateAndTime.validTime(time1));

        String time2 = "12:59";
        assertTrue(DateAndTime.validTime(time2));

        String time3 = "5:00";
        assertTrue(DateAndTime.validTime(time3));

        String time4 = "1:01";
        assertTrue(DateAndTime.validTime(time4));

        String time5 = "05:23";
        assertTrue(DateAndTime.validTime(time5));

        String time6 = "01:00";
        assertTrue(DateAndTime.validTime(time6));

        String time7 = "12:00";
        assertTrue(DateAndTime.validTime(time7));

        String time8 = "03:03";
        assertTrue(DateAndTime.validTime(time8));
    }

    @Test
    public void validTimeTestFalse() throws Exception {
        String time7 = "00:00";
        assertFalse(DateAndTime.validTime(time7));

        String wrongTime1 = ":43";
        assertFalse(DateAndTime.validTime(wrongTime1));

        String wrongTime2 = "";
        assertFalse(DateAndTime.validTime(wrongTime2));

        String wrongTime3 = "1:1";
        assertFalse(DateAndTime.validTime(wrongTime3));

        String wrongTime4 = "10:101";
        assertFalse(DateAndTime.validTime(wrongTime4));

        String wrongTime5 = " ";
        assertFalse(DateAndTime.validTime(wrongTime5));

        String wrongTime6 = "12:  ";
        assertFalse(DateAndTime.validTime(wrongTime6));

        String wrongTime7 = "  :  ";
        assertFalse(DateAndTime.validTime(wrongTime7));

        String wrongTime8 = ":::::";
        assertFalse(DateAndTime.validTime(wrongTime8));

        String wrongTime9 = "--:--";
        assertFalse(DateAndTime.validTime(wrongTime9));
    }

    @Test
    public void validDateTestTrue() throws Exception {
        String date1 = "12/7/2017";
        assertTrue(DateAndTime.validDate(date1));

        String date2 = "1/1/17";
        assertTrue(DateAndTime.validDate(date2));

        String date3 = "1/1/2017";
        assertTrue(DateAndTime.validDate(date3));

        String date4 = "12/21/2017";
        assertTrue(DateAndTime.validDate(date4));

        String date5 = "9/9/19";
        assertTrue(DateAndTime.validDate(date5));

        String date6 = "09/01/2017";
        assertTrue(DateAndTime.validDate(date6));
    }

    @Test
    public void validDateTestFalse() throws Exception {
        String date1 = "13/12/2016";
        assertFalse(DateAndTime.validDate(date1));

        String date2 = "/12/2016";
        assertFalse(DateAndTime.validDate(date2));

        String date3 = "";
        assertFalse(DateAndTime.validDate(date3));

        String date4 = " ";
        assertFalse(DateAndTime.validDate(date4));

        String date5 = "1";
        assertFalse(DateAndTime.validDate(date5));

        String date6 = "        ";
        assertFalse(DateAndTime.validDate(date6));

        String date7 = "///////";
        assertFalse(DateAndTime.validDate(date7));

        //have to use slashes not dashes
        String date8 = "12-15-2017";
        assertFalse(DateAndTime.validDate(date8));

        String date9 = "12/15/2 17";
        assertFalse(DateAndTime.validDate(date9));

        String date10 = "1 /3/17";
        assertFalse(DateAndTime.validDate(date10));

        String date11 = "10/ 3/2017";
        assertFalse(DateAndTime.validDate(date11));
    }


    @Test
    public void isNumberTest() throws Exception {
        String number1 = "343535";
        assertTrue(DateAndTime.isNumber(number1));

        String number2 = "0";
        assertTrue(DateAndTime.isNumber(number2));

        String number3 = " ";
        assertFalse(DateAndTime.isNumber(number3));

        String number4 = "klkj";
        assertFalse(DateAndTime.isNumber(number4));

        String number5 = "48597:84";
        assertFalse(DateAndTime.isNumber(number5));
    }

}
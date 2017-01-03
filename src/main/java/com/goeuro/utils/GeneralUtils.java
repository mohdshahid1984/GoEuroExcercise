package com.goeuro.utils;

import com.goeuro.constants.TestDataLocators;
import com.goeuro.dataproviders.annotations.MethodArguments;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mshahid on 02/01/17.
 */
public class GeneralUtils
{
    public static final String CLASS_NAME = "class name";
    public static final String CSS = "css";
    public static final String ID = "id";
    public static final String LINK_TEXT = "link text";
    public static final String NAME = "name";
    public static final String PARTIAL_LINK_TEXT = "partial link text";
    public static final String TAG_NAME = "tag name";
    public static final String XPATH = "xpath";

    /**
     * @param driver
     * @param url
     */
    public static void openURL(WebDriver driver, String url)
    {
        driver.get(url);
    }

    /**
     * @param method
     * @return
     * @throws IllegalArgumentException
     */
    public static Map<String, String> resolveMethodArguments(Method method) throws IllegalArgumentException
    {
        if (method == null)
            throw new IllegalArgumentException("A null test method is invoked");

        MethodArguments methodArguments = method.getAnnotation(MethodArguments.class);

        if (methodArguments.value() == null || methodArguments.value().length == 0)
            throw new IllegalArgumentException("TestMethod Doesn't have any arguments");

        Map<String, String> map = new HashMap<>();
        for (String argument : methodArguments.value())
        {
            String[] parts = argument.split("=");
            map.put(parts[0].trim(), parts[1].trim());
        }

        return map;
    }

    public static By getBy(String locatorStrategy, String locator)
    {
        switch (locatorStrategy.toLowerCase())
        {
            case (CLASS_NAME):
                return new By.ByClassName(locator);
            case (CSS):
                return new By.ByCssSelector(locator);
            case (ID):
                return new By.ById(locator);
            case (LINK_TEXT):
                return new By.ByLinkText(locator);
            case (NAME):
                return new By.ByName(locator);
            case (PARTIAL_LINK_TEXT):
                return new By.ByPartialLinkText(locator);
            case (TAG_NAME):
                return new By.ByTagName(locator);
            case (XPATH):
                return new By.ByXPath(locator);
        }

        return null;
    }

    public static int numberOfMonthsFromCurrent(String startDateString, String date)
    {

        Calendar startCalendar = new GregorianCalendar();
        if (startDateString == null || startDateString.isEmpty())
            startCalendar.setTime(new Date());
        else
        {
            startCalendar.setTime(getDate(startDateString));
            startCalendar.add(Calendar.DATE, 1);
        }

        Date endDate = getDate(date);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        return (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));

    }

    private static Date getDate(String date)
    {
        Date date1 = null;
        try
        {
            date1 = getDateFormat().parse(date);
        }
        catch (ParseException pe)
        {
            pe.printStackTrace();
        }

        return date1;
    }

    private static DateFormat getDateFormat()
    {
        return new SimpleDateFormat(TestDataLocators.Test_Data_Date_Format);
    }

    public static int getDay(String date)
    {
        Calendar calendar = new GregorianCalendar();
        DateFormat format = getDateFormat();
        try
        {
            calendar.setTime(format.parse(date));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}

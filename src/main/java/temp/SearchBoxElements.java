package temp;



import com.goeuro.constants.WebElementLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by mshahid on 28/12/16.
 */

public class SearchBoxElements
{
    WebDriver driver;
    public SearchBoxElements(WebDriver driver){
        this.driver = driver;
    }
    public void tripType(String string)
    {

        if (string.equalsIgnoreCase("oneway")){
            driver.findElement(By.id("trip_type_2")).click();
        }
        if (string.equalsIgnoreCase("roundtrip")){
            driver.findElement(By.id("trip_type_1")).click();
        }
    }

    public void fillODPairs(String origin, String destination){
        driver.findElement(By.id("from_filter")).sendKeys(origin);
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(WebElementLocators.HomePage.AUTO_COMPLETE_MENU_ITEM, origin))));
        driver.findElement(By.xpath(String.format(WebElementLocators.HomePage.AUTO_COMPLETE_MENU_ITEM,
                                                  origin))).click();


        driver.findElement(By.id("to_filter")).sendKeys(destination);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(WebElementLocators.HomePage.AUTO_COMPLETE_MENU_ITEM, destination))));
        driver.findElement(By.xpath(String.format(WebElementLocators.HomePage.AUTO_COMPLETE_MENU_ITEM,
                                                  destination))).click();

        // driver.findElement(By.id("to_filter")).click();
        WebElement dateElement = driver.findElement(By.id("departure_date"));
        dateElement.click();

        String date  = "11-02-2017";
        int day = getDay(date);

        int diffMonths = diffMonths(date);
        for (int i=1;i<=diffMonths;i++)
            driver.findElement((By.className("ui-datepicker-next"))).click();

        List<WebElement> webElementList = driver.findElements(By.tagName("td"));
        for (int i=day;i<webElementList.size();i++){
            if (Integer.parseInt(webElementList.get(i).getText())==day)
            {
                webElementList.get(i).click();
                break;
            }
        }


        driver.findElement(By.className("hotel-checkbox")).click();

        //driver.findElement(By.id("search")).click();
        //WaitUtility.waitForElementToBeVisibleBy(driver, SeleniumUtility., "ui-id-1", 3);
        //        String xpath = "//*[@id=\"search-form__submit-btn\"]";
        //        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
        //                .until(ExpectedConditions.presenceOfElementLocated(By.id("search-form__submit-btn")));

        WebElement buttonElement = driver.findElement(By.id("search-form__submit-btn"));
        buttonElement.click();

        wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.className("Result__result___ebQmu"))));

        List<WebElement> list = driver.findElements(By.className("Result__result___ebQmu"));

        for (WebElement element:list)
        {
            System.out.print(element.findElement(By.className("Result__priceMain___25qv5")).getText());
            System.out.print(element.findElement(By.className("Result__priceFraction___16hVT")).getText()+"  ");
        }
        //((FirefoxDriver) driver).findElementByXPath("//*[@data-key=\"dw.paging.next\"]").click()
        //        String test = driver.findElements(By.className("Result__result___ebQmu")).get(0).findElement(By.xpath("//span[contains(@class,'Result__priceMain___')]")).getText();
        //        String test1 = driver.findElements(By.className("Result__result___ebQmu")).get(2).findElement(By.xpath("//span[contains(@class,'Result__priceMain___')]")).getText();
        //        myDynamicElement.click();
        //        try{Thread.sleep(2000);}catch (Exception e){
        //            e.printStackTrace();
        //        }
        //        buttonElement.click();
        //        buttonElement.click();
        //        buttonElement.click();
        //driver.manage().timeouts().implicitlyWait();
        //        element.submit();
        //driver.findElement(By.id("search-form__submit-btn")).submit();
        System.out.println();
    }
    private int getDay(String date){
        Calendar calendar = new GregorianCalendar();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try{
            calendar.setTime(format.parse(date));
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    private Date getTravelDate(String travelDate){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date endDate = null;
        try{
            endDate = dateFormat.parse(travelDate);
        }catch (ParseException pe){
            pe.printStackTrace();
        }

        return endDate;
    }
    private int diffMonths(String travelDate){

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date endDate = null;
        try{
            endDate = dateFormat.parse(travelDate);
        }catch (ParseException pe){
            pe.printStackTrace();
        }
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(new Date());
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return diffMonth;
    }


}

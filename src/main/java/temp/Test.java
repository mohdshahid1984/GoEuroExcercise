package temp;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mshahid on 28/12/16.
 */
public class Test
{
    public static void main(String[] strings){

        String url = "http://www.goeuro.com/GoEuroAPI/rest/api/v5/searches";
        //String request = "from_filter=Madrid, Spain&departure_fk=378655&departure_date=10 Jan 2017&to_filter=Barcelona, Spain&arrival_fk=378468&return_date=18 Jan 2017&bonuscards[0]=renfe:adultCard1:Tarjeta_Dorada&bonuscards[0]=renfe:adultCard1:Tarjeta_Dorada&trip_type=R&departure_lat=40.4165&departure_lng=-3.70256&arrival_lat=41.38879&arrival_lng=2.15899&departure_time=any&return_time=any&adults=1&children=0&infants=0&trip_by=['train','flight','bus']&cabin_type=1&ab_test_enabled=&travel_mode=&tracking_information=&soid=0&v=&departurePositionNameType=NAME&arrivalPositionNameType=NAME";
        String request = "{\"searchOptions\":{\"departurePosition\":{\"id\":378655},\"arrivalPosition\":{\"id\":378468},\"travelModes\":[\"Flight\",\"Train\",\"Bus\"],\"departureDate\":\"2017-01-11\",\"returnDate\":\"2017-01-17\",\"passengers\":[{\"age\":12,\"discountCards\":[{\"provider\":\"renfe\",\"code\":\"Tarjeta_Dorada\"}]}],\"userInfo\":{\"identifier\":\"0.kcvdgf5m0jxw55rzpfe597ldi\",\"domain\":\".com\",\"locale\":\"en\",\"currency\":\"EUR\"},\"abTestParameters\":[]}}";
        HttpEntity entity = new NStringEntity(request, ContentType.APPLICATION_JSON);
        RestClient client = RestClient.builder(new HttpHost("www.goeuro.com",80,"http")).build();
        Response searchresponse = null;
        String output = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            searchresponse = client.performRequest("POST", "/GoEuroAPI/rest/api/v5/searches", Collections.<String, String>emptyMap(),entity);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((searchresponse.getEntity().getContent())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
               sb.append(output);
                System.out.println(output);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        output = sb.toString();
        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(output).getAsJsonObject();

        Integer searchId = Integer.parseInt(element.get("searchId").getAsString());


        System.out.println();
        StringBuilder trainResponseBuilder = new StringBuilder();
        String trainRequest = "http://www.goeuro.com/GoEuroAPI/rest/api/v5/results?price_from=1&stops=0%7C1%7C2%3B-1&travel_mode=train&limit=10&offset=0&position_report_enabled=true&all_positions=true&search_id=355209284&sort_by=price&sort_variants=price&use_stats=true&ts=1482470870915";
        //http://www.goeuro.com/GoEuroAPI/rest/api/v5/results?price_from=1&stops=0|1|2;-1&travel_mode=train&limit=10&offset=0&position_report_enabled=true&all_positions=true&search_id=355209284&sort_by=price&sort_variants=price&use_stats=true&ts=1482470870915
        String trainUrl = "/GoEuroAPI/rest/api/v5/results?travel_mode=train&sort_by=price&search_id="+element.get("searchId").getAsString();
        Response trainResponse  = null;
        StringBuilder builder2 = new StringBuilder();
        try
        {
            trainResponse = client.performRequest("GET", trainUrl, Collections.<String, String>emptyMap(),entity);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((trainResponse.getEntity().getContent())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                trainResponseBuilder.append(output);
                System.out.println(output);
            }
            Thread.sleep(5000);
            //trainResponseBuilder.delete(0,trainResponseBuilder.length()-1);

            trainResponse = client.performRequest("GET", trainUrl, Collections.<String, String>emptyMap(),entity);
            BufferedReader br1 = new BufferedReader(
                    new InputStreamReader((trainResponse.getEntity().getContent())));


            System.out.println("Output from Server .... \n");
            while ((output = br1.readLine()) != null) {
                builder2.append(output);
                System.out.println(output);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        output = trainResponseBuilder.toString();
        String output2 = builder2.toString();
        System.out.println();


        System.setProperty("webdriver.gecko.driver","drivers/geckodriver");

        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.goeuro.com/");
        SearchBoxElements searchElements =  new SearchBoxElements(driver);
        searchElements.tripType("oneway");
        searchElements.fillODPairs("Prague, Czech Republic","Barcelona, Spain");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        dateFormat.format(date);
        System.out.println();
        Calendar startDate = new GregorianCalendar();
        startDate.setTime(date);
        String endDate = "22-02-2017";
        Calendar endCal = new GregorianCalendar();
        Date end = null;
        try
        {
            end = dateFormat.parse(endDate);
        }catch (ParseException pe){
            pe.printStackTrace();
        }

        endCal.setTime(end);

        driver.quit();

        //        System.out.println("------" + driver.getCurrentUrl() + "-------");
    }




}


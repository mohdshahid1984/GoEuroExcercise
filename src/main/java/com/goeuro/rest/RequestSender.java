package com.goeuro.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * Created by mshahid on 02/01/17.
 */
public class RequestSender
{
    /* private  String readAll(Reader rd) throws IOException {
         StringBuilder sb = new StringBuilder();
         int cp;
         while ((cp = rd.read()) != -1) {
             sb.append((char) cp);
         }
         return sb.toString();
     }
     public JSONObject readJsonFromUrl(String url)  {
         JSONObject json = null;
         try {
         InputStream is = new URL(url).openStream();

             BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
             String jsonText = readAll(rd);
             JSONParser parser = new JSONParser();
             json = (JSONObject) parser.parse(jsonText);

         } catch (IOException e){
             Assert.fail("test failed while opening the url = " + url + " Please check your URL/Request");
         }catch (ParseException pe){
             Assert.fail("test failed while parsing json response.");
         }
         return json;
     }*/
    public JSONAware sendGETTRequest(String url)
    {
        return sendRequest(url, "GET", "");
    }

    public JSONAware sendRequest(String url, String request)
    {
        return sendRequest(url, "POST", request);
    }

    private JSONAware sendRequest(String url, String requestType, String request)
    {
        RestClient client = RestClient.builder(new HttpHost("www.goeuro.com", 80, "http")).build();
        Response searchResponse;
        Object object = null;
        try
        {
            if (requestType.equalsIgnoreCase("POST"))
            {
                HttpEntity entity = new NStringEntity(request, ContentType.APPLICATION_JSON);
                searchResponse = client.performRequest("POST", url, Collections.<String, String>emptyMap(), entity);
            }
            else
            {
                searchResponse = client.performRequest("GET", url);
            }
            object = JSONValue.parse(new BufferedReader(new InputStreamReader((searchResponse.getEntity().getContent()))));

            if (object instanceof JSONArray)
                return jsonArray(object);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            Assert.fail("test failed while opening the url = " + url + " Please check your URL/Request");
        }

        return jsonObject(object);

    }

    private JSONObject jsonObject(Object object)
    {
        return (JSONObject) object;
    }

    private JSONArray jsonArray(Object object)
    {
        return (JSONArray) object;
    }
}

package com.goeuro.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.logging.Logger;

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
   public JSONAware sendGETTRequest(String url){
       return sendPOSTRequest(url,"GET","");
   }
    public JSONAware sendPOSTRequest(String url, String request){
        return sendPOSTRequest(url,"POST",request);
    }
    public JSONAware sendPOSTRequest(String url, String requestType, String request){
        RestClient client = RestClient.builder(new HttpHost("www.goeuro.com",80,"http")).build();
        Response searchresponse = null;
        String output = null;
        StringBuilder sb = new StringBuilder();
        Object object = null;
        try
        {
            if (requestType.equalsIgnoreCase("POST"))
            {
                HttpEntity entity = new NStringEntity(request, ContentType.APPLICATION_JSON);
                searchresponse = client.performRequest("POST", url, Collections.<String, String>emptyMap(), entity);
            }else {
                searchresponse= client.performRequest("GET",url);
            }
             object = (JSONAware)JSONValue.parse(new BufferedReader(new InputStreamReader((searchresponse.getEntity().getContent()))));

                if (object instanceof JSONArray)
                    return jsonArray(object);

        }catch (IOException e){
            e.printStackTrace();
            Assert.fail("test failed while opening the url = " + url + " Please check your URL/Request");
        }

        return jsonObject(object);

    }
    private JSONObject jsonObject(Object object){
        return (JSONObject) object;
    }

    private JSONArray jsonArray(Object object){
            return (JSONArray) object;
}
}

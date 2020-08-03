package com.sg.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.sg.domain.WeatherInfo;
import com.sg.repo.WeatherInfoRepository;

import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder.Block;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

@Component
@Service
public class IWeatherServiceImpl implements IWeatherService {

    private UriComponentsBuilder urlbuilder;
private Block block;
final DateTimeFormatter formatter = 
DateTimeFormatter.ofPattern("yyyy-MM-dd");
LocalDate date = LocalDate.now();
LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
@Autowired
private Environment env;

@Autowired
private WeatherInfoRepository repository;
 

    @Override
    public JSONObject getForecast(String city) throws ForecastException, ParseException {
    	System.out.println("city-- >" +city);
    	JSONParser parser = new JSONParser();
    	List<JSONObject> json = new ArrayList<JSONObject>();
    	JSONObject jsonInput = new JSONObject();
    	String coOrd = env.getProperty("ordinate."+city);
    	String[] coOrdVal = coOrd.split(",");
    	
    	 
    	json = repository.findByCityName(city,date.format(formatter));
       
    		    if(json.size()!=0) {
    		    	 return json.get(0);    		    	  
    		    	
    		    }else {
    		    	System.out.println("inside else");
    		    	ForecastRequest request = new ForecastRequestBuilder()
    	    		        .key(new APIKey("ccae7a00b3ce0cf1dee5541a48be0dd7"))
    	    		        .location(new GeoCoordinates(new Longitude(Double.parseDouble(coOrdVal[0])), new Latitude(Double.parseDouble(coOrdVal[1]))))
    	    		        .exclude(block.daily).exclude(block.flags).exclude(block.hourly).exclude(block.minutely).build();

    	    		    DarkSkyClient client = new DarkSkyClient();
    	    		    String forecast = client.forecastJsonString(request);
    	    		    JSONObject jsonOld = (JSONObject) parser.parse(forecast);
    	    		    jsonInput = (JSONObject)jsonOld.get("currently");
    	    		    System.out.println("jsonInput"+jsonInput);
    	    		    final String formattedDtm = Instant.ofEpochSecond(Long.parseLong(jsonInput.get("time").toString()))
    	    			        .atZone(ZoneId.of("GMT+8"))
    	    			        .format(formatter);
    	    		    
    		    	 try {
 		    		    repository.save(new WeatherInfo(city,formattedDtm,jsonInput.get("windGust"),jsonInput.get("icon"),jsonInput.get("cloudCover")
 		    		    		,jsonInput.get("temperature"),jsonInput.get("pressure"),
 		    		    		jsonInput.get("dewPoint"),jsonInput.get("ozone"),jsonInput.get("humidity"),jsonInput.get("windSpeed"),jsonInput.get("timezone"),
 		    		    		jsonInput.get("time")));
 		    		    
 		    		    }catch(Exception e) {
 		    		    	e.printStackTrace();
 		    		    	
 		    		    }
    		    	 return jsonInput;
    		    } 
    		    	 
    		
    }
}

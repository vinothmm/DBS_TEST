package com.sg.web;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter; 

import org.json.simple.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import com.sg.service.IWeatherService;

@Controller
public class WeatherAccessController {

	  private static final Logger logger = LogManager.getLogger(WeatherAccessController.class);

	 
	    @Autowired
	    private IWeatherService yahooService;
	    final DateTimeFormatter formatter = 
	    	    DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    @RequestMapping(value = "/", method = RequestMethod.GET)
	    public String index() {
	        return "redirect:/forecastInfo/Campbell";
	    }

	    @RequestMapping(value = "/forecastInfo/{city}", method = RequestMethod.GET)
	    public String getForecast(Model model, @PathVariable("city") String city) throws Exception {
	System.out.println("inside controller new  ");
	logger.debug("inside controller");
	JSONObject yahooResponse = (JSONObject) yahooService.getForecast(city);
	final String formattedDtm = Instant.ofEpochSecond(Long.parseLong(yahooResponse.get("time").toString()))
	        .atZone(ZoneId.of("GMT+8"))
	        .format(formatter);
	System.out.println("formattedDtm "+formattedDtm);
			model.addAttribute("cityKey", city);
	        model.addAttribute("temperature",yahooResponse.get("temperature")+"°C");
	        model.addAttribute("icon",yahooResponse.get("icon"));
	        model.addAttribute("dewPoint",yahooResponse.get("dewPoint"));
	        model.addAttribute("humidity",yahooResponse.get("humidity"));
	        model.addAttribute("pressure",yahooResponse.get("pressure"));
	        model.addAttribute("windSpeed",yahooResponse.get("windSpeed"));
	        model.addAttribute("uvIndex",yahooResponse.get("uvIndex"));
	   
	        return Pages.FORECAST;

	    }

	    @ExceptionHandler(value = Exception.class)
	    public String handleException(Exception e){
	    	e.getStackTrace();
	        return Pages.ERROR;
	    }
	
}

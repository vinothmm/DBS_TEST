package com.sg.service;

import org.json.simple.JSONObject;

public interface IWeatherService{

    public JSONObject getForecast(String city) throws Exception;

}


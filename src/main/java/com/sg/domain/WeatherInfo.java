package com.sg.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "weatherInfo")
public class WeatherInfo {

	@Id
	private String id;
	private String cityName;
	private String date;
	private Object windGust;
	private Object icon;
	private Object cloudCover;
	private Object pressure;
	private Object dewPoint;
	private Object ozone;
	private Object temperature;
	private Object humidity;
	private Object windSpeed;
	private Object timezone;
	private Object time;
	
	public WeatherInfo(String cityName, String date,Object windGust, Object icon, Object cloudCover,Object temperature,Object pressure,Object dewPoint,
			Object ozone,Object humidity,Object windSpeed,Object timezone,Object time) {
		this.cityName = cityName;
		this.date = date;
		this.windGust = windGust;
		this.icon = icon;
		this.cloudCover = cloudCover;
		this.temperature = temperature;
		this.pressure= pressure;
		this.dewPoint = dewPoint;
		this.ozone= ozone;
		this.humidity = humidity;
		this.windSpeed = windSpeed;
		this.timezone = timezone;
		this.time  = time;
	}

}

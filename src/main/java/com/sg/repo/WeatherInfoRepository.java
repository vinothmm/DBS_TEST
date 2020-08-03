package com.sg.repo;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;
import com.sg.domain.WeatherInfo;

@Repository
public interface WeatherInfoRepository extends MongoRepository<WeatherInfo, String> {
	
	
	
    @Query("{cityName:'?0','date' : ?1}")
    List<JSONObject> findByCityName(String cityName , String date);
     
     

  
}


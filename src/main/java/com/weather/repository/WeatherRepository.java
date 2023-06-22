package com.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.entity.Weather;

public interface WeatherRepository extends  JpaRepository<Weather, Integer>{
	
	public Weather findByCity(String city);

}

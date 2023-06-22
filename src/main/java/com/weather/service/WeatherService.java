package com.weather.service;

import com.weather.entity.Weather;

public interface WeatherService {
	
	Weather getWeatherDetails(String city);
	Weather addCity(Weather weather);
	

}

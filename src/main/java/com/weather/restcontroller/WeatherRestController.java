package com.weather.restcontroller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.entity.Weather;
import com.weather.jwt.JwtUtil;
import com.weather.serviceimpl.WeatherServiceImpl;
import com.weather.userentiy.UserRequest;

@RestController

public class WeatherRestController {
	
	@Autowired
	private WeatherServiceImpl service;
	
	@PostMapping("/addweather")
	public ResponseEntity<Weather> addWetherDetails(@RequestBody Weather weather){
		return new ResponseEntity<Weather>(service.addCity(weather),HttpStatus.CREATED);
		
	}
	@GetMapping("/getweather")
	public Weather getWeatherDetails(@RequestParam String city,Principal principal) {
		Weather weatherDetails = service.getWeatherDetails(city);
		return weatherDetails;
		
	}
	
}

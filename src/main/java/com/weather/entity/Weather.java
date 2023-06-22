package com.weather.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="weather_details")
@Data
public class Weather {

	
	
		@Id
		@GeneratedValue
		private Integer id;
		private String city;
		private Integer currentTemperature;
		private Integer minTemperature;
		private Integer maxTemperature;
		private double windSpeed;
		

	
}

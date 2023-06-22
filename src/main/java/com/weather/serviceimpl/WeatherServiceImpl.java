package com.weather.serviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weather.entity.Weather;
import com.weather.repository.WeatherRepository;
import com.weather.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService{
	@Autowired
	private WeatherRepository repo;
	@Override
	public Weather getWeatherDetails(String city) {
		
		return repo.findByCity(city);
	}

	@Override
	public Weather addCity(Weather weather) {
		return repo.save(weather);
	}

}

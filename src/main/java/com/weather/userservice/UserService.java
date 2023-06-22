package com.weather.userservice;

import java.util.Optional;

import com.weather.userentiy.User;

import ch.qos.logback.core.net.server.Client;

public interface UserService {
	User saveClient(User user);
	Optional<User> findByName(String name);

}

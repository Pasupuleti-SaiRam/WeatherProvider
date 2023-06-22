package com.weather.userrepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weather.userentiy.User;

import ch.qos.logback.core.net.server.Client;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByName(String name);

}

package com.weather.userserviceimpl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.weather.userentiy.User;
import com.weather.userrepository.UserRepository;
import com.weather.userservice.UserService;

import ch.qos.logback.core.net.server.Client;
@Service
public class UserServiceImpl implements UserService ,UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	@Override
	public User saveClient(User user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	// get user name from db
	@Override
	public Optional<User> findByName(String name) {
		Optional<User> username = userRepository.findByName(name);
		return username;
	}
	//read user deatail from db
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> existUser = findByName(username);
		if(existUser.isEmpty())
			throw new UsernameNotFoundException("user is not exist");
		User user = existUser.get();
		
		return new org.springframework.security.core.userdetails.User(
				username, 
				user.getPassword(), 
				user.getRoles().stream()
				.map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList()));
	}
	

}

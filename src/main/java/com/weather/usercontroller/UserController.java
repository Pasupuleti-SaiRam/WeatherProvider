package com.weather.usercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weather.exceptions.InvalidCredentialsException;
import com.weather.exceptions.InvalidTokenException;
import com.weather.jwt.JwtUtil;
import com.weather.userentiy.User;
import com.weather.userentiy.UserRequest;
import com.weather.userentiy.UserResponse;
import com.weather.userserviceimpl.UserServiceImpl;

import ch.qos.logback.core.net.server.Client;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private JwtUtil util;
	// this for validating user creentials
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/user")
	public ResponseEntity<User> saveClientDetails(@RequestBody User user) {
		System.out.println(user);
		return new ResponseEntity<User>(userServiceImpl.saveClient(user), HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> getToken(@RequestBody UserRequest userLoginDetails) {
		// validate username and password
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDetails.getUsername(),
					userLoginDetails.getPassword()));
			String username = userLoginDetails.getUsername();
			String token = util.generateToken(username);
			System.out.println("this is provider" + token);
			return new ResponseEntity<UserResponse>(new UserResponse(token, "Login Is Success"), HttpStatus.OK);

		} catch (AuthenticationException ex) {
			throw new InvalidCredentialsException("Invalid User Credentials");
		} catch (JwtException ex) {
			if (ex instanceof SignatureException) {
				throw new InvalidTokenException("Invalid Token Signature");
			} else {
				throw new InvalidTokenException("Invalid Token");
			}

		}

	}
}

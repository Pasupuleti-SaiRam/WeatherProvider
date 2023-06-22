package com.weather.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.weather.jwt.JwtUtil;
@Component
public class SecurityFilter  extends OncePerRequestFilter{
	@Autowired
	private JwtUtil util;
	@Autowired
	private  UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			
			throws ServletException, IOException {
		//read token from authorization header
		String token = request.getHeader("Authorization");
		if(token!=null) {
			// do validation
			String userName = util.getUserName(token);
			// username not be empty, context authentication must be null
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				//here im loading username from db and cross verify db and token username same or not
				UserDetails user = userDetailsService.loadUserByUsername(userName);
				//validate token 
				boolean isValid = util.validateToken(token, user.getUsername());
				if(isValid) {
					UsernamePasswordAuthenticationToken authenticateToken=
							new UsernamePasswordAuthenticationToken(userName, user.getPassword(),user.getAuthorities());
					authenticateToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// final obj stored in securitycontext with user details(username, password)
					SecurityContextHolder .getContext().setAuthentication(authenticateToken);
				}
				
			}
		}
		filterChain.doFilter(request, response);
		
	}

}

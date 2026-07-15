package com.trial.demo.controller;

import com.trial.demo.dto.AuthRequest;
import com.trial.demo.dto.AuthResponse;
import com.trial.demo.entity.MyUser;
import com.trial.demo.service.JwtService;
import com.trial.demo.service.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class MyUserController {
    @Autowired
    private MyUserDetailsService us;

    @Autowired
    private AuthenticationManager am;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger =
            LoggerFactory.getLogger(MyUserController.class);

    @PostMapping("/signup")
    public MyUser signup(@RequestBody MyUser user) {
        logger.info(user.toString());
        return us.create(user);
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        Authentication auth = am.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        AuthResponse response=null;
        if(auth.isAuthenticated()) {
            //give jwt

            UserDetails userDetails=	(UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            response=new AuthResponse();
            response.setUsername(userDetails.getUsername());
            response.setToken(token);
            //convert authorities to roles
            String roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> role.replaceFirst("^ROLE_", ""))
                    .collect(Collectors.joining(","));

            response.setRoles(roles);
        }else {
            throw new UsernameNotFoundException("Login failed");
        }

        return response;
    }

}

package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Configuration.JwtTokenProvider;
import com.infy.WikiDocsProject.Exception.IncorrectCredentialsException;
import com.infy.WikiDocsProject.Model.AuthBody;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Service.CustomUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Muhammad Sultan
 */
@SuppressWarnings("rawtypes")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/AuthAPI")
public class AuthAPI {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    static Logger logger = LogManager.getLogger(AuthAPI.class);

    /**
     * login a user with given email and password.
     * @param data contains entered credentials
     * @return map containing email, user role, and token
     */
    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody AuthBody data){
        try{
            logger.info("USER TRYING TO LOGIN WITH EMAIL " + data.getEmail());

            //retrieve email from AuthBody to retrieve user and their roles
            String username = data.getEmail();
            User user = customUserDetailsService.findByEmail(data.getEmail());
            List<GrantedAuthority> authorities = customUserDetailsService.getUserAuthority(user.getRoles());

            //authenticate user credentials
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword(), authorities));

            //create a token storing email and user role
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            logger.info("LOGIN SUCCESSFUL FOR " + data.getEmail());

            //create a map to store necessary information to send back
            //to frontend
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("role", user.getRoles());
            model.put("token",token);

            return ok(model);
        }
        catch(AuthenticationException e){
            logger.info("LOGIN FAILED INVALID CREDENTIALS PROVIDED");
            throw new IncorrectCredentialsException("AuthAPI.INCORRECT_CREDENTIALS");
        }

    }

    @PostMapping("register")
    @ResponseBody
    public ResponseEntity register(@RequestBody User user){
        logger.info("USER REGISTERING WITH EMAIL: " + user.getEmail());

        //call user service to register the user
        customUserDetailsService.register(user);

        logger.info("USER REGISTERED WITH EMAIL: " + user.getEmail());

        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }
}

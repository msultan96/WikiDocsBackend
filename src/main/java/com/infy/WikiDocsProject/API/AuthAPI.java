package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Configuration.JwtTokenProvider;
import com.infy.WikiDocsProject.Model.AuthBody;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody AuthBody data){
        try{
            String username = data.getEmail();
            User user = customUserDetailsService.findByEmail(data.getEmail());
            List<GrantedAuthority> authorities = customUserDetailsService.getUserAuthority(user.getRoles());
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword(), authorities));
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("role", user.getRoles());
            model.put("token",token);
            return ok(model);
        }
        catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid credentials provided");
        }

    }

    @PostMapping("register")
    @ResponseBody
    public ResponseEntity register(@RequestBody User user){
        customUserDetailsService.register(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }
}

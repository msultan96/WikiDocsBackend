package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.UserAlreadyExistsException;
import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.Role;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.RoleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value="customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * Autowired using field injection because of conflict in WebSecurityConfig
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User findByEmail(String email){
        Optional<User> optional = userRepository.findByEmail(email);
        if(optional.isPresent()){
            User user = optional.get();
            return user;
        }
        else{
            throw new UserNotFoundException("UserService.USER_NOT_FOUND");
        }
    }

    public String getNameByEmail(String email){
        User user = findByEmail(email);
        return user.getName();
    }

    public void register(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("UserService.EMAIL_IN_USE");
        }else {
            Role userRole = roleRepository.findByRole("USER");
            Role adminRole = roleRepository.findByRole("ADMIN");
            User newUser = User.builder()
                    .id(new ObjectId())
                    .email(user.getEmail().toLowerCase())
                    .password(bCryptPasswordEncoder.encode(user.getPassword()))
                    .name(user.getName())
                    .articles(new ArrayList<>())
                    .collaboratingArticles(new ArrayList<>())
                    .roles(new HashSet<>(Arrays.asList(userRole)))
                    .enabled(true)
                    .build();
            if(user.getEmail().equals("muhammad.sultan96@gmail.com"))
                newUser.setRoles(new HashSet<>(Arrays.asList(adminRole)));
            userRepository.insert(newUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<User> optional = userRepository.findByEmail(email);
        if(optional.isPresent()){
            User user = optional.get();
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        }
        else{
            throw new UsernameNotFoundException("UserService.USER_NOT_FOUND");
        }
    }

    public List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}

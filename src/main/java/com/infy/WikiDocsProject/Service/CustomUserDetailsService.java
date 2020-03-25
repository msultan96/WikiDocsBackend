package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.RegistrationException;
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

    /**
     * email lookup inside of user repository
     * @param email email of user
     * @return user with given email
     */
    public User findByEmail(String email){
        //change email to lowercase
        email = email.toLowerCase();

        //retrieve an optional user from repository
        //return the user object if optional is present
        //throw exception if optional is not present
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
        //change email to lowercase
        email = email.toLowerCase();

        //get the user
        User user = findByEmail(email);

        return user.getName();
    }

    /**
     * registers a user.
     * handles input validtion
     * @param user the user being registered
     */
    public void register(User user) {
        //retrieve user information
        String email = user.getEmail().toLowerCase();
        String password = user.getPassword();
        String name = user.getName();

        //validate that the email is an email
        if(!email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
            throw new RegistrationException("UserService.REGISTRATION_INVALID_EMAIL");
        }
        //validate that the password meets conditions
        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-=_+`~\\[\\]\\{\\};:'\",<.>/?\\\\|]).{6,}$")){
            throw new RegistrationException("UserService.REGISTRATION_INVALID_PASSWORD");
        }
        //validate that the name is a name
        if(!name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
            throw new RegistrationException("UserService.REGISTRATION_INVALID_NAME");
        }

        //check to see if a user exists with the given email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("UserService.EMAIL_IN_USE");
        }else {
            //retrieve roles to give to a user
            Role userRole = roleRepository.findByRole("USER");
            Role adminRole = roleRepository.findByRole("ADMIN");

            //use lombok builder method to create a user
            User newUser = User.builder()
                    .id(new ObjectId())
                    .email(email)
                    .password(bCryptPasswordEncoder.encode(password))
                    .name(name)
                    .articles(new ArrayList<>())
                    .collaboratingArticles(new ArrayList<>())
                    .roles(new HashSet<>(Arrays.asList(userRole)))
                    .enabled(true)
                    .build();

            //hardcode admin privileges to user with specific email
            if(user.getEmail().equals("muhammad.sultan96@gmail.com"))
                newUser.setRoles(new HashSet<>(Arrays.asList(adminRole)));

            userRepository.insert(newUser);
        }
    }

    /**
     * loads the user and changes it into
     * a spring security user object
     * @param email email entered by user
     * @return a spring security UserDetails object
     */
    @Override
    public UserDetails loadUserByUsername(String email){
        email = email.toLowerCase();
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

    /**
     * changes the roles of a user to GrantedAuthority
     * for spring security purposes
     * @param userRoles roles a user has
     * @return GrantedAuthorities
     */
    public List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    /**
     * changes a user to a spring security User object
     * @param user the user being logged in
     * @param authorities the authorities a user has
     * @return a userDetails object
     */
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        String email = user.getEmail().toLowerCase();
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
    }
}

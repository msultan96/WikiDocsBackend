package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.PasswordIncorrectException;
import com.infy.WikiDocsProject.Exception.UserAlreadyExistsException;
import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.enums.Role;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
/**
 * 
 * User Service Implementations
 *
 */
@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * Retrieves the user associated with the email
	 * @param email Used to locate the user
	 * @return The user found
	 * @throws UserNotFoundException If the email isn't found
	 */
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()){
			return user.get();
		}
		else{
			throw new UserNotFoundException("UserService.USER_NOT_FOUND");
		}
	}

	/**
	 * Retrieves the user with entered email and validates the password
	 * @param email Email of the user logging in
	 * @param password Password of the user logging in
	 * @return The authenticated user
	 * @throws PasswordIncorrectException If the password is incorrect
	 */
	public User findByEmailAndPassword(String email, String password) {
		User user = findByEmail(email);
		if(bCryptPasswordEncoder.matches(password, user.getPassword())){
//		if(password.equals(optionalUser.get().getPassword())){
			return user;
		}
		else{
			throw new PasswordIncorrectException("UserService.INCORRECT_PASSWORD");
		}
	}

	/**
	 * The method to register a user.
	 * @param user The registrants information
	 * @return The registered user
	 */
	public User register(User user) {
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		if(optionalUser.isPresent()){
			throw new UserAlreadyExistsException("UserService.EMAIL_IN_USE");
		}
		else{
			User newUser = User.builder()
					.id(new ObjectId())
					.name(user.getName())
					.email(user.getEmail())
					.articles(new ArrayList<>())
					.password(bCryptPasswordEncoder.encode(user.getPassword()))
					.role(Role.USER)
					.build();
			userRepository.insert(newUser);
			return newUser;
		}
	}

	/**
	 * Get the name of the user with the given email
	 * @param email to find the user in the database
	 * @return the name of the user requested
	 */
	public String getNameByEmail(String email){
		User user = findByEmail(email);
		return user.getName();
	}

	public List<String> getAllUserEmails(){
		List<User> users = userRepository.findAll();
		List<String> userEmails = Collections.emptyList();
		users.stream()
				.filter(user -> user.getRole() == Role.ADMIN)
				.forEach(user -> userEmails.add(user.getEmail()));

		return userEmails;
	}
}

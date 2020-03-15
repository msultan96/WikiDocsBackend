package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.PasswordIncorrectException;
import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
/**
 * 
 * User Service Implementations
 * @Service - declared ArticleService class as a Service class
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
	public UserServiceImpl(UserRepository userRepository , BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * @name findUserByEmail
	 * @Desciption Find user of given email
	 * @param email
	 * @return user object
	 */
	public User findUserByEmail(String email) {
		// Called findUserByEmail() from userRepository class to find user of given name
		Optional<User> optionalUser = userRepository.findUserByEmail(email);
		// if user is present
		if(optionalUser.isPresent()){
			return optionalUser.get();
		}
		else{
			throw new UserNotFoundException("UserService.USER_NOT_FOUND");
		}
	}

	public User findUserByEmailAndPassword(String email, String password) {
		Optional<User> optionalUser = userRepository.findUserByEmail(email);
		if(optionalUser.isPresent()){
			if(bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())){
//			if(password.equals(optionalUser.get().getPassword())){
				return optionalUser.get();
			}
			else{
				throw new PasswordIncorrectException("UserService.INCORRECT_PASSWORD");
			}
		}
		else{
			throw new UserNotFoundException("UserService.USER_NOT_FOUND");
		}
	}
}

package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.PasswordIncorrectException;
import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;

import com.infy.WikiDocsProject.Utility.ArticleBuilder;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
	private final ArticleRepository articleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository, ArticleRepository articleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.articleRepository = articleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * @name findUserByEmail
	 * @Desciption Find user of given email
	 * @param email
	 * @return user object
	 */
	public User findUserByEmail(String email) throws Exception{
		// Called findUserByEmail() from userRepository class to find user of given name
		Optional<User> optionalUser = userRepository.findUserByEmail(email);
		// if user is present
		if(optionalUser.isPresent()){
			return optionalUser.get();
		}
		else{
			throw new UserNotFoundException();
		}
	}

	public User findUserByEmailAndPassword(String email, String password) throws Exception{
		Optional<User> optionalUser = userRepository.findUserByEmail(email);
		if(optionalUser.isPresent()){
			if(bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())){
				return optionalUser.get();
			}
			else{
				throw new PasswordIncorrectException();
			}
		}
		else{
			throw new UserNotFoundException();
		}
	}

	/**
	 * @name createArticleByEmail
	 * @Desciption Create new article with given emailId and channelId
	 * @param emailId
	 * @param channelId
	 * @return article object
	 */
	public Article createArticleByEmail(String emailId, String channelId) throws Exception{
		// User object declared
		User user;
		// List of article declared
		List<Article> articles;

		try {
			// call findUserByName to find user of given name
			// receive back a user object
			user = findUserByEmail(emailId);
			// called findAllArticlesByUserId() from articleRepository with users email
			// receive back an article object
			articles = articleRepository.findAllArticlesByEmailId(emailId);
			
			// Declared new article object and
			// set with with the article builder with initial parameters
			Article newArticle = new ArticleBuilder()
					.id(new ObjectId())
					.emailId(user.getEmail())
					.status(Status.INITIAL)
					.channelId(channelId)
					.editable(true)
					.build();
			// add new article to list
			articles.add(newArticle);
			// set article list to users articles
			user.setArticles(articles);
			// save article to database
			articleRepository.save(newArticle);
			// save user class to database
			userRepository.save(user);
			// return new article object
			return newArticle;
		}
		catch(UserNotFoundException e){
			// throw User Not Found Exception
			throw new UserNotFoundException();
		}
	}
}

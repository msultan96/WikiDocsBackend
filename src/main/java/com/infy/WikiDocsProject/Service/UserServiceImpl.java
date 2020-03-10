package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;

import com.infy.WikiDocsProject.Utility.ArticleBuilder;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	// Objects declarations
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;

	/**
	 * Constructor
	 * @param userRepository
	 * @param articleRepository
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
		this.userRepository = userRepository;
		this.articleRepository = articleRepository;
	}

	/**
	 * @name findUserByName
	 * @Desciption Find user of given name
	 * @param name
	 * @return user object
	 */
	public User findUserByName(String name) throws Exception{
		// Called findUserByName() from userRepository class to find user of given name
		Optional<User> optionalUser = userRepository.findUserByName(name);
		// if user is present
		if(optionalUser.isPresent()){
			// return user
			return optionalUser.get();
		}
		else{
			// else throw User Not Found Exception
			throw new UserNotFoundException();
		}
	}
	
	/**
	 * @name createArticleByUser
	 * @Desciption Create new article with given user name and channelId
	 * @param name
	 * @param channelId
	 * @return article object
	 */
	public Article createArticleByUser(String name, String channelId) throws Exception{
		// User object declared
		User user;
		// List of article declared
		List<Article> articles;

		try {
			// call findUserByName to find user of given name
			// receive back a user object
			user = findUserByName(name);
			// called findAllArticlesByUserId() from articleRepository with user's id
			// receive back an article object
			articles = articleRepository.findAllArticlesByUserId(user.getId());
			
			// Declared new article object and set with with an article build and auto generated params
			Article newArticle = new ArticleBuilder()
					//auto generated params
					.id(new ObjectId())
					.userId(user.getId())
					.status(Status.INITIAL)
					.channelId(channelId)
					.editable(true)
					.build();
			// add new article
			articles.add(newArticle);
			// set article to user
			user.setArticles(articles);
			// save article from articleRepository class to database
			articleRepository.save(newArticle);
			// save user from userRepository class to database
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

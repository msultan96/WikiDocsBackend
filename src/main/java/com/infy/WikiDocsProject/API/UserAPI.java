package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

/**
 * 
 * User API Controller Class
 *
 */
@RestController
@CrossOrigin
@RequestMapping("UserAPI")
public class UserAPI {

	private final UserService userService;
	private final ArticleService articleService;
	private final Environment environment;

	static Logger logger = LogManager.getLogger(UserAPI.class);

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public UserAPI(UserService userService, ArticleService articleService, Environment environment) {
		this.userService = userService;
		this.articleService = articleService;
		this.environment = environment;
	}

	/**
	 * Method name: login
	 * @param user object sent from frontend
	 * @return user object with updated information
	 * @throws Exception
	 */
	// @PostMapping to expose endpoint
	// and to send/retrieve information securely
	@PostMapping("login")
	public ResponseEntity<User> loginUser(@RequestBody User user) throws Exception{
		try{
			logger.info("USER TRYING TO LOGIN, VALIDATING CREDENTIALS, EMAIL ID ENTERED: " + user.getEmail());
			// Called findUserByEmail() from userService class to find user
			//  with given email and provided password
			User returnedUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
			logger.info("USER FOUND, RETRIEVING USER ARTICLES");
			// Called getAllArticleByUser() from articleService class to find all articles of user by the email
			List<Article> articleList = articleService.getAllArticlesByEmailId(returnedUser.getEmail());
			// Set user's articles with list of articles from above.
			user.setArticles(articleList);
			// return user object
			logger.info("USER LOGIN SUCCESS: " + returnedUser.getEmail());
			return new ResponseEntity<User>(returnedUser, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			logger.error("USER CREDENTIALS COULD NOT BE VALIDATED: " + user.getEmail());
			logger.error(environment.getProperty((e.getMessage())).toUpperCase());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@PostMapping("register")
	public ResponseEntity<User> register(@RequestBody User user) throws Exception{
		try{

			User returnedUser = userService.register(user);
			return new ResponseEntity<User>(returnedUser, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * Method name: getUsersArticles
	 * @param email
	 * @return List of articles
	 * @throws Exception
	 */
	// @GetMapping to expose API endpoint
	@GetMapping("getUsersArticles/{email:.+}")
	public ResponseEntity<List<Article>> getUsersArticles(@PathVariable String email) throws Exception{
		try{
			logger.info("GETTING ARTICLES BELONGING TO USER WITH EMAIL: " + email);;
			// Called getAllArticleByUser() from articleService class to find all article of user by that "name"
			List<Article> articles = articleService.getAllArticlesByEmailId(email);
			// Return list of articles
			logger.info("RETRIEVED ARTICLES BELONGING TO USER WITH EMAIL: " + email);
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
		catch(Exception e){
			logger.error("USER COULD NOT BE LOCATED " + email);
			logger.error(environment.getProperty(e.getMessage().toUpperCase()));
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
}

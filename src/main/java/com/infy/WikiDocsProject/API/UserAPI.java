package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 
 * User API Controller Class
 *
 */
@RestController
@RequestMapping("UserAPI")
@CrossOrigin
public class UserAPI {

	private final UserService userService;
	private final ArticleService articleService;
	private final PasswordEncoder passwordEncoder;
	private final Environment environment;

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public UserAPI(UserService userService, ArticleService articleService, PasswordEncoder passwordEncoder, Environment environment) {
		this.userService = userService;
		this.articleService = articleService;
		this.passwordEncoder = passwordEncoder;
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
			// Called findUserByEmail() from userService class to find user
			//  with given email and provided password
			System.out.println(user.getPassword());
			User returnedUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
			// Called getAllArticleByUser() from articleService class to find all articles of user by the email
			List<Article> articleList = articleService.getAllArticlesByEmail(returnedUser.getEmail());
			// Set user's articles with list of articles from above.
			user.setArticles(articleList);
			// return user object
			return new ResponseEntity<User>(returnedUser, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
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
			// Called getAllArticleByUser() from articleService class to find all article of user by that "name"
			List<Article> articles = articleService.getAllArticlesByEmail(email);
			// Return list of articles
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * Method name: createNewArticle
	 * @param email
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	// @GetMapping to expose API endpoint
	@PostMapping("createNewArticle/{email}/{channelId}")
	public ResponseEntity<Article> createNewArticle(@PathVariable String email, @PathVariable String channelId) throws Exception {
		try{
			// Called createArticleByUser() from userService class to create a new article with name and channelId
			Article article = userService.createArticleByEmail(email, channelId);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
}

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 
 * User API Controller Class
 *
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserAPI {

	// Objects declaration
	private final UserService userService;
	private final ArticleService articleService;
	private final Environment environment;

	// Contructor with Autowired objects assigned
	@Autowired
	public UserAPI(UserService userService, ArticleService articleService, Environment environment) {
		// Autowired objects assigned to local objects
		this.userService = userService;
		this.articleService = articleService;
		this.environment = environment;
	}

	/**
	 * Method name: loginUser
	 * @param name
	 * @return user object
	 * @throws Exception
	 */
	
	// @GetMapping to retrieve info
	// "login/{name}" - URL link to this particular method.
	@GetMapping("login/{name}")
	public ResponseEntity<User> loginUser(@PathVariable String name) throws Exception{
		try{
			// Called findUserByName() from userService classs to find user with that "name"
			User user = userService.findUserByName(name);
			// Called getAllArticleByUser() from articleService class to find all articles of user by at name has
			// Receive back a list of articles
			List<Article> articleList = articleService.getAllArticlesByUser(name);
			// Set user's articles with list of articles from above.
			user.setArticles(articleList);
			// return user object
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	
	/**
	 * Method name: getUsersArticles
	 * @param name
	 * @return List of articles
	 * @throws Exception
	 */
	// @GetMapping to retrieve info
	// "getUsersArticles/{name}" - URL link to this particular method.
	@GetMapping("getUsersArticles/{name}")
	public ResponseEntity<List<Article>> getUsersArticles(@PathVariable String name) throws Exception{
		try{
			// Called getAllArticleByUser() from articleService class to find all article of user by that "name"
			// Receive a  list of articles as result
			List<Article> articles = articleService.getAllArticlesByUser(name);
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
	 * @param name
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	// @PostMapping post up info
	// "createNewArticle/{name}/{channelId}" - URL link to this particular method.
	@PostMapping("createNewArticle/{name}/{channelId}")
	public ResponseEntity<Article> createNewArticle(@PathVariable String name, @PathVariable String channelId) throws Exception {
		try{
			// Called createArticleByUser() from userService class to create a new article with name and channelId
			// Receive back an article object
			Article article = userService.createArticleByUser(name, channelId);
			// Return previous article object
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
}

package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 
 * User API Controller Class
 *
 */
@SuppressWarnings("UnnecessaryLocalVariable")
@RestController
@CrossOrigin
@RequestMapping("UserAPI")
public class UserAPI {

	private final UserService userService;
	private final ArticleService articleService;

	static Logger logger = LogManager.getLogger(UserAPI.class);

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public UserAPI(UserService userService, ArticleService articleService) {
		this.userService = userService;
		this.articleService = articleService;
	}

	/**
	 * Method name: login
	 * @param user object sent from frontend
	 * @return user object with updated information
	 */
	@PostMapping("login")
	@ResponseBody
	public User loginUser(@RequestBody User user){
		logger.info("USER TRYING TO LOGIN, VALIDATING CREDENTIALS, EMAIL ID ENTERED: " + user.getEmail());
		// Called findUserByEmail() from userService class to find user
		//  with given email and provided password
		User returnedUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
		logger.info("USER FOUND, RETRIEVING USER ARTICLES");
		// Called getAllArticleByUser() from articleService class to find all articles of user by the email
		List<Article> articleList = articleService.getAllArticlesByEmailId(returnedUser.getEmail());
		// Set user's articles with list of articles from above.
		user.setArticles(articleList);
		// return user object
		logger.info("USER LOGIN SUCCESS: " + returnedUser.getEmail());
		return returnedUser;
	}

	@PostMapping("register")
	@ResponseBody
	public User register(@RequestBody User user){
		User returnedUser = userService.register(user);
		return returnedUser;
	}

	/**
	 * Method name: getUsersArticles
	 * @param email email used as id to retrieve articles
	 * @return List of articles
	 */
	@GetMapping("getUsersArticles/{email:.+}")
	@ResponseBody
	public List<Article> getUsersArticles(@PathVariable String email){
		logger.info("GETTING ARTICLES BELONGING TO USER WITH EMAIL: " + email);;
		// Called getAllArticleByEmail() from articleService class to find all article of user by their email
		List<Article> articles = articleService.getAllArticlesByEmailId(email);
		// Return list of articles
		logger.info("RETRIEVED ARTICLES BELONGING TO USER WITH EMAIL: " + email);
		return articles;
	}
}

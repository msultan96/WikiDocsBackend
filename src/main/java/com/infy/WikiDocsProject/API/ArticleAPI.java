package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Service.ArticleService;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * Article API Controller Class
 *
 */

@RestController
@RequestMapping("ArticleAPI")
@CrossOrigin
public class ArticleAPI {

	private final ArticleService articleService;
	private final Environment environment;

	//Constructor using constructor injection
	@Autowired
	public ArticleAPI(ArticleService articleService, Environment environment) {
		this.articleService = articleService;
		this.environment = environment;
	}

	/**
	 * @name GetAllArticleByEmail()
	 * @param email
	 * @Description get all article of given user via email
	 * @return list of articles in a response entity
	 * @throws Exception
	 */
	// @GetMapping to expose API endpoint
	@GetMapping("getAllArticlesByEmail/{email:.+}")
	public ResponseEntity<List<Article>> getAllArticlesByEmail(@PathVariable String email) throws Exception {
		try {
			// call getAllArticlesByEmail() from articleService class to find  articles of user with email.
			// receive back  a list of articles
			List<Article> articles = articleService.getAllArticlesByEmailId(email);
			// return a list of articles of that user has.
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * @name getApprovedArticles()
	 * @Description get approved articles across all users
	 * @return List of approved articles across all users
	 */
	// @GetMapping to expose API endpoint
	@GetMapping("getApprovedArticles")
	public ResponseEntity<List<Article>> getApprovedArticles(){
		// Called getApprovedArticles() from articleService class to find a list of articles with approved status
		// receive back a list of approved articles
		List<Article> approvedArticles = articleService.getApprovedArticles();
		// Return list of approved articles
		return new ResponseEntity<List<Article>>(approvedArticles, HttpStatus.OK);
	}

	/**
	 * @name getBetaArticles()
	 * @Description get all beta articles
	 * @return List of beta articles
	 */
	// @GetMapping to expose API endpoint
	@GetMapping("getBetaArticles")
	public ResponseEntity<List<Article>> getBetaArticles(){
		// Called getBetaArticles() from articleService class to find a list of articles with beta status
		// Receive back a list of beta articles
		List<Article> betaArticles = articleService.getBetaArticles();
		// Return a list of beta articles
		return new ResponseEntity<List<Article>>(betaArticles, HttpStatus.OK);
	}

	@GetMapping("getAllApprovedArticlesByEmail/{email:.+}")
	public ResponseEntity<List<Article>> getAllApprovedArticles(@PathVariable String email) throws Exception{
		try{
			List<Article> approvedArticles = articleService.getAllApprovedArticlesByEmailId(email);
			return new ResponseEntity<List<Article>>(approvedArticles, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@GetMapping("getAllBetaArticlesByEmail/{email:.+}")
	public ResponseEntity<List<Article>> getAllBetaArticlesByEmail(@PathVariable String email) throws Exception{
		try{
			List<Article> betaArticles = articleService.getAllBetaArticlesByEmailId(email);
			return new ResponseEntity<List<Article>>(betaArticles, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@GetMapping("getAllInitialArticlesByEmail/{email:.+}")
	public ResponseEntity<List<Article>> getAllInitialArticlesByEmail(@PathVariable String email) throws Exception{
		try{
			List<Article> initialArticles = articleService.getAllInitialArticlesByEmailId(email);
			return new ResponseEntity<List<Article>>(initialArticles, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@GetMapping("getAllRejectedArticlesByEmail/{email:.+}")
	public ResponseEntity<List<Article>> getAllRejectedArticlesByEmail(@PathVariable String email) throws Exception{
		try{
			List<Article> rejectedArticles = articleService.getAllRejectedArticlesByEmailId(email);
			return new ResponseEntity<List<Article>>(rejectedArticles, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@GetMapping("getAllDiscardedArticlesByEmail/{email:.+}")
	public ResponseEntity<List<Article>> getAllDiscardedArticlesByEmail(@PathVariable String email) throws Exception{
		try{
			List<Article> discardedArticles = articleService.getAllDiscardedArticlesByEmailId(email);
			return new ResponseEntity<List<Article>>(discardedArticles, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * @name submitArticleForApproval
	 * @Description submit an article for approval
	 * @param article sent from frontend
	 * @return a submitted article object
	 * @throws Exception
	 */
	// @PostMapping to expose API endpoint
	// Post secures all information of the article
	@PostMapping("submitArticleForApproval")
	public ResponseEntity<Article> submitArticleForApproval(@RequestBody Article article) throws Exception {
		try {
			// Called submitArticle(channelId) with channelId from articleService class to submit an article
			Article submittedArticle = articleService.submitArticle(article.getChannelId());
			// Return the submitted article
			return new ResponseEntity<Article>(submittedArticle, HttpStatus.OK);
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * @name approveArticle
	 * @Description approve an article
	 * @param article sent from frontend
	 * @return an approved article object
	 * @throws Exception
	 */
	// @PostMapping to expose API endpoint
	@PostMapping("approveArticle")
	public ResponseEntity<Article> approveArticle(@RequestBody Article article) throws Exception {
		try{
			// Called approveArticle with channelId from articleService class to approve an article.
			Article returnedArticle = articleService.approveArticle(article.getChannelId());
			return new ResponseEntity<Article>(returnedArticle, HttpStatus.OK);
		}
		catch(Exception e){
			// find exception message from environmnet.getProperty()
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * @name rejectArticle
	 * @Description reject an article
	 * @param article sent from frontend
	 * @return rejected article object
	 * @throws Exception
	 */
	// @PostMapping to post up info
	@PostMapping("rejectArticle")
	public ResponseEntity<Article> rejectArticle(@RequestBody Article article) throws Exception{
		try{
			// Called rejectArticle with channelId from articleService to reject an article.
			Article rejectedArticle = articleService.rejectArticle(article.getChannelId());
			return new ResponseEntity<Article>(rejectedArticle, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@PostMapping("getArticleByChannelId")
	public ResponseEntity<Article> getArticleByChannelId(@RequestParam String channelId) throws Exception{
		try{
			Article article = articleService.getArticleByChannelId(channelId);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * Method name: createNewArticle
	 * @param map
	 * @return
	 * @throws Exception
	 */
	// @GetMapping to expose API endpoint
	@PostMapping("createNewArticle")
	public ResponseEntity<Article> createNewArticle(@RequestBody Map<String, String> map) throws Exception {
		try{
			// Called createArticleByUser() from userService class to create a new article with name and channelId
			Article article = articleService.createArticleByEmail(map.get("email"), map.get("channelId"));
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

}

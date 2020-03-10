package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Service.ArticleService;
import java.util.List;
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
@RequestMapping("article")
@CrossOrigin
public class ArticleAPI {

	// Objects declarations
	private final ArticleService articleService;
	private final Environment environment;

	//Contructor with Autowired object assigned
	@Autowired
	public ArticleAPI(ArticleService articleService, Environment environment) {
		// Autowired object assigned
		this.articleService = articleService;
		this.environment = environment;
	}

	/**
	 * @name GetAllArticleByUser()
	 * @param name
	 * @Description get all article of given user
	 * @return list of articles in a response entity
	 * @throws Exception
	 */
	// @GetMapping to retrieve info
	// "getAllArticlesByUser/{name}" - URL link to this particular method
	@GetMapping("getAllArticlesByUser/{name}")
	public ResponseEntity<List<Article>> getAllArticlesByUser(@PathVariable String name) throws Exception{
		try{
			// call getAllArticlesByUser() from articleService class to find  articles of user with name.
			// receive back  a list of articles
			List<Article> articles = articleService.getAllArticlesByUser(name);
			// return a list of articles of that user has.
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
	
	/**
	 * @name getApprovedArticles()
	 * @Description get approved articles
	 * @return List of approved articles
	 */
	// @GetMapping to retrieve info
	// "getApprovedArticles" - URL link to this particular method.
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
	// @GetMapping to retrieve info
	// "getBetarticles" - URL link to this particular method.
	@GetMapping("getBetarticles")
	public ResponseEntity<List<Article>> getBetaArticles(){
		// Called getBetaArticles() from articleService class to find a list of articles with beta status
		// Receive back a list of beta articles
		List<Article> betaArticles = articleService.getBetaArticles();
		// Return a list of beta articles
		return new ResponseEntity<List<Article>>(betaArticles, HttpStatus.OK);
	}

	/**
	 * @name submitArticleForApproval
	 * @Description submit an article for approval
	 * @param channelId
	 * @return a submitted article object
	 * @throws Exception
	 */
	// @PostMapping to post up info
	// "submitArticleForApproval/{channelId}" - URL link to this particular method.
	@PostMapping("submitArticleForApproval/{channelId}")
	public ResponseEntity<Article> submitArticleForApproval(@PathVariable String channelId) throws Exception{
		try{
			// Called submitArticle(channelId) with channelId from articleService class to submit an article
			// Receive an submitted article 	
			Article article = articleService.submitArticle(channelId);
			// Return the submitted article
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * @name approveArticle
	 * @Description approve an article given the channelID of the article
	 * @param channelId
	 * @return an approved article object
	 * @throws Exception
	 */
	// @PostMapping to post up info
	// "approveArticle/{channelId}" - URL link to this particular method.
	@PostMapping("approveArticle/{channelId}")
	public ResponseEntity<Article> approveArticle(@PathVariable String channelId) throws Exception {
		try{
			// Called approveArticle with channelId from articleService class to approve an article.
			// receive an approve article
			Article article = articleService.approveArticle(channelId);
			// Return an approved article
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	/**
	 * @name rejectArticle
	 * @Description reject an article given the channelID of the article
	 * @param channelId
	 * @return rejected article object
	 * @throws Exception
	 */
	// @PostMapping to post up info
	// "rejectArticle/{channelId}" - URL link to this particular method.
	@PostMapping("rejectArticle/{channelId}")
	public ResponseEntity<Article> rejectArticle(@PathVariable String channelId) throws Exception{
		try{
			// Called rejectArticle with channelId from articleService to reject an article.
			// receive back a rejected article
			Article article = articleService.rejectArticle(channelId);
			// Return a rejected article
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			// throw exception of user with that name is not found
			// find exception message from environmnet.getProperty().
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

}

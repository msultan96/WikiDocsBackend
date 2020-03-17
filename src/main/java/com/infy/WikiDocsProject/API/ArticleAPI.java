package com.infy.WikiDocsProject.API;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Service.ArticleService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * Article API Controller Class
 *
 */

@SuppressWarnings("UnnecessaryLocalVariable")
@RestController
@CrossOrigin
@RequestMapping("ArticleAPI")
public class ArticleAPI {

	private final ArticleService articleService;

	static Logger logger = LogManager.getLogger(ArticleAPI.class);

	/*
	Constructor using constructor injection
	 */
	@Autowired
	public ArticleAPI(ArticleService articleService) {
		this.articleService = articleService;
	}

	/**
	 * get all beta articles
	 * @return List of beta articles
	 */
	@GetMapping("getBetaArticles")
	@ResponseBody
	public List<Article> getBetaArticles(){
		logger.info("GETTING ALL BETA ARTICLES");
		// Called getBetaArticles() from articleService class to find a list of articles with beta status
		// Receive back a list of beta articles
		List<Article> betaArticles = articleService.getBetaArticles();
		logger.info("RETRIEVED ALL BETA ARTICLES");
		// Return a list of beta articles
		return betaArticles;
	}

	/**
	 * get all article of given user via email
	 * @param email email provided used for lookup
	 * @return list of articles in a response entity
	 */
	@GetMapping("getAllArticlesByEmail/{email:.+}")
	@ResponseBody
	public List<Article> getAllArticlesByEmail(@PathVariable String email) {
		logger.info("GETTING ALL ARTICLES BELONGING TO USER WITH EMAIL: " + email);
		// call getAllArticlesByEmail() from articleService class to find  articles of user with email.
		// receive back  a list of articles
		List<Article> articles = articleService.getAllArticlesByEmailId(email);
		logger.info("RETRIEVED ARTICLES BELONGING TO USER WITH EMAIL : " + email);
		// return a list of articles of that user has.
		return articles;
	}

	/**
	 * get approved articles across all users
	 * @return List of approved articles across all users
	 */
	@GetMapping("getApprovedArticles")
	@ResponseBody
	public List<Article> getApprovedArticles(){
		logger.info("GETTING ALL APPROVED ARTICLES");
		// Called getApprovedArticles() from articleService class to find a list of articles with approved status
		// receive back a list of approved articles
		List<Article> approvedArticles = articleService.getApprovedArticles();
		logger.info("RETRIEVED ALL APPROVED ARTICLES");
		// Return list of approved articles
		return approvedArticles;
	}

	@GetMapping("getAllApprovedArticlesByEmail/{email:.+}")
	@ResponseBody
	public List<Article> getAllApprovedArticles(@PathVariable String email){
		logger.info("GETTING ALL APPROVED ARTICLES BELONG TO USER WITH EMAIL: " + email);
		List<Article> approvedArticles = articleService.getAllApprovedArticlesByEmailId(email);
		logger.info("RETRIEVED ALL APPROVED ARTICLES BELONG TO USER WITH EMAIL: " + email);
		return approvedArticles;
	}

	@GetMapping("getAllBetaArticlesByEmail/{email:.+}")
	@ResponseBody
	public List<Article> getAllBetaArticlesByEmail(@PathVariable String email){
		logger.info("GETTING ALL BETA ARTICLES BELONG TO USER WITH EMAIL: " + email);
		List<Article> betaArticles = articleService.getAllBetaArticlesByEmailId(email);
		logger.info("RETRIEVED ALL APPROVED ARTICLES BELONG TO USER WITH EMAIL: " + email);
		return betaArticles;
	}

	@GetMapping("getAllInitialArticlesByEmail/{email:.+}")
	@ResponseBody
	public List<Article> getAllInitialArticlesByEmail(@PathVariable String email){
		logger.info("GETTING ALL INITIAL ARTICLES BELONG TO USER WITH EMAIL: " + email);
		List<Article> initialArticles = articleService.getAllInitialArticlesByEmailId(email);
		logger.info("GETTING ALL INITIAL ARTICLES BELONG TO USER WITH EMAIL: " + email);
		return initialArticles;
	}

	@GetMapping("getAllRejectedArticlesByEmail/{email:.+}")
	@ResponseBody
	public List<Article> getAllRejectedArticlesByEmail(@PathVariable String email){
		logger.info("GETTING ALL REJECTED ARTICLES BELONG TO USER WITH EMAIL: " + email);
		List<Article> rejectedArticles = articleService.getAllRejectedArticlesByEmailId(email);
		logger.info("GETTING ALL REJECTED ARTICLES BELONG TO USER WITH EMAIL: " + email);
		return rejectedArticles;
	}

	@GetMapping("getAllDiscardedArticlesByEmail/{email:.+}")
	@ResponseBody
	public List<Article> getAllDiscardedArticlesByEmail(@PathVariable String email){
		logger.info("GETTING ALL DISCARDED ARTICLES BELONG TO USER WITH EMAIL: " + email);
		List<Article> discardedArticles = articleService.getAllDiscardedArticlesByEmailId(email);
		logger.info("GETTING ALL DISCARDED ARTICLES BELONG TO USER WITH EMAIL: " + email);
		return discardedArticles;
	}

	/**
	 * submit an article for approval
	 * @param article sent from frontend
	 * @return a submitted article object
	 */
	@PostMapping("submitArticleForApproval")
	@ResponseBody
	public Article submitArticleForApproval(@RequestBody Article article) {
		logger.info("SUBMITTING ARTICLE FOR APPROVAL FOR USER WITH EMAIL " + article.getEmailId());
		// Called submitArticle(channelId) with channelId from articleService class to submit an article
		Article submittedArticle = articleService.submitArticle(article.getId());
		logger.info("ARTICLE SUBMITTED FOR APPROVAL FOR USER WITH EMAIL " + article.getEmailId());
		// Return the submitted article
		return submittedArticle;
	}

	/**
	 * approve an article
	 * @param article sent from frontend
	 * @return an approved article object
	 */
	@PostMapping("approveArticle")
	@ResponseBody
	public Article approveArticle(@RequestBody Article article) {
		logger.info("APPROVING ARTICLE SUBMITTED BY USER WITH EMAIL " + article.getEmailId());
		// Called approveArticle with channelId from articleService class to approve an article.
		Article returnedArticle = articleService.approveArticle(article.getId());
		logger.info("APPROVED ARTICLE SUBMITTED BY USER WITH EMAIL " + article.getEmailId());
		return returnedArticle;
	}

	/**
	 * reject an article
	 * @param article sent from frontend
	 * @return rejected article object
	 */
	@PostMapping("rejectArticle")
	@ResponseBody
	public Article rejectArticle(@RequestBody Article article){
		logger.info("REJECTING ARTICLE SUBMITTED BY USER WITH EMAIL " + article.getEmailId());
		// Called rejectArticle with channelId from articleService to reject an article.
		Article rejectedArticle = articleService.rejectArticle(article.getId());
		logger.info("REJECTED ARTICLE SUBMITTED BY USER WITH EMAIL " + article.getEmailId());
		return rejectedArticle;
	}

	@PostMapping("getArticleById")
	@ResponseBody
	public Article getArticleByChannelId(@RequestParam String id){
		Article article = articleService.findById(id);
		return article;
	}

	@PostMapping("createNewArticle")
	@ResponseBody
	public Article createNewArticle(@RequestBody Map<String, String> map) {
		logger.info("CREATING NEW ARTICLE FOR USER WITH EMAIL " + map.get("email"));
		// Called createArticleByUser() from userService class to create a new article with name
		Article article = articleService.createArticleByEmail(map.get("email"));
		logger.info("CREATED NEW ARTICLE FOR USER WITH EMAIL " + map.get("email"));
		return article;
	}

	@PostMapping("saveArticle")
	@ResponseBody
	public Article saveArticle(@RequestBody String etherPadId){
		Article returnedArticle = articleService.saveArticle(etherPadId);
		return returnedArticle;
	}

	@PostMapping("getEtherPadUrl")
	@ResponseBody
	public String getEtherPadUrl(@RequestBody String etherPadId){
		String etherPadUrl = articleService.getEtherPadUrl(etherPadId);
		return etherPadUrl;
	}
}

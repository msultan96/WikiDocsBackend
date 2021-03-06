package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.enums.Status;
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

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public ArticleAPI(ArticleService articleService) {
		this.articleService = articleService;
	}

	/**
	 * get all approved articles - for admins
	 * @return List of approved articles across all users
	 */
	@GetMapping("getAllApprovedArticles/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllApprovedArticles(@PathVariable int pageNumber, @PathVariable int pageSize){

		// request Article Service for paginated approved articles
		List<Article> approvedArticles = articleService.getAllArticlesByStatus(Status.APPROVED, pageNumber, pageSize);

		return approvedArticles;
	}

	/**
	 * get all beta articles - for admins
	 * @return List of beta articles across all users
	 */
	@GetMapping("getAllBetaArticles/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllBetaArticles(@PathVariable int pageNumber, @PathVariable int pageSize){

		// request Article Service for paginated beta articles
		List<Article> betaArticles = articleService.getAllArticlesByStatus(Status.BETA, pageNumber, pageSize);

		return betaArticles;
	}

	/**
	 * get all article of given user via email
	 * @param email email provided used for lookup
	 * @param pageNumber page number for infinite scroll
	 * @param pageSize page size
	 * @return the articles being requested
	 */
	@GetMapping("getAllArticlesByEmail/{email:.+}/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllArticlesByEmail(@PathVariable String email, @PathVariable int pageNumber, @PathVariable int pageSize) {

		// request Article Service for paginated articles by given email
		List<Article> articles = articleService.getAllArticlesByEmailId(email, pageNumber, pageSize);

		return articles;
	}

	/**
	 * get all approved articles of given user via email
	 * @param email email of user provided for lookup
	 * @param pageNumber page number for infinite scrolling
	 * @param pageSize page sie
	 * @return all approved articles by email
	 */
	@GetMapping("getAllApprovedArticlesByEmail/{email:.+}/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllApprovedArticlesByEmail(@PathVariable String email, @PathVariable int pageNumber, @PathVariable int pageSize){

		// request Article Service for paginated approved articles by given email
		List<Article> approvedArticles = articleService.getAllArticlesByEmailIdAndStatus(email, Status.APPROVED, pageNumber, pageSize);

		return approvedArticles;
	}

	/**
	 * get all beta articles of given user via email
	 * @see ArticleAPI#getAllApprovedArticlesByEmail
	 */
	@GetMapping("getAllBetaArticlesByEmail/{email:.+}/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllBetaArticlesByEmail(@PathVariable String email, @PathVariable int pageNumber, @PathVariable int pageSize){

		// request Article Service for paginated beta articles by given email
		List<Article> betaArticles = articleService.getAllArticlesByEmailIdAndStatus(email, Status.BETA, pageNumber, pageSize);

		return betaArticles;
	}

	/**
	 * get all initial articles of given user via email
	 * @see ArticleAPI#getAllApprovedArticlesByEmail
	 */
	@GetMapping("getAllInitialArticlesByEmail/{email:.+}/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllInitialArticlesByEmail(@PathVariable String email, @PathVariable int pageNumber, @PathVariable int pageSize){

		// request Article Service for paginated initial articles by given email
		List<Article> initialArticles = articleService.getAllArticlesByEmailIdAndStatus(email, Status.INITIAL, pageNumber, pageSize);

		return initialArticles;
	}

	/**
	 * get all rejected articles of given user via email
	 * @see ArticleAPI#getAllApprovedArticlesByEmail
	 */
	@GetMapping("getAllRejectedArticlesByEmail/{email:.+}/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllRejectedArticlesByEmail(@PathVariable String email, @PathVariable int pageNumber, @PathVariable int pageSize){

		// request Article Service for paginated rejected articles by given email
		List<Article> rejectedArticles = articleService.getAllArticlesByEmailIdAndStatus(email, Status.REJECTED, pageNumber, pageSize);

		return rejectedArticles;
	}

	/**
	 * get all discarded articles of given user via email
	 * @see ArticleAPI#getAllApprovedArticlesByEmail
	 */
	@GetMapping("getAllDiscardedArticlesByEmail/{email:.+}/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllDiscardedArticlesByEmail(@PathVariable String email, @PathVariable int pageNumber, @PathVariable int pageSize){

		// request Article Service for paginated discarded articles by given email
		List<Article> discardedArticles = articleService.getAllArticlesByEmailIdAndStatus(email, Status.DISCARDED, pageNumber, pageSize);

		return discardedArticles;
	}

	/**
	 * get all articles a user has been invited to
	 * @param email email provided used for lookup
	 * @param pageNumber page number for infinite scroll
	 * @param pageSize page size
	 * @return the articles being
	 */
	@GetMapping("getAllInvitedArticlesByEmail/{email:.+}/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<Article> getAllInvitedArticlesByEmail(@PathVariable String email, @PathVariable int pageNumber, @PathVariable int pageSize){
		List<Article> articles = articleService.getAllInvitedArticlesByEmail(email, pageNumber, pageSize);
		return articles;
	}

	/**
	 * submit an article for approval
	 * @param articleId id of the article
	 * @return the article requested
	 */
	@PostMapping("submitArticleForApproval")
	@ResponseBody
	public Article submitArticleForApproval(@RequestBody String articleId) {
		Article submittedArticle = articleService.submitArticle(articleId);
		return submittedArticle;
	}

	/**
	 * approve an article
	 * @param articleId id of the article
	 * @return the article requested
	 */
	@GetMapping("approveArticle/{articleId}")
	@ResponseBody
	public Article approveArticle(@PathVariable String articleId) {
		Article returnedArticle = articleService.approveArticle(articleId);
		return returnedArticle;
	}

	/**
	 * reject an article
	 * @param articleId id of the article
	 * @return the article requested
	 */
	@GetMapping("rejectArticle/{articleId}")
	@ResponseBody
	public Article rejectArticle(@PathVariable String articleId){
		Article rejectedArticle = articleService.rejectArticle(articleId);
		return rejectedArticle;
	}

	/**
	 * retrieve the article being requested
	 * @param articleId id of the article
	 * @return the article requested
	 */
	@PostMapping("getArticleById")
	@ResponseBody
	public Article getArticleById(@RequestParam String articleId){
		Article article = articleService.findById(articleId);
		return article;
	}

	/**
	 * creates a new article with the given name
	 * and adds that article to the list of articles
	 * of the given email
	 * @param map contains email and article name
	 * @return newly created article
	 */
	@PostMapping("createNewArticle")
	@ResponseBody
	public Article createNewArticle(@RequestBody Map<String, String> map) {

		// Called createArticleByUser() from userService class to create a new article with name
		Article article = articleService.createArticleByEmail(map.get("email"), map.get("articleName"));

		return article;
	}

	/**
	 * saves the article
	 * @param etherPadId id the of etherPad to retrieve and save the content
	 * @return saved article
	 */
	@PostMapping("saveArticle")
	@ResponseBody
	public Article saveArticle(@RequestBody String etherPadId){
		Article returnedArticle = articleService.saveArticle(etherPadId);
		return returnedArticle;
	}

	/**
	 * generates the appropriate url for the
	 * frontend EtherPad instance
	 * @param etherPadId
	 * @return the url
	 */
	@PostMapping("getEtherPadUrl")
	@ResponseBody
	public String getEtherPadUrl(@RequestBody String etherPadId) {
		String etherPadUrl = articleService.getEtherPadUrl(etherPadId);
		return etherPadUrl;
	}

	/**
	 * invites a user with the given email
	 * to collaborate on the given article
	 * @param map contains invitee email and article id to collaborate on
	 * @return name of the invitee to send to front end
	 */
	@PostMapping("inviteUserToCollaborateByEmail")
	@ResponseBody
	public String inviteUserToCollaborateByEmail(@RequestBody Map<String, String> map){
		String inviteeName = articleService.inviteUserToCollaborateByEmail(map);
		return inviteeName;
	}
}

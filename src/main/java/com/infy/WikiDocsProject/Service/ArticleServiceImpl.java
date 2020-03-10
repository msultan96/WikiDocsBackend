package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.*;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * Article Service Implementations
 * @Service - declared ArticleService class as a Service class
 *
 */
@Service(value="articleService")
public class ArticleServiceImpl implements ArticleService {

	// Objects declarations
	private final UserService userService;
	private final ArticleRepository articleRepository;

	/**
	 * Constructor
	 * @param userService
	 * @param articleRepository
	 */
	@Autowired
	public ArticleServiceImpl(UserService userService, ArticleRepository articleRepository) {
		// Initialize local objects with params
		this.userService = userService;
		this.articleRepository = articleRepository;
	}

	/**
	 * @name getAllArticlesByUser
	 * @Desciption Get all article by user's name
	 * @param name
	 * @return List of articles
	 */
	public List<Article> getAllArticlesByUser(String name) throws Exception{
		// User object declared
		User user;
		try{
			// called findUserByName() from userService class to find user of given name
			// receive back a user object
			user = userService.findUserByName(name);
			// called findAllArticleByUserId() from articleRepository class to find all articles of given userId
			// receive back a list of articles
			List<Article> articles = articleRepository.findAllArticlesByUserId(user.getId());
			// return list of article
			return articles;
		}
		catch(UserNotFoundException e){
			// throw not found exception if article or user is not found
			throw new UserNotFoundException();
		}
	}

	/**
	 * @name: getApprovedArticles
	 * @Desciption Get all approved articles
	 * @return List of articles
	 */
	public List<Article> getApprovedArticles(){
		// called findArticlesByStatus() from articleRepository class to find article of given status APPROVED
		// receive back a list of articles
		List<Article> articles = articleRepository.findArticlesByStatus(Status.APPROVED);
		// return article list
		return articles;
	}
	/**
	 * @name: getBetaArticles
	 * @Desciption Get all beta articles
	 * @return List of articles
	 */
	public List<Article> getBetaArticles(){
		// called findArticlesByStatus() from articleRepository class to find article of given status BETA
		// receive back a list of articles
		List<Article> articles = articleRepository.findArticlesByStatus(Status.BETA);
		// return article list
		return articles;
	}
	/**
	 * @name: getArticleByChannelId
	 * @Desciption Get all articles of given channelId
	 * @param channelId
	 * @return article object
	 */	
	public Article getArticleByChannelId(String channelId) throws Exception{
		// called findArticleByChannelId() from articleRepository class to find article of given channelId
		// receive back an article object
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		// If article is present return article
		if(optionalArticle.isPresent()){
			return optionalArticle.get();
		}
		// else throw article not found exception
		else{
			throw new ArticleNotFoundException();
		}
	}

	/**
	 * @name: submitArticle
	 * @Desciption Submit an initial or beta or rejected article
	 * @param channelId
	 * @return article object
	 */	
	public Article submitArticle(String channelId) throws Exception{
		// called findArticleByChannelId() from articleRepository class to find article of given channelId
		// receive back an article object
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		// If article is present create new article object and assign article to it
		if(optionalArticle.isPresent()){
			Article article = optionalArticle.get();
			// If article's status is INITIAL or BETA or REJECTED
			if(article.getStatus() == Status.INITIAL
					|| article.getStatus() == Status.BETA
					|| article.getStatus() == Status.REJECTED) {
				// then set status to BETA inorder to submit
				article.setStatus(Status.BETA);
				/*
				TODO: Send an email to Administrator
				 */
				// Save article from articleRepository to database
				articleRepository.save(article);
				// return article object
				return article;
			}
			// If article's status is DISCARDED throw exception
			if(article.getStatus() == Status.DISCARDED){
				// Submitting Article Is Discarded Exception thrown
				throw new SubmittingArticleIsDiscardedException();
			}
			// If article's status is APPROVED throw exception
			if(article.getStatus() == Status.APPROVED) {
				// Submitting Article Is Approved Exception thrown
				throw new SubmittingArticleIsApprovedException();
			}
		}
		// Throw Article Not Found Exception
		throw new ArticleNotFoundException();
	}

	/**
	 * @name: approveArticle
	 * @Desciption Approve an article
	 * @param channelId
	 * @return article object
	 */	
	public Article approveArticle(String channelId) throws Exception{
		// called findArticleByChannelId() from articleRepository class to find article of given channelId
		// receive back an article object
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		// If article is present create new article object and assign article to it
		if(optionalArticle.isPresent()) {
			Article article = optionalArticle.get();
			// If article's status is BETA
			if(article.getStatus() == Status.BETA){
				// then set article status to APPROVED
				article.setStatus(Status.APPROVED);
				// Save article from articleRepository to database
				articleRepository.save(article);
				// return article object
				return article;
			}

			// If article status is REJECTED
			if(article.getStatus() == Status.REJECTED){
				// throw Approving Article Is Still Rejected Exception
				throw new ApprovingArticleIsStillRejectedException();
			}
			// If article status is INITIAL
			if(article.getStatus() == Status.INITIAL){
				// throw Approving Article Is Initial Exception
				throw new ApprovingArticleIsInitialException();
			}
			// If article status is APPROVED
			if(article.getStatus() == Status.APPROVED){
				// throw Approving Article Is Approved Exception
				throw new ApprovingArticleIsApprovedException();
			}
			// If article status is DISCARDED
			if(article.getStatus() == Status.DISCARDED){
				// throw Approving Article Is Discarded Exception
				throw new ApprovingArticleIsDiscardedException();
			}
		}
		// throw Article Not Found Exception
		throw new ArticleNotFoundException();
	}

	/**
	 * @name: rejectArticle
	 * @Desciption Reject an article
	 * @param channelId
	 * @return article object
	 */	
	public Article rejectArticle(String channelId) throws Exception{
		// called findArticleByChannelId() from articleRepository class to find article of given channelId
		// receive back an article object
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		// If article is present create new article object and assign article to it
		if(optionalArticle.isPresent()) {
			Article article = optionalArticle.get();
			
			// If article's status is BETA 
			if(article.getStatus() == Status.BETA){
				// then set article's status to REJECTED
				article.setStatus(Status.REJECTED);
				// Increase article rejected count by 1
				article.setRejectedCount(article.getRejectedCount() + 1);
				// If article's rejected count greater than 3, article status is set DISCARDED
				if (article.getRejectedCount() > 3) article.setStatus(Status.DISCARDED);
				// Save article from articleRepository to database
				articleRepository.save(article);
				// return article object
				return article;
			}
			// If article's status is INITIAL
			if(article.getStatus() == Status.INITIAL){
				// throw Rejecting Article Is Initial Exception
				throw new RejectingArticleIsInitialException();
			}
			// If article's status is APPROVED
			if(article.getStatus() == Status.APPROVED){
				// throw Rejecting Article Is Approved Exception
				throw new RejectingArticleIsApprovedException();
			}
			// If article's status is REJECTED
			if(article.getStatus() == Status.REJECTED){
				// throw Rejecting Article Is Still Rejected Exception
				throw new RejectingArticleIsStillRejectedException();
			}
			// If article's status is DISCARDED
			if(article.getStatus() == Status.DISCARDED){
				//throw Rejecting Article Is Discarded Exception
				throw new RejectingArticleIsDiscardedException();
			}
		}
		// throw Article Not Found Exception
		throw new ArticleNotFoundException();
	}
}

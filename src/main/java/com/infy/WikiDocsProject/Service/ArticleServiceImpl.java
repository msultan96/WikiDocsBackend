package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.*;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;
import java.util.Optional;

import net.gjerull.etherpad.client.EPLiteClient;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * Article Service Implementations
 *
 */
@SuppressWarnings("UnnecessaryLocalVariable")
@Service(value="articleService")
public class ArticleServiceImpl implements ArticleService {

	private final UserService userService;
	private final EPLiteClient epLiteClient;
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public ArticleServiceImpl(UserService userService, EPLiteClient epLiteClient,
							  ArticleRepository articleRepository, UserRepository userRepository) {
		this.userService = userService;
		this.epLiteClient = epLiteClient;
		this.articleRepository = articleRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Retrieves the list of articles of a user
	 *
	 * @param email Used to locate the user
	 * @return The list of articles of a user
	 */
	public List<Article> getAllArticlesByEmailId(String email) {
		// Call the userService's findByEmail method
		// To validate that the user with the given email exists
		userService.findByEmail(email);

		// call articleRepository's findAllArticleByEmailId() method
		// to find all articles of given user by email
		// and receive back a list of articles
		List<Article> articles = articleRepository.findAllArticlesByEmailId(email);
		return articles;
	}

	/**
	 * Retrieves the list of articles of a user
	 * with the status of APPROVED
	 *
	 * @param email Used to locate the user
	 * @return The list of approved articles of a user
	 * @throws UserNotFoundException If the email isn't found
	 */
	public List<Article> getAllApprovedArticlesByEmailId(String email) {
		// Call the userService's findByEmail method
		// To validate that the user with the given email exists
		userService.findByEmail(email);

		// call articleRepository's findAllArticlesByEmailIdAndStatus() method to
		// find all articles of given user by email and status APPROVED
		// and receive back the list of articles
        List<Article> articles = articleRepository.findAllArticlesByEmailIdAndStatus(email, Status.APPROVED);
        return articles;
    }

	/**
	 * Retrieves the list of articles of a user
	 * with the status of BETA
	 *
	 * @param email Used to locate the user
	 * @return the list of beta articles of a user
	 * @throws UserNotFoundException If the email isn't found
	 */
    public List<Article> getAllBetaArticlesByEmailId(String email)  {
		// Call the userService's findByEmail method
		// To validate that the user with the given email exists
		userService.findByEmail(email);

		// call articleRepository's findAllArticlesByEmailIdAndStatus() method to
		// find all articles of given user by email and status BETA
		// and receive back the list of articles
        List<Article> articles = articleRepository.findAllArticlesByEmailIdAndStatus(email, Status.BETA);
        return articles;
    }

	/**
	 * Retrieves the list of articles of a user
	 * with the status of INITIAL
	 *
	 * @param email Used to locate the user
	 * @return the list of initial articles of a user
	 * @throws UserNotFoundException If the email isn't found
	 */
    public List<Article> getAllInitialArticlesByEmailId(String email)  {
		// Call the userService's findByEmail method
		// To validate that the user with the given email exists
		userService.findByEmail(email);

		// call articleRepository's findAllArticlesByEmailIdAndStatus() method to
		// find all articles of given user by email and status INITIAL
		// and receive back the list of articles
        List<Article> articles = articleRepository.findAllArticlesByEmailIdAndStatus(email, Status.INITIAL);
        return articles;
    }

	/**
	 * Retrieves the list of articles of a user
	 * with the status of REJECTED
	 *
	 * @param email Used to locate the user
	 * @return the list of rejected articles of a user
	 * @throws UserNotFoundException If the email isn't found
	 */
    public List<Article> getAllRejectedArticlesByEmailId(String email) {
		// Call the userService's findByEmail method
		// To validate that the user with the given email exists
		userService.findByEmail(email);

		// call articleRepository's findAllArticlesByEmailIdAndStatus() method to
		// find all articles of given user by email and status REJECTED
		// and receive back the list of articles
		List<Article> articles = articleRepository.findAllArticlesByEmailIdAndStatus(email, Status.REJECTED);
		return articles;
    }

	/**
	 * Retrieves the list of articles of a user
	 * with the status of DISCARDED
	 *
	 * @param email Used to locate the user
	 * @return the list of discarded articles of a user
	 * @throws UserNotFoundException If the email isn't found
	 */
    public List<Article> getAllDiscardedArticlesByEmailId(String email) {
		// Call the userService's findByEmail method
		// To validate that the user with the given email exists
		userService.findByEmail(email);

		// call articleRepository's findAllArticlesByEmailIdAndStatus() method to
		// find all articles of given user by email and status DISCARDED
		// and receive back the list of articles
		List<Article> articles = articleRepository.findAllArticlesByEmailIdAndStatus(email, Status.DISCARDED);
		return articles;
    }

	/**
	 * Retrieves all APPROVED articles across all users
	 *
	 * @return the list of approved articles
	 */
	public List<Article> getApprovedArticles(){
		// called findArticlesByStatus() from articleRepository class to find article of given status APPROVED
		// receive back a list of articles
		List<Article> articles = articleRepository.findArticlesByStatus(Status.APPROVED);
		// return article list
		return articles;
	}

	/**
	 * Retrieves all BETA articles across all users
	 *
	 * @return the list of approved articles
	 */
	public List<Article> getBetaArticles(){
		// called findArticlesByStatus() from articleRepository class to find article of given status BETA
		// receive back a list of articles
		List<Article> articles = articleRepository.findArticlesByStatus(Status.BETA);
		// return article list
		return articles;
	}

	/**
	 * Retrieve an article with a specific id.
	 *
	 * @param id Used to locate the article
	 * @return the article found
	 * @throws ArticleNotFoundException If the id isn't associated with an article
	 */
	public Article findById(ObjectId id){
		// Call findById from articleRepository to find the article with the given id
		Optional<Article> optionalArticle = articleRepository.findById(id);

		// If the article is present, then return it
		// otherwise, throw an Article Not Found Exception
		if(optionalArticle.isPresent()){
			return optionalArticle.get();
		}
		else{
			throw new ArticleNotFoundException();
		}
	}

	/**
	 * Overloaded method using ObjectId as parameter
	 *
	 * @see ArticleServiceImpl#findById(ObjectId)
	 */
	public Article findById(String id){
		// Convert the string to an objectId
		ObjectId objectId = new ObjectId(id);

		// Use the converted objectId as the parameter for the original method
		return findById(objectId);
	}

	/**
	 * Submit an article for approval.
	 * If it passes the requirements for submission,
	 * then change its status to BETA
	 * and send an email to an administrator about the submission
	 *
	 * @param id Used to locate the article
	 * @return the updated Article
	 */
	public Article submitArticle(ObjectId id) {
		// Call findById to retrieve the article and validate that it exists
		Article article = findById(id);

		// A submitting article can't be of status DISCARDED or APPROVED
		// throw appropriate exception
		if (article.getStatus() == Status.DISCARDED) {
			throw new SubmittingArticleIsDiscardedException();
		} else if (article.getStatus() == Status.APPROVED) {
			throw new SubmittingArticleIsApprovedException();
		} else if(article.getStatus() == Status.BETA) {
			throw new SubmittingArticleIsBetaException();
		}
		else{
			// If the article is of status INITIAL or REJECTED
			// Set the status to BETA
			article.setStatus(Status.BETA);
			/*
			TODO: Send an email to Administrator
			 */
			// Save the article
			articleRepository.save(article);
			// return the article with the new status
			return article;
		}
	}

	/**
	 * Approve an article that's been submitted.
	 * If it passes the requirements for approval,
	 * then change its status to APPROVED.
	 *
	 * @param id Used to locate the article
	 * @return the updated Article
	 */
	public Article approveArticle(ObjectId id){
		// Call findById to retrieve the article and validate that it exists
		Article article = findById(id);

		// An article being approved can only be of status BETA
		// throw appropriate exceptions otherwise
		if(article.getStatus() == Status.REJECTED){
			throw new ApprovingArticleIsStillRejectedException();
		} else if(article.getStatus() == Status.INITIAL){
			throw new ApprovingArticleIsInitialException();
		} else if(article.getStatus() == Status.APPROVED){
			throw new ApprovingArticleIsApprovedException();
		} else if(article.getStatus() == Status.DISCARDED){
			throw new ApprovingArticleIsDiscardedException();
		}
		else{
			// If the article is of status BETA
			// Set the status to APPROVED
			article.setStatus(Status.APPROVED);
			// Save the article
			articleRepository.save(article);
			// return the article with the new status
			return article;
		}

	}

	/**
	 * Reject an article that's been submitted.
	 * If it passes the requirements for rejection,
	 * then change its status to REJECTED.
	 *
	 * @param id Used to locate the article
	 * @return the updated Article
	 */
	public Article rejectArticle(ObjectId id){
		// Call findById to retrieve the article and validate that it exists
		Article article = findById(id);

		// An article being rejected can only be of status BETA
		// throw appropriate exceptions otherwise
		if(article.getStatus() == Status.REJECTED){
			throw new RejectingArticleIsStillRejectedException();
		} else if(article.getStatus() == Status.INITIAL){
			throw new RejectingArticleIsInitialException();
		} else if(article.getStatus() == Status.APPROVED){
			throw new RejectingArticleIsApprovedException();
		} else if(article.getStatus() == Status.DISCARDED){
			throw new RejectingArticleIsDiscardedException();
		}
		else{
			// Increase the rejection count of the article
			article.setRejectedCount(article.getRejectedCount() + 1);
			// If it's been allowed more than 3 edits,
			// then change status to DISCARDED
			// otherwise change the status to REJECTED
			if (article.getRejectedCount() >= 4) article.setStatus(Status.DISCARDED);
			else article.setStatus(Status.REJECTED);
			// Save the article
			articleRepository.save(article);
			// return the article with the new status
			return article;
		}
	}

	/**
	 * Create a new article for a user with the given email.
	 * Also, create a new ether pad with the new article's id.
	 * @param email Email associated with the user
	 * @return article The new article created
	 */
	public Article createArticleByEmail(String email){
		// Call the userService's findByEmail method
		// To validate that the user with the given email exists
		User user = userService.findByEmail(email);

		// Get that user's articles using the articleRepository
		// (We could have used user.getArticles()
		List<Article> articles = articleRepository.findAllArticlesByEmailId(email);

		// Declared new article object and
		// set with with the article builder with initial parameters
		Article newArticle = Article.builder()
				.id(new ObjectId())
				.emailId(user.getEmail())
				.name("New Article")
				.status(Status.INITIAL)
				.readOnly(false)
				.build();

		// add the new article to the list of articles
		articles.add(newArticle);

		// set the articles list to the users articles
		user.setArticles(articles);

		//create an etherPad with the article's ObjectId
		epLiteClient.createPad(newArticle.getId().toString());

		// save article to database
		articleRepository.save(newArticle);

		// save user to database
		userRepository.save(user);

		// return new article object
		return newArticle;
	}

	/**
	 * Save an article with the given id
	 *
	 * @param etherPadId id of the ether pad and article
	 * @return article The saved article
	 */
	public Article saveArticle(String etherPadId) {
		// Call findById to validate that the article does exist
		Article article = findById(etherPadId);
		// Retrieve the contents of the ether pad
		String content = epLiteClient.getText(etherPadId).get("text").toString();
		epLiteClient.setText(etherPadId, content);
		// Set the article's contents to the ether pad content
		article.setContent(content);
		// Save the article with updated content
		articleRepository.save(article);
		return article;
	}

	/**
	 * Get the appropriate URL for the ether pad requested
	 * @param id the id of the article
	 * @return ether pad url
	 */
	public String getEtherPadUrl(String id){
		// Call findById to validate that the article does exist
		Article article = findById(id);

		// TODO: Change to retrieve from application.properties
		String etherPadUrl = "http://localhost:9001/p/";

		// Get either the readOnly id or the editable id
		String appendingId = null;
		switch(article.getStatus()){
			case APPROVED:
			case BETA:
			case DISCARDED:
				appendingId = epLiteClient.getReadOnlyID(id).get("readOnlyID").toString() + "?";
				appendingId = appendingId +  "showControls=false";
				break;
			case INITIAL:
			case REJECTED:
				appendingId = id + "?";
		}
		etherPadUrl = etherPadUrl + appendingId;
		return etherPadUrl;
	}
}

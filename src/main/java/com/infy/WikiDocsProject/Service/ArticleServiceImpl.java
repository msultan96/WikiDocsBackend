package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.*;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.RoleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Model.Role;
import com.infy.WikiDocsProject.enums.Status;

import java.util.*;

import net.gjerull.etherpad.client.EPLiteClient;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Article Service Implementations
 */
@SuppressWarnings("UnnecessaryLocalVariable")
@Service(value="articleService")
public class ArticleServiceImpl implements ArticleService {

	private final CustomUserDetailsService customUserDetailsService;
	private final EPLiteClient epLiteClient;
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JavaMailSender javaMailSender;

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public ArticleServiceImpl(CustomUserDetailsService customUserDetailsService,
							  ArticleRepository articleRepository,
							  UserRepository userRepository,
							  RoleRepository roleRepository,
							  EPLiteClient epLiteClient,
							  JavaMailSender javaMailSender) {
		this.customUserDetailsService = customUserDetailsService;
		this.articleRepository = articleRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.epLiteClient = epLiteClient;
		this.javaMailSender = javaMailSender;
	}

//	public List<Article> getAllArticlesByEmailId(String email) {
//		customUserDetailsService.findByEmail(email);
//
//		List<Article> articles = articleRepository.findAllArticlesByEmailId(email);
//		return articles;
//	}

	/**
	 * Retrieves the list of articles of a user
	 *
	 * @param email Used to locate the user
	 * @return The list of articles of a user
	 */
	public List<Article> getAllArticlesByEmailId(String email, int pageNumber, int pageSize) {
		customUserDetailsService.findByEmail(email);

		Pageable pageWithSize = PageRequest.of(pageNumber, pageSize);
		Page<Article> page = articleRepository.findAllArticlesByEmailId(email, pageWithSize);
		List<Article> articles = page.getContent();
		return articles;
	}

	/**
	 * Retrieves all articles across all users with given status
	 * @return the list of approved articles
	 */
	public List<Article> getAllArticlesByStatus(Status status, int pageNumber, int pageSize){
		Pageable pageWithSize = PageRequest.of(pageNumber, pageSize);
		Page<Article> page = articleRepository.findArticlesByStatus(status, pageWithSize);
		List<Article> articles = page.getContent();
		return articles;
	}

	/**
	 * Retrieves the list of articles of a user filtered
	 * by a given status
	 *
	 * @param email Used to locate the user
	 * @param status The status of the article
	 * @return The list of articles with specific status of a user
	 * @throws UserNotFoundException If the email isn't found
	 */
	public List<Article> getAllArticlesByEmailIdAndStatus(String email, Status status, int pageNumber, int pageSize) {
		customUserDetailsService.findByEmail(email);

		Pageable pageWithSize = PageRequest.of(pageNumber, pageSize);
		Page<Article> page = articleRepository.findAllArticlesByEmailIdAndStatus(email, status, pageWithSize);
		List<Article> articles = page.getContent();
		return articles;
	}

	/**
	 * Retrieve an article with a specific id.
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
			throw new ArticleNotFoundException("ArticleService.INVALID_ID");
		}
	}

	/**
	 * Overloaded method using ObjectId as parameter instead
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
	public Article submitArticle(String id) {
		// Call findById to retrieve the article and validate that it exists
		Article article = findById(id);

		// A submitting article can't be of status DISCARDED or APPROVED
		// throw appropriate exception
		if (article.getStatus() == Status.DISCARDED) {
			throw new SubmittingArticleIsDiscardedException("ArticleService.SUBMITTING_ARTICLE_DISCARDED");
		} else if (article.getStatus() == Status.APPROVED) {
			throw new SubmittingArticleIsApprovedException("ArticleService.SUBMITTING_ARTICLE_APPROVED");
		} else if(article.getStatus() == Status.BETA) {
			throw new SubmittingArticleIsBetaException("ArticleService.SUBMITTING_ARTICLE_BETA");
		}
		else{
			// If the article is of status INITIAL or REJECTED
			// Set the status to BETA
			article.setStatus(Status.BETA);
			/*
			TODO: Send an email to Administrator
			 */
			Role adminRole = roleRepository.findByRole("ADMIN");
			List<User> users = userRepository.findAll();
			List<User> admins = new ArrayList<>();

			users.forEach(user ->{
				Set<Role> userRoles = user.getRoles();
				if(userRoles.contains(adminRole)){
					admins.add(user);
				}
			});

			String[] adminEmailsArray = new String[admins.size()];
			for(int i=0; i<admins.size(); i++){
				adminEmailsArray[i] = admins.get(i).getEmail();
			}
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(adminEmailsArray);
			email.setSubject("Article pending approval");
			String emailBody = "The following article by " + article.getEmailId() + " requires your attention. \n\n\n"
					+ article.getContent() + "\n\n\n"
//					+ "Approve: " + "http://localhost:8080/DLM_Wiki/ArticleAPI/approveArticle/" + article.getId().toHexString()
//					+ " \n\n"
//					+ "Reject: " + "http://localhost:8080/DLM_Wiki/ArticleAPI/rejectArticle/" + article.getId().toHexString();
					+ "Head on over to DLM Wiki to make further action " + "http://localhost:4200";
			email.setText(emailBody);
			javaMailSender.send(email);

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
	public Article approveArticle(String id){
		// Call findById to retrieve the article and validate that it exists
		Article article = findById(id);

		// An article being approved can only be of status BETA
		// throw appropriate exceptions otherwise
		if(article.getStatus() == Status.REJECTED){
			throw new ApprovingArticleIsStillRejectedException("ArticleService.APPROVING_ARTICLE_REJECTED");
		} else if(article.getStatus() == Status.INITIAL){
			throw new ApprovingArticleIsInitialException("ArticleService.APPROVING_ARTICLE_INITIAL");
		} else if(article.getStatus() == Status.APPROVED){
			throw new ApprovingArticleIsApprovedException("ArticleService.APPROVING_ARTICLE_APPROVED");
		} else if(article.getStatus() == Status.DISCARDED){
			throw new ApprovingArticleIsDiscardedException("ArticleService.APPROVING_ARTICLE_DISCARDED");
		}
		else{
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
	public Article rejectArticle(String id){
		// Call findById to retrieve the article and validate that it exists
		Article article = findById(id);

		// An article being rejected can only be of status BETA
		// throw appropriate exceptions otherwise
		if(article.getStatus() == Status.REJECTED){
			throw new RejectingArticleIsStillRejectedException("ArticleService.REJECTING_ARTICLE_REJECTED");
		} else if(article.getStatus() == Status.INITIAL){
			throw new RejectingArticleIsInitialException("ArticleService.REJECTING_ARTICLE_INITIAL");
		} else if(article.getStatus() == Status.APPROVED){
			throw new RejectingArticleIsApprovedException("ArticleService.REJECTING_ARTICLE_APPROVED");
		} else if(article.getStatus() == Status.DISCARDED){
			throw new RejectingArticleIsDiscardedException("ArticleService.REJECTING_ARTICLE_DISCARDED");
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
	public Article createArticleByEmail(String email, String articleName){
		// Validate that the user with the given email exists
		// and retrieve the user
		User user = customUserDetailsService.findByEmail(email);

		// Get that user's articles using the articleRepository
		// (We could have used user.getArticles()
		List<Article> articles = articleRepository.findAllArticlesByEmailId(email);

		// Declared new article object and
		// set with with the article builder with initial parameters
		Article newArticle = Article.builder()
				.id(new ObjectId())
				.emailId(user.getEmail())
				.name(articleName)
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
		switch(article.getStatus()){
			case APPROVED:
			case BETA:
			case DISCARDED:
				throw new SavingArticleIsSubmittedException("ArticleService.EDITING_ARTICLE_SUBMITTED");
		}
		// Retrieve the contents of the ether pad
		String content = epLiteClient.getText(etherPadId).get("text").toString();
		if(content == null) content = "";
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
		if(article.getContent() == null) article.setContent("");
		epLiteClient.setText(id,article.getContent());
		return etherPadUrl;
	}

	/**
	 * Get All Articles a User has been invited to collaborate on
	 * @param email email of the user
	 * @param pageNumber current page number
	 * @param pageSize size of the page
	 * @return the collaborating articles
	 */
	public List<Article> getAllInvitedArticlesByEmail(String email, int pageNumber, int pageSize){
		List<Article> articles = new ArrayList<>();

		//Retrieve the user + their collaborating articles
		User user = customUserDetailsService.findByEmail(email);
		List<ObjectId> collaboratingArticlesById = user.getCollaboratingArticles();

		//Remove any articles that aren't editable
		collaboratingArticlesById.removeIf(id -> {
			Article article = findById(id);
			if(article.getStatus() == Status.DISCARDED
					|| article.getStatus() == Status.BETA
					|| article.getStatus() == Status.APPROVED)
				return true;
			else{
				articles.add(article);
				return false;
			}
		});

		user.setCollaboratingArticles(collaboratingArticlesById);
		userRepository.save(user);

		//Convert the list into pages
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		int start = Math.toIntExact(pageable.getOffset());
		int end = Math.min((start + pageable.getPageSize()), articles.size());

		if(start>end) return Collections.emptyList();
		Page<Article> page = new PageImpl<>(articles.subList(start, end), pageable, articles.size());

		//return the current page
		return page.getContent();
	}

	public String inviteUserToCollaborateByEmail(Map<String, String> map){
		String inviteeEmail = map.get("email");
		String articleIdAsString = map.get("articleId");
		ObjectId articleId = new ObjectId(articleIdAsString);

		//validate the user email exists
		User invitee = customUserDetailsService.findByEmail(inviteeEmail);

		//retrieve the articles to mutate later
		List<ObjectId> inviteeArticles = invitee.getCollaboratingArticles();

		if(inviteeArticles.contains(articleId)) {
			throw new UserAlreadyInvitedException("ArticleService.USER_ALREADY_INVITED");
		}

		//Validate article id
		findById(articleId);

		//Add to users collaborating articles
		inviteeArticles.add(articleId);

		//Save user with modified collaborating articles
		userRepository.save(invitee);

		return invitee.getName();
	}

}

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

@Service(value="articleService")
public class ArticleServiceImpl implements ArticleService {

	private final UserService userService;
	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleServiceImpl(UserService userService, ArticleRepository articleRepository) {
		this.userService = userService;
		this.articleRepository = articleRepository;
	}

	public List<Article> getAllArticlesByUser(String name) throws Exception{
		User user;
		try{
			user = userService.findUserByName(name);
			List<Article> articles = articleRepository.findAllArticlesByUserId(user.getId());
			return articles;
		}
		catch(UserNotFoundException e){
			throw new UserNotFoundException();
		}
	}

	public List<Article> getApprovedArticles(){
		List<Article> articles = articleRepository.findArticlesByStatus(Status.APPROVED);
		return articles;
	}

	public List<Article> getBetaArticles(){
		List<Article> articles = articleRepository.findArticlesByStatus(Status.BETA);
		return articles;
	}

	public Article getArticleByChannelId(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()){
			return optionalArticle.get();
		}
		else{
			throw new ArticleNotFoundException();
		}
	}

	public Article submitArticle(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()){
			Article article = optionalArticle.get();
			if(article.getStatus() == Status.INITIAL
					|| article.getStatus() == Status.BETA
					|| article.getStatus() == Status.REJECTED) {

				article.setStatus(Status.BETA);
				/*
				TODO: Send an email to Administrator
				 */
				articleRepository.save(article);
				return article;
			}

			if(article.getStatus() == Status.DISCARDED){
				throw new SubmittingArticleIsDiscardedException();
			}
			if(article.getStatus() == Status.APPROVED) {
				throw new SubmittingArticleIsApprovedException();
			}
		}
		throw new ArticleNotFoundException();
	}

	public Article approveArticle(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()) {
			Article article = optionalArticle.get();

			if(article.getStatus() == Status.BETA){
				article.setStatus(Status.APPROVED);
				articleRepository.save(article);
				return article;
			}

			if(article.getStatus() == Status.REJECTED){
				throw new ApprovingArticleIsStillRejectedException();
			}

			if(article.getStatus() == Status.INITIAL){
				throw new ApprovingArticleIsInitialException();
			}
			if(article.getStatus() == Status.APPROVED){
				throw new ApprovingArticleIsApprovedException();
			}
			if(article.getStatus() == Status.DISCARDED){
				throw new ApprovingArticleIsDiscardedException();
			}
		}
		throw new ArticleNotFoundException();
	}

	public Article rejectArticle(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()) {
			Article article = optionalArticle.get();

			if(article.getStatus() == Status.BETA){
				article.setStatus(Status.REJECTED);
				article.setRejectedCount(article.getRejectedCount() + 1);
				if (article.getRejectedCount() > 3) article.setStatus(Status.DISCARDED);
				articleRepository.save(article);
				return article;
			}

			if(article.getStatus() == Status.INITIAL){
				throw new RejectingArticleIsInitialException();
			}
			if(article.getStatus() == Status.APPROVED){
				throw new RejectingArticleIsApprovedException();
			}
			if(article.getStatus() == Status.REJECTED){
				throw new RejectingArticleIsStillRejectedException();
			}
			if(article.getStatus() == Status.DISCARDED){
				throw new RejectingArticleIsDiscardedException();
			}
		}
		throw new ArticleNotFoundException();
	}
}

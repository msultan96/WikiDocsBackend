package com.infy.WikiDocsProject.Service;

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
		}
		catch(Exception e){
			throw new Exception(e.getMessage());
		}

		List<Article> articles = articleRepository.findAllArticlesByUserId(user.getId());
		return articles;
	}

	public List<Article> getApprovedAndBetaArticles(){
		List<Article> articles = articleRepository.findArticlesByStatusOrStatus(Status.APPROVED, Status.BETA);
		return articles;
	}

	public Article getArticleByChannelId(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()){
			return optionalArticle.get();
		}
		else{
			throw new Exception("ArticleService.INVALID_CHANNEL_ID");
		}
	}

	public Article submitArticleForApproval(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()){
			Article article = optionalArticle.get();
			article.setStatus(Status.BETA);
			/*
			TODO: Send an email to Administrator
			 */
			articleRepository.save(article);
			return article;
		}
		else{
			throw new Exception("ArticleService.INVALID_CHANNEL_ID_SUBMISSION");
		}
	}

	public Article approveArticle(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()) {
			Article article = optionalArticle.get();
			article.setStatus(Status.APPROVED);
			articleRepository.save(article);
			return article;
		}
		else{
			throw new Exception("ArticleService.INVALID_CHANNEL_ID_APPROVAL");
		}
	}

	public Article rejectArticle(String channelId) throws Exception{
		Optional<Article> optionalArticle = articleRepository.findArticleByChannelId(channelId);
		if(optionalArticle.isPresent()) {
			Article article = optionalArticle.get();
			article.setStatus(Status.REJECTED);
			article.setRejectedCount(article.getRejectedCount() + 1);
			if (article.getRejectedCount() > 3) article.setStatus(Status.DISCARDED);
			articleRepository.save(article);
			return article;
		}
		else{
			throw new Exception("ArticleService.INVALID_CHANNEL_ID_REJECTED");
		}
	}
}

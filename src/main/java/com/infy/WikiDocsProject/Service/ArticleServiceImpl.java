package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="articleService")
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public List<Article> getApprovedAndBetaArticles(){
		return articleRepository.findArticlesByStatusOrStatus(Status.APPROVED, Status.BETA);
	}

	public Article getArticleByChannelId(String channelId) {
		return articleRepository.findArticleByChannelId(channelId);
	}

	public void submitArticleForApproval(String channelId){
		Article article = articleRepository.findArticleByChannelId(channelId);
		article.setStatus(Status.BETA);

		//Send an email to Admin

		articleRepository.save(article);
	}

	public void approveArticle(String channelId){
		Article article = articleRepository.findArticleByChannelId(channelId);
		article.setStatus(Status.APPROVED);

		articleRepository.save(article);
	}

	public void rejectArticle(String channelId){
		Article article = articleRepository.findArticleByChannelId(channelId);
		article.setStatus(Status.REJECTED);
		article.setRejectedCount(article.getRejectedCount() + 1);
		if(article.getRejectedCount()>3) article.setStatus(Status.DISCARDED);
	}
}

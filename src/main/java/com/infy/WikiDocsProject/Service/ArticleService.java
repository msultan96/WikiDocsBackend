package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;

/**
 * Article Service Class 
 * Headers
 */
public interface ArticleService {
	
	//Method declarations

	List<Article> getAllArticlesByEmailId(String email) throws Exception;
	List<Article> getAllApprovedArticlesByEmailId(String email) throws Exception;
	List<Article> getAllBetaArticlesByEmailId(String email) throws Exception;
	List<Article> getAllInitialArticlesByEmailId(String email) throws Exception;
	List<Article> getAllRejectedArticlesByEmailId(String email) throws Exception;
	List<Article> getAllDiscardedArticlesByEmailId(String email) throws Exception;

	Article getArticleByChannelId(String channelId) throws Exception;

	List<Article> getApprovedArticles();
	List<Article> getBetaArticles();

	Article submitArticle(String channelId) throws Exception;
	Article approveArticle(String channelId) throws Exception;
	Article rejectArticle(String channelId) throws Exception;

}

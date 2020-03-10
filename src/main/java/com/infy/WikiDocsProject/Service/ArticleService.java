package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;

/**
 * Article Service Class 
 * Headers
 */
public interface ArticleService {
	
	//Method declarations

	List<Article> getAllArticlesByEmail(String email) throws Exception;
	List<Article> getAllApprovedArticlesByEmail(String email) throws Exception;
	List<Article> getAllBetaArticlesByEmail(String email) throws Exception;
	List<Article> getAllInitialArticlesByEmail(String email) throws Exception;
	List<Article> getAllRejectedArticlesByEmail(String email) throws Exception;
	List<Article> getAllDiscardedArticlesByEmail(String email) throws Exception;

	Article getArticleByChannelId(String channelId) throws Exception;

	List<Article> getApprovedArticles();
	List<Article> getBetaArticles();

	Article submitArticle(String channelId) throws Exception;
	Article approveArticle(String channelId) throws Exception;
	Article rejectArticle(String channelId) throws Exception;

}

package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;

/**
 * Article Service Class 
 * Headers
 */
public interface ArticleService {
	
	//Method declarations

	Article createArticleByEmail(String email, String channelId);

	List<Article> getAllArticlesByEmailId(String email);
	List<Article> getAllApprovedArticlesByEmailId(String email);
	List<Article> getAllBetaArticlesByEmailId(String email);
	List<Article> getAllInitialArticlesByEmailId(String email);
	List<Article> getAllRejectedArticlesByEmailId(String email);
	List<Article> getAllDiscardedArticlesByEmailId(String email);

	Article getArticleByChannelId(String channelId);

	List<Article> getApprovedArticles();
	List<Article> getBetaArticles();

	Article submitArticle(String channelId);
	Article approveArticle(String channelId);
	Article rejectArticle(String channelId);

}
